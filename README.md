# TODO: INF112 Project – *(prosjekt navn)*

## Team:
* Gruppe 6 - *"Sjette_Etasje"*
* Medlemmer: Endre H. Aspøy, Eivind H. Naasen, Julie Sandanger, Aurora Bjørnerud, Thale Marie Bjerkreim, Stine Bjordal

## Linker:
* Lenke til GitLab: https://git.app.uib.no/inf112/25v/proj/sjette-etasje
* Lenke til Jira (krever invitert tilgang): https://endre-aspoy.atlassian.net/jira/core/projects/IS/board


## Om spillet (TODO)

Hvordan spille: 
- Mål: Målet er å fullføre så mange level som mulig. Dette gjøres ved å fange en stjerne 
- komme lengst mulig, få høyest mulig score og ikke miste liv ved å komme borti monster/enemies. Man stater med ett liv.
- Kontroller: høyre/venstre piltaster for frem og tilbake.


## Kjøring (TODO)
* Kompileres med `mvn package`, benytter Apache Maven 3.6.3.
* Kjøres med `mvn exec:java`.
* Krever Java 17.0.14 eller senere.

## Kjente feil 
- mister ikke liv hvis player collider med en fiende hvis de går i samme retning
- En snegl på siste level beveger seg innimellom bak/gjennom vegger. Det virker som at dette kun skjer dersom man har spilt gjennom alle levelene en gang før.
- Hvis du holder inne hoppe knappen mens du pauser, så vil player hoppe konstant selv om du har slippet knappen.

## Credits (TODO)

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
- Snegle, leopard, banan og stjerne
  * [Noto emoji by Google] (https://emoji.aranja.com/)
  - Under Apache License 2.0

### Lyder

- coin/scoreUp
  - https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=230517
  - https://pixabay.com/sound-effects/coin-recieved-230517/

