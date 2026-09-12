# FelixNoChat

Minimaler clientseitiger Fabric-Mod für Minecraft Java 26.2. Er verhindert das
Öffnen des Chatfensters (einschließlich `T` und `/`), blockiert ausgehende
Chatnachrichten und blendet eingehende Chatzeilen aus. Der Mod besitzt
absichtlich keine Konfiguration und keine GUI.

## Bauen

Voraussetzung ist eine Internetverbindung. Java 25 wird bei Bedarf automatisch
über die Gradle-Toolchain bezogen.

- macOS/Linux: `./gradlew build`
- Windows: `gradlew.bat build`

Die nutzbare Datei liegt danach unter `build/libs/felix-no-chat-1.0.1.jar`.

## Installieren

1. Minecraft 26.2 einmal mit dem aktuellen Fabric Loader starten.
2. Minecraft beenden.
3. Eine vorhandene ältere FelixNoChat-JAR aus `mods` entfernen und
   `felix-no-chat-1.0.1.jar` hineinkopieren.
4. Das Fabric-Profil erneut starten.

Die JAR ist plattformunabhängig und für macOS, Windows und Linux identisch.
Fabric API wird nicht benötigt.

Hinweis: Dies ist eine robuste Hürde im Minecraft-Client, aber keine
Betriebssystem-Kindersicherung. Wer Schreibzugriff auf den `mods`-Ordner hat,
kann die JAR entfernen.
