package com.diplomacy.redux;

import aoh.kingdoms.history.mainGame.Game;
import aoh.kingdoms.history.mainGame.GameValues;
import aoh.kingdoms.history.map.army.ArmyDivision;
import aoh.kingdoms.history.map.civilization.Civilization;
import aoh.kingdoms.history.map.diplomacy.DiplomacyManager;
import aoh.kingdoms.history.map.province.Province;
import aoh.kingdoms.history.map.province.ProvinceDrawArmy;
import aoh.kingdoms.history.menusInGame.Info.InGame_Info;
import aoh.kingdoms.history.textures.Images;

import java.util.ArrayList;
import java.util.List;

import static aoh.kingdoms.history.map.diplomacy.DiplomacyManager.*;
import static com.diplomacy.redux.InGame_Union.Diplomacy_Cost;

public class DiplomacyManagerExtension {

    public static boolean offerUnion(int iFromCivID, int iToCivID) {
        if (Game.getCiv(iToCivID).getPuppetOfCivID() != iToCivID && iToCivID != Game.player.iCivID && iFromCivID != Game.player.iCivID) {
            return false;
        } else if (Game.getCiv(iFromCivID).fDiplomacy < Diplomacy_Cost) {
            return false;
        } else if (isAtWar(iFromCivID, iToCivID)) {
            return false;
        } else {
            Civilization var10000 = Game.getCiv(iFromCivID);
            var10000.fDiplomacy -= Diplomacy_Cost;
            if (iToCivID == Game.player.iCivID) {
                return true;
            } else {
                if (getUnion_Score(iFromCivID, iToCivID) >= 0 && isAlly(iFromCivID, iToCivID)) {
                    acceptUnionOffer(iFromCivID, iToCivID);
                } else {
                    declineUnionOffer(iFromCivID, iToCivID);
                }
                return true;
            }
        }
    }

    public static void acceptUnionOffer(int iCivA, int iCivB) {
        if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
            InGame_Info.iCivID = iCivA;
            InGame_Info.iCivID2 = iCivB;
            Game.menuManager.rebuildInGame_Info("Union proposal was accepted.", Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
            InGame_Info.imgID = Images.heart;
        }
        addUnion(iCivA, iCivB);
    }

    public static void declineUnionOffer(int iCivA, int iCivB) {
        if (iCivA == Game.player.iCivID || iCivB == Game.player.iCivID) {
            InGame_Info.iCivID = iCivA;
            InGame_Info.iCivID2 = iCivB;
            Game.menuManager.rebuildInGame_Info("Union proposal was rejected.", Game.getCiv(iCivA).getCivName() + " - " + Game.getCiv(iCivB).getCivName());
            InGame_Info.imgID = Images.heart;
        }

    }

    public static void addUnion(int iCivA, int iCivB) {
        if (iCivA != iCivB) {
            if (!isAtWar(iCivA, iCivB)) {
                Civilization CivA = Game.getCiv(iCivA);
                Civilization CivB = Game.getCiv(iCivB);
                if (iCivA == Game.player.iCivID) {
                    transferArmies(iCivB, iCivA);
                    transferProvinces(CivB, CivA);
                } else if (iCivB == Game.player.iCivID) {
                    transferArmies(iCivA, iCivB);
                    transferProvinces(CivA, CivB);
                }
                Game.menuManager.rebuildInGame_Civ(); // Rebuilds the colours on the map when ran
                Game.menuManager.rebuildInGame_ProvinceArmy(); // Not sure if this line or the next are needed, just placing here just in case.
                Game.menuManager.rebuildInGame_ProvinceInfo_Army();
                if (iCivA == Game.player.iCivID) {
                    Game.getCiv(iCivB).updateArmyImgID();
                    Game.player.fog.updateFogOfWar_Civ(iCivB); // Updates the fog-of-war for the claimed territories
                    CivA.setCivName(CivA.getCivName() + "-" + CivB.getCivName());
                } else if (iCivB == Game.player.iCivID) {
                    Game.getCiv(iCivA).updateArmyImgID();
                    Game.player.fog.updateFogOfWar_Civ(iCivA);
                    CivB.setCivName(CivB.getCivName() + "-" + CivA.getCivName());
                }
            }
        }
    }

    public static int getUnion_Score(int civFrom, int civTo) { // Original getAlliance_Score function is so fucked, so I rewrote it using if statements, rather than a huge line of conditionals.
        // Check if the civilizations are allies
        if (!DiplomacyManager.isAlly(civFrom, civTo)) {
            return DiplomacyRedux.config.getUNION_SCORE_NOT_ALLIED();
        }

        // Check if the civilizations are at war
        if (Game.getCiv(civFrom).diplomacy.isAtWar()) {
            return (int) GameValues.diplomacy.ALLIANCE_SCORE_AT_WAR;
        }

        // Calculate the base union score
        float score = getUnion_Score_BASE_VALUE(civFrom, civTo);

        // Add relation-based score
        score += Game.getCiv(civTo).diplomacy.getRelation(civFrom) * GameValues.diplomacy.ALLIANCE_SCORE_PER_RELATION;

        // Add distance-based score
        score += DiplomacyRedux.config.getUNION_SCORE_DISTANCE() *
                Game.getDistance_PercOfMax(
                        Game.getCiv(civFrom).getCapitalProvinceID(),
                        Game.getCiv(civTo).getCapitalProvinceID()
                );

        // Adjust for rivalry penalties
        if (Game.getCiv(civTo).diplomacy.isRival(civFrom)) {
            score += GameValues.diplomacy.ALLIANCE_SCORE_RIVALS;
        }
        if (Game.getCiv(civFrom).diplomacy.isRival(civTo)) {
            score += GameValues.diplomacy.ALLIANCE_SCORE_RIVALS;
        }

        // Add shared rivals score
        score += getAlliance_Score_SameRivals(civFrom, civTo);

        // Check if civTo is a vassal of civFrom
        if (Game.getCiv(civTo).getPuppetOfCivID() == civFrom) {
            score += GameValues.diplomacy.ALLIANCE_SCORE_FOR_VASSAL_OF_CIV;
        }

        // Penalize for aggressive expansion
        score += Game.getCiv(civFrom).getAggressiveExpansion() * GameValues.diplomacy.ALLIANCE_SCORE_PER_AGGRESSIVE_EXPANSION;

        // Penalize for religion differences
        if (Game.getCiv(civFrom).getReligionID() != Game.getCiv(civTo).getReligionID()) {
            if (Game.religionManager.getReligion(Game.getCiv(civFrom).getReligionID()).ReligionGroupID !=
                    Game.religionManager.getReligion(Game.getCiv(civTo).getReligionID()).ReligionGroupID) {
                score += GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_RELIGION_GROUP;
            } else {
                score += GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_RELIGION;
            }
        }

        // Penalize for government/ideology differences
        if (Game.getCiv(civFrom).getIdeologyID() != Game.getCiv(civTo).getIdeologyID()) {
            if (Game.ideologiesManager.getIdeology(Game.getCiv(civFrom).getIdeologyID()).GOV_GROUP_ID !=
                    Game.ideologiesManager.getIdeology(Game.getCiv(civTo).getIdeologyID()).GOV_GROUP_ID) {
                score += GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_GOVERNMENT_GROUP;
            } else {
                score += GameValues.diplomacy.ALLIANCE_SCORE_DIFFERENT_GOVERNMENT;
            }
        }

        // Return the final score as an integer
        return (int) score;
    }

    public static float getUnion_Score_BASE_VALUE(int civFrom, int civTo) {
        return DiplomacyRedux.config.getUNION_SCORE_BASE_VALUE() * Math.max(GameValues.diplomacy.ALLIANCE_SCORE_BASE_RANK_SCORE_MIN, Math.min(GameValues.diplomacy.ALLIANCE_SCORE_BASE_RANK_SCORE_MAX, Game.getCiv(civTo).iCivRankScore / Game.getCiv(civFrom).iCivRankScore));
    }

    public static void transferArmies(int oldCivID, int newCivID) {
        for (int i = 0; i < (Game.getCiv(oldCivID)).iArmyPositionSize; i++) {
            int nProvinceID = Game.getCiv(oldCivID).getArmyPosition(i);
            if (nProvinceID >= 0) {
                for (int j = Game.getProvince(nProvinceID).getArmySize() - 1; j >= 0; j--) {
                    if ((Game.getProvince(nProvinceID).getArmy(j)).civID == oldCivID) {
                        ArmyDivision army = (Game.getProvince(nProvinceID).getArmy(j));
                        ((ArmyDivisionExtension)army).updateCivID(newCivID);
                        army.updateArmyWidth_Just(true); // Fixes issue with armies not showing army size when unionised.
                        ProvinceDrawArmy.updateDrawArmy(nProvinceID);
                    }
                }
            }
        }
    }

    public static void transferProvinces(Civilization oldCivID, Civilization newCivID) {
        List<Integer> ICivBProvinces = oldCivID.getProvinces();
        List<Integer> copyOfProvinces = new ArrayList<>(ICivBProvinces);
        for (int province : copyOfProvinces) {
            Province prov = Game.getProvince(province);
            prov.setCivID(newCivID.getCivID());
            Game.player.fog.updateFogOfWar_All(province);
        }
    }
}
