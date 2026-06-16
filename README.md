# Schiffe versenken

## Vorschau

![Schiffe versenken im Terminal](screenshot.png)

Ein konsolenbasiertes Schiffe-versenken-Spiel (Mensch gegen Computer) in Java,
mit einer sauber geschichteten, objektorientierten Architektur.

## Über das Projekt

Klassisches Schiffe versenken auf einem 10×10-Feld. Der Schwerpunkt des
Projekts liegt weniger im Spiel selbst als in einem sauberen Design: Die
gesamte Spiellogik ist strikt von der Oberfläche getrennt. Dadurch ließe sich
die aktuelle Konsolen-Oberfläche jederzeit gegen eine grafische (z. B. JavaFX
oder Android) austauschen, ohne eine einzige Zeile der Spielmechanik zu ändern.

## Features

- Mensch gegen Computer auf einem 10×10-Feld
- Getrennte Bretter: eigenes Feld und Trefferfeld des Gegners, ohne Einblick in
  dessen Schiffe
- Schiffe der Längen 2 bis 5 – vom Spieler manuell platziert, vom Computer
  zufällig
- Robuste Eingabeprüfung: ungültige Eingaben führen nicht zum Absturz, sondern
  werden erneut abgefragt
- Automatische Erkennung versenkter Schiffe und des Spielendes

## Architektur

Das Projekt ist in zwei Schichten aufgeteilt:

**Spiellogik** (kennt die Oberfläche nicht):

- `Koordinate` – eine Position auf dem Feld (Zeile, Spalte)
- `Ausrichtung` – horizontal/vertikal; erzeugt die Felder eines Schiffs
- `Schiff` – kennt seine Felder und weiß selbst, ob es versenkt ist
- `Spielfeld` – das Brett eines Spielers: platzieren, beschießen, Sieg prüfen
- `Schussergebnis` – Ergebnis eines Schusses (daneben / Treffer / versenkt)
- `Spieler`, `MenschlicherSpieler`, `ComputerSpieler` – ein Spieler mit eigenem
  Feld; der Mensch entscheidet per Eingabe, der Computer per Zufall
- `Spiel` – der Schiedsrichter: Zugreihenfolge und Spielende
- `Zugergebnis` – beschreibt, was in einem Zug passiert ist

**Oberfläche** (austauschbar):

- `Eingabe` – Schnittstelle für Spielereingaben
- `KonsolenEingabe` – liest Eingaben von der Konsole
- `KonsoleUI` – zeichnet die Bretter und steuert den Spielablauf

## Technische Highlights

- Records für unveränderliche Werte (`Koordinate`, `Zugergebnis`)
- Ein Enum mit Verhalten (`Ausrichtung`)
- Abstrakte Basisklasse mit Template-Method-Muster (`Spieler`)
- Eine Schnittstelle als Naht zwischen Logik und Oberfläche (`Eingabe`)
- Constructor Injection statt fest verdrahteter Abhängigkeiten

## Starten

Voraussetzung: JDK 17 oder neuer.

In IntelliJ IDEA: Projekt öffnen und die `main`-Methode in `KonsoleUI`
ausführen.

Über die Kommandozeile:

    cd src
    javac *.java
    java KonsoleUI

## Spielanleitung

- **Schiffe platzieren:** Startfeld eingeben (z. B. `A1`), dann Ausrichtung
  `h` (horizontal) oder `v` (vertikal)
- **Schießen:** Zielkoordinate eingeben (z. B. `C7`)
- **Legende:** `S` = Schiff, `X` = Treffer, `O` = Fehlschuss, `~` = Wasser

## Mögliche Erweiterungen

- Grafische Oberfläche (JavaFX für den Desktop, Android für mobil) – dank der
  getrennten Logik ohne Eingriff in die Spielmechanik
- Klügere Computer-Strategie (gezieltes Nachschießen rund um einen Treffer)
- Zusätzliche Regeln wie ein Extra-Schuss bei Treffer