# FelixNoChat

Erstellt mit ChatGPT.

Minimaler clientseitiger Fabric-Mod für Minecraft Java 26.2. Er verhindert das
Öffnen des Chatfensters (einschließlich `T` und `/`), blockiert ausgehende
Chatnachrichten und Befehle und blendet eingehende Chatnachrichten vollständig
aus. Auf ausdrücklich freigegebenen Servern bleibt der Chat verfügbar.

## Konfiguration

Beim ersten Start wird `config/felix-no-chat/config.yml` angelegt. Änderungen
werden beim nächsten Start von Minecraft übernommen.

```yaml
chat:
  default-enabled: false
  allowed-servers:
    - "schmoeller.net"
    - "*.schmoeller.net"
```

`default-enabled: false` sperrt den Chat grundsätzlich. Ein exakter Eintrag
gibt genau diesen Host frei; ein Eintrag mit `*.` gibt dessen Subdomains frei.
Einzelspielerwelten bleiben gesperrt, solange `default-enabled` nicht auf
`true` gesetzt wird.

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
