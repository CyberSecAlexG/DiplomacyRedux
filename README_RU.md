[🇺🇸 English](https://github.com/MushroomMif/example-aoh3-fabric-mod/blob/master/README.md)
-----
Пример мода для Age of History 3 Fabric Loader
============
Это пример мода для [AOH3 Fabric Loader](https://github.com/MushroomMif/aoh3-fabric-loader).
Вы можете использовать его как шаблон для создания собственных модов для этого загрузчика.
Шаги, чтобы установить проект этого мода:
1. Изначальная установка (вам надо будет сделать это только один раз)
- Создайте папку, где будут находиться проекты всех ваших модов для AOH3 Fabric Loader.
- Создайте там ещё одну папку `libs`.
- Скачайте [этот архив](https://disk.yandex.ru/d/eTRhqz-oJE8lpQ) и распакуйте его 
содержимое в созданную до этого папку `libs`.
- Добавьте jar файл загрузчика Fabric в эту папку
- Если вы используете Windows:
  - Просто скопируйте файл `aoh3.jar` из папки игры в вашу папку `libs`

  Если вы используете Mac:
  - Зайдите в папку игры, скопируйте файл `Age of History 3.app/Contents/MacOS/Age of History 3-*Версия*.jar`
    в вашу папку `libs` **и переименуйте его в** `aoh3.jar`

  В случае выхода новой версии игры, вам надо будет повторить этот шаг.
- Скачайте файлы `aoh3-fabric-api.jar` и `aoh3-fabric-api-sources.jar` с
  [последнего релиза AOH3 Fabric Api](https://github.com/MushroomMif/aoh3-fabric-api/releases/latest)
  и тоже добавьте их в папку `libs`. Технически, это не обязательно, но
  AOH3 Fabric Api добавляет много очень полезных утилит, так что почему не использовать их.
  В случае выхода новой версии AOH3 Fabric Api вам также надо будет повторить этот шаг.
- По желанию: Для лучшей читабельности и навигации по коду игры в IDE
  мы рекомендуем декомпилировать его с помощью декомпилятора
  (например, [Vineflower](https://github.com/Vineflower/vineflower))
  и прикрепить полученные исходники к jar-файлу игры в вашей IDE.
2. Настройка проекта мода (вам надо будет сделать это для каждого нового мода)
- Клонируйте этот репозиторий в папку для проектов модов, что вы создали ранее.
- Переименуйте появившуюся папку в имя вашего мода, а также измените его в файле
  `settings.gradle.kts`.
- Откройте проект в вашей любимой IDE (если вы не знаете, какую IDE выбрать,
  мы рекомендуем [бесплатную Intellij IDEA **Community Edition**](https://jetbrains.com/idea/download/)).
- Переименуйте `com.example.mod` в `src/main/java` как вы хотите, а также измените его
  в файле `src/main/resources/modid.mixins.json`.
- Настройте поля `id`, `version`, `name`, `description` и `authors` в файле
  `src/main/resources/fabric.mod.json`. Также, замените `modid` в названии файла
  `modid.mixins.json` на айди вашего мода и обновите поле `mixins` в файле
  `fabric.mod.json`.
- Поздравляем! Вы успешно установили и настроили проект мода.
  Теперь вы можете начать писать ваш мод, в том числе миксины. 
  Мы рекомендуем установить [плагин Minecraft Dev](https://mcdev.io/)
  если вы используете IntelliJ IDEA, он поможет в написании миксинов. 
  Чтобы собрать проект, используйте команду `gradlew build`,
  она создаст jar-файл мода в папке `build/libs`. Также, посмотрите на
  [секцию про миксины на Fabric вики](https://fabricmc.net/wiki/tutorial:mixin_introduction) и
  [документацию MixinExtras](https://github.com/LlamaLad7/MixinExtras/wiki)
  или просто видео на ютубе на тему миксинов. 
  Если вы хотите глубокое понимание этой технологии, то посетите
  [документацию библиотеки Mixin](https://github.com/SpongePowered/Mixin/wiki).
