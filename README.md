<img align="right" width=200 src=".\src\main\resources\assets\starhunt.png">

# INF112 Project – **Star Hunt**

## Team:
* Gruppe 6 - *"Sjette_Etasje"*

### Medlemmer og roller:
* **E** * Endre H. Aspøy: Teamleder, utvikler
* **T** * Thale Marie Bjerkreim: Dokumentasjonsansvarlig, utvikler
* **A** * Aurora Bjørnerud: Kundekontakt, ansvar for user Interface, utvikler
* **S** * Stine Bjordal: Testansvarlig, utvikler
* **J** * Julie Sandanger: Designansvarlig, utvikler
* **E** * Eivind H. Naasen: Arkitekt, utvikler

### Lenker:
* [GitLab](https://git.app.uib.no/inf112/25v/proj/sjette-etasje)
* [Jira](https://endre-aspoy.atlassian.net/jira/core/projects/IS/board) (krever invitert tilgang)


## Om spillet 

#### Konsept:

- Basert på spillet Super Mario Bros
- Spillfigur som kan styres: gå til høyre og venstre, og hoppe oppover (innenfor spillets ramme).
- Todimensjonal verden:
  * Plattform – horisontal flate spilleren kan stå eller gå på (inkludert «bakken»)
  * Vegg – vertikal flate som spilleren ikke kan gå gjennom
  * Spilleren beveger seg oppover ved å hoppe, og nedover ved å falle, hvor grafikk tilpasses hvert level
  * Spillet foregår i levels med økende vanskelighetsgrad
  * Spillet har tre forskjellige level design

- Fiender som beveger seg og er skadelige ved berøring 
- Spilleren kan samle poeng ved å plukke opp mynter, bekjempe fiender og fullføre levels
- Spillets mål: Å komme lengst mulig og samle flest mulig poeng før spillkarakteren dør (game-over)
- Utfordringer i spillet:
  * Å bevege seg gjennom terrenget uten å falle utfor
  * Å unngå å miste liv som følge av å bli skadet av fiender og/eller hindringer
  * Å samle poeng
  * Få ekstra poeng ved å fullføre levelet før 45 sekunder har gått
  * Fiender har variende mengde liv

- Scoresystem:
  * Poenggjenstander: Mynter, snegler, leoparder
  * Spilleren får 10 poeng ved å hoppe på en snegl(fiendetype)
  * spilleren får 15 poeng ved å hoppe på en leopard(fiendetype)
  * Spiller får 5 poeng ved kollidere med en mynt
  * Poeng for å ha gjenværende tid ved fullført level


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
- Vi har en bug som ikke er knyttet til et spesifikt MVP/post MVP krav. Playeren hopper noen ganger litt høyere enn "vanlig". Hoppingen
  har vi tenkt er en feature istedenfor en bug. Det er jo til og med realistisk at man ikke hopper like høyt hver gang man hopper:)
  - Dette er en bug som har med Deltatime i LibGDX å gjøre, og henger sammen med FPS.
- En annen bug som vi vet om er at i grensetilfelle når Player treffer kant på plattform som kan gi ekstra høyt hopp.


## Credits 

#### Konsept
- Super Mario Bros (1984) for inspirasjon.

#### Grafikk
- Bakgrunn av [Jessie M.](https://jesse-m.itch.io/jungle-pack), inkluderer:
  * plx-*.png
  - Tillatelse fra skaper for både personlig og kommersiell bruk. 
- Spiller av [Jessie M.](https://jesse-m.itch.io/jungle-pack), inkluderer:
  * i*.png
  * jump.png, landing.png
  * r*.png
  - Tillatelse fra skaper for både personlig og kommersiell bruk.
- Hindringer/bakke av [Kenney.nl](https://kenney.nl/assets/roguelike-modern-city), inkluderer:
  * ground_*.png
  - Fra lisensen:  ingen opphavsrettigheter (No copyrights).
- Mynt av [Pritishor](https://opengameart.org/content/simple-coin), inkluderer:
  * coin.png
  - Fra lisensen:  ingen opphavsrettigheter (No copyrights).
- Snegl, leopard, banan og stjerne av [Noto emoji by Google](https://emoji.aranja.com/), inkluderer:
  * banana.png
  * leopard.png
  * snail.png
  * star.png
  - Emoji-grafikk: Noto Emoji av Google, lisensiert under Apache License 2.0
- VT323 font av [Peter Hull](https://fonts.google.com/specimen/VT323), inkluderer:
  * VT323-Regular.ttf
  - VT323 Regular (c) 2011 The VT323 Project Authors (peter.hull@oikoi.com), lisensiert under SIL Open Font License, versjon 1.1
- Star Hunt Logo av Julie Sandanger (c) 2025. Alle rettigheter reservert.
  * Bestående av Font VT323, av Peter Hull, samt stjerne av Google (se over).
  



#### Lyder
- [Coin recieved / scoreUp](https://pixabay.com/sound-effects/coin-recieved-230517/)
  - Fra [Pixabay](https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=230517) (Royalty-free)
- [Damage taken / ouch-sound](https://pixabay.com/sound-effects/characterouch2-163912/)
  - Lydeffekt av [Nykoda Caston](https://pixabay.com/users/kodasworldproductions-27998106/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=163912)
  - Fra [Pixabay](https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=163912)  (Royalty-free)
- [PowerUp / eatBanana](https://pixabay.com/sound-effects/game-bonus-144751/) 
  - Fra [Pixabay](https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=230517) (Royalty-free)
- [BounceOnEnemy](https://pixabay.com/sound-effects/boing-6222/)
  - Fra [Pixabay](https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=230517) (Royalty-free
- [NewLevel / collideWithStar](https://pixabay.com/sound-effects/level-up-5-326133/)
  - Fra [Pixabay](https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=230517) (Royalty-free)
- [GameOver](https://pixabay.com/sound-effects/brass-fail-8-a-207130/)
  - Fra [Pixabay](https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=230517) (Royalty-free)
- [MenuMusic](https://pixabay.com/sound-effects/jungle-nature-229896/)
  - Fra [Pixabay](https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=230517) (Royalty-free)
- [ActiveGameMusic](https://pixabay.com/sound-effects/humorous-loop-275485/)
  - Fra [Pixabay](https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=230517) (Royalty-free)

### MIT License

Copyright (c) 2025 by Sjette Etasje
