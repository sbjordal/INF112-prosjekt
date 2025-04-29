<img align="right" width=200 src=".\src\main\resources\assets\gameicon.png">

# INF112 Project – *Star Hunt*

## Team:
* Gruppe 6 - *"Sjette_Etasje"*

### Medlemmer og roller:
* Endre H. Aspøy: Teamleder, utvikler
* Thale Marie Bjerkreim: Dokumentasjonsansvarlig, utvikler
* Aurora Bjørnerud: Kundekontakt, ansvar for user Interface, utvikler
* Stine Bjordal: Testansvarlig, utvikler
* Julie Sandanger: Designansvarlig, utvikler
* Eivind H. Naasen: Arkitekt, utvikler

### Lenker:
* Lenke til GitLab: https://git.app.uib.no/inf112/25v/proj/sjette-etasje
* Lenke til Jira (krever invitert tilgang): https://endre-aspoy.atlassian.net/jira/core/projects/IS/board


## Om spillet 

### Hvordan spille 
#### Mål:
- Målet med spillet er å komme så langt som mulig med flest mulig poeng. 
- For å komme til neste level må spilleren fange levelets stjerne. Underveis kan man samle mynter, spise bananer, 
  drepe fiender, samt unngå å bli drept selv.
- Man mottar poeng ved å samle mynter, drepe fiender og fullføre levels, og mister poeng dersom man tar skade.
- Spilleren kan spise bananer for å bli stor og få skjold mot skade fra fiender. 
- Spilleren starter med 3 liv, og mister liv dersom den kolliderer med fiender. Faller spilleren ned, vil den dø umiddelbart.
- Hvert level må fullføres før tiden er ute.

#### Kontroller: 
- Spilleren beveges med høyre og venstre piltaster for frem og tilbake, eventuelt A og D. 
- Spilleren hopper med piltast opp, space eller W.

### Kjøring 
* Kompileres med `mvn package`, benytter Apache Maven 3.6.3.
* Kjøres med `mvn exec:java`.
* Krever Java 17.0.14 eller nyere.

### Kjente feil / bugs
- En snegl på siste level beveger seg innimellom bak/gjennom vegger. 
  Det virker som at dette kun skjer dersom man har spilt gjennom alle levels en gang før. 
  Har forsøkt å gjenskape bugen med tester og brukertesting uten hell.
- Hvis du holder inne "piltast opp" for hopp mens du pauser, vil player hoppe konstant selv om du har sluppet knappen.

## Credits 

### Konsept
- Super Mario Bros (1984) for inspirasjon.

### Grafikk
- Bakgrunn 
  * [Jessie M.] (https://jesse-m.itch.io/jungle-pack)
- Hindringer/bakke 
  * [Kenney.nl] (https://opengameart.org/content/platformer-art-complete-pack-often-updated)
- Spiller 
  * [Jessie M.] (https://jesse-m.itch.io/jungle-pack)
- Mynt 
  * [Pritishor] (https://opengameart.org/content/simple-coin)
- Snegl, leopard, banan og stjerne
  * [Noto emoji by Google] (https://emoji.aranja.com/)
  - Under Apache License 2.0

### Lyder

- Coin / scoreUp
  - https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=230517
  - https://pixabay.com/sound-effects/coin-recieved-230517/
- Ouch / damage taken
  - Sound Effect by <a href="https://pixabay.com/users/kodasworldproductions-27998106/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=163912">Nykoda Caston</a> from <a href="https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=163912">Pixabay</a>
  - https://pixabay.com/sound-effects/characterouch2-163912/
