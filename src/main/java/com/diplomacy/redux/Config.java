package com.diplomacy.redux;

public class Config {
    private final int UNION_DIPLOMACY_COST;
    private final int UNION_SCORE_NOT_ALLIED;
    private final int UNION_SCORE_BASE_VALUE;
    private final int UNION_SCORE_DISTANCE;

    public Config(
            int UNION_DIPLOMACY_COST,
            int UNION_SCORE_NOT_ALLIED,
            int UNION_SCORE_BASE_VALUE,
            int UNION_SCORE_DISTANCE )
    {
        this.UNION_DIPLOMACY_COST = UNION_DIPLOMACY_COST;
        this.UNION_SCORE_NOT_ALLIED = UNION_SCORE_NOT_ALLIED;
        this.UNION_SCORE_BASE_VALUE = UNION_SCORE_BASE_VALUE;
        this.UNION_SCORE_DISTANCE = UNION_SCORE_DISTANCE;
    }

    public static Config createDefault() {
        return new Config(200, -1000, -50, -50);
    }

    public int getUNION_DIPLOMACY_COST() {
        return UNION_DIPLOMACY_COST;
    }

    public int getUNION_SCORE_NOT_ALLIED() {
        return UNION_SCORE_NOT_ALLIED;
    }

    public int getUNION_SCORE_BASE_VALUE() {
        return UNION_SCORE_BASE_VALUE;
    }

    public int getUNION_SCORE_DISTANCE() {
        return UNION_SCORE_DISTANCE;
    }

    public void validate() {
        if (UNION_DIPLOMACY_COST <= 0) {
            throw new IllegalStateException("All union diplomacy cost values should be positive or zero in Diplomacy config");
        }
    }
}