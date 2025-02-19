# Rapport – innlevering 1

## A0
### Team:
* Gruppe 6 - *"Sjette_Etasje"*
- Medlemmer: Endre H. Aspøy, Eivind H. Naasen, Julie Sandanger, 
  Aurora Bjørnerud, Thale Marie Bjerkreim, Stine Bjordal

### Kompetanse:

Thale:
Går kunstig intelligens på 3.året. Jeg har hatt INF101 og INF102, så jeg kan litt java:) Har også hatt INF264, INF265 og AIKI110.

Aurora:
Går kunstig intelligens 3. året og har hatt INF101, INF102, INF264, INF265, AIKI110.
Programmerte litt med HTML, css og Javascript på vgs i IT1 og IT2


Eivind: Jeg er 25 år og har studert tidligere. Det var "Bachelor i Spillteknologi og Simulering" hvor jeg programmerte veldig mye i C++. Jeg hadde fag som "Systemtenkning", "Spillifisering", og hadde mange programmering/matte fag. Generelt så var det mye gruppe arbeid, hvor det ene faget gikk ut på å lage våres eget spill hvor vi var 2 programmerere og 3 artister (skikkelig déjà vu her no jammen meg).

Stine:
Går datateknologi 2.år, og har hatt INF100 (er også gruppeleder nå), INF101, INF102, og tar INF222 nå.
Ingen programmeringserfaring før studiene.


Julie:
Går datasikkerhet 3.året. Av programmeringsemner har jeg blant annet hatt inf 100, 101, 102, 214, og tar nå inf237.


Endre:
Jeg heter Endre Aspøy, er 26 år og går Informatikk: Datateknologi. Har hatt INF100, 101, 115, 102, 122. Har også tidligere tatt fagskole i Maskinteknikk (120sp) der vi skrev hovedoppgave (10sp) som på mange måter ligner på denne semesteroppgaven, der vi samarbeidet med en bedrift. Har også jobbet som Maskiningeniør/konstruktør i 2,5 år, der jeg hadde ansvar for diverse prosjekter som innebar design og levering av maskiner og stålstrukturer.


## A1

## A2: Konsept

Spillnavn: (Bestemmes senere)

- Basert på spillet Super Mario Bros
- Spillfigur som kan styres: gå til høyre og venstre, og hoppe oppover (innenfor spillets ramme).
- Todimensjonal verden:
  * Plattform – horisontal flate spilleren kan stå eller gå på (inkludert «bakken»)
  * Vegg – vertikal flate som spilleren ikke kan gå gjennom
  * Spilleren beveger seg oppover ved å hoppe, og nedover ved å falle Levels, hvor grafikk tilpasses hvert level 
  * Spillet foregår i levels med økende vanskelighetsgrad

- Fiender som beveger seg og er skadelige ved berøring og treff med våpen 
- Spilleren kan samle poeng ved å plukke opp mynter, bekjempe fiender og fullføre levels 
- Spillets mål: Å komme lengst mulig og samle flest mulig poeng før spillkarakteren dør (game-over)
- Utfordringer i spillet:
  * Å bevege seg gjennom terrenget uten å falle utfor 
  * Å unngå å miste liv som følge av å bli skadet av fiender og/eller hindringer 
  * Å samle poeng

- Scoresystem:
  * Poenggjenstander: Mynter, (stjerner), (power-ups?)
  * Samle mynter 
  * Poeng for å drepe fiende 
  * Poeng for å fullføre level 
  * Poeng for å ha gjenværende tid ved fullført level 
  * Bonus:
    * Samle stjerner? Ekstra mange poeng for disse? 
    * Highscore? 
    * Bonusscore når man når 100 mynter? 
    * Bonusscore når man bekjemper en boss?

## A3
En kort beskrivelse av det overordnede målet for applikasjonen :
lage et gøy spill som forskjellige brukere kan like :)

MVP:
- Vise et spillebrett på skjermen
- Vise en spiller på spillebrettet
- Spilleren kan bevege seg
- Spilleren får poeng via et poengsystem
- En bane har en start og en slutt
- Viser fiender
- Spilleren får verre fysisk tilstand ved koalisjon med fiender
- Spilleren kan dø
- Når spiller dør kommer en slutt-skjerm der spiller kan velge å starte banen på nytt
- Når spillet åpnes møtes spiller med en start skjerm


#### MVP krav med brukerhistorie, akseptansekriterier og arbeidsoppgaver i prioritert rekkefølge

1.  **Vise et spillebrett på skjermen**
* Brukerhistorie: Som spiller trenger jeg å kunne se brettet for å spille.
* Akseptansekriterier: Programmet skal kunne kjøres samt at man skal kunne se et spillbrett.
* Arbeidsoppgaver: Lag et vilkårlig spillbrett som vises på skjermen.

2.  **Vise en spiller på spillebrettet**
* Brukerhistorie: Som spiller trenger jeg å kunne se spilleren for å spille.
* Akseptansekriterier: Når programmet kjøres skal man se noe grafisk som representerer en spiller.
* Arbeidsoppgaver: Lag en grafisk fremstilling av en spiller som plasseres på spillbrettet.

3. **Spilleren kan bevege seg**
* Brukerhistorie: Som spiller trenger jeg å kunne bevege spilleren for å utføre hensikten til spillet.
* Akseptansekriterier: Når brukeren interagerer med (eks) piltastene vil spilleren på brettet flytte seg. Spilleren skal kunne hoppe opp, falle ned, samt gå både høyre og venstre.
* Arbeidsoppgaver: I klassene det måtte gjelde skal det implementeres funksjonalitet slik at spillerens posisjon endrer seg iht. input fra brukeren eller spillets fysiske lover, endringene skal også fremstilles grafisk.

4. **En bane har en start og en slutt**
* Brukerhistorie: Som spiller trenger jeg et sted å starte et sted samt et sted å stoppe når målet er nådd.
* Akseptansekriterier: Bane skal lages.
* Arbeidsoppgaver: Designe bane(r) med start og slutt.

5. **Viser fiender**
* Brukerhistorie: Som spiller trenger jeg å se fiender for å kunne unngå evt. nedkjempe dem.
* Akseptansekriterier: En grafisk fremstilling av en fiende på skjermen som er synlig for brukeren.
* Arbeidsoppgaver:Lage fiender ved hjelp av en objektfabrikk. Fiendene skal ha en grafisk fremstilling.

6. **Spilleren får verre fysisk tilstand ved kollisjon med fiender**
* Brukerhistorie: Som spiller trenger jeg motstand for at spillet skal bli mer interessant.
* Akseptansekriterier: Den fysiske tilstanden er representert på et vis, og den skal oppdateres/ svekkes når spilleren kolliderer med fiender.
* Arbeidsoppgaver: Spillerens fysiske tilstand skal holdes styr på, samt at den skal endre seg når spilleren utfører handlinger som tilsier dette. Endringen skal fremstilles grafisk.

7. **Spilleren kan dø**
* Brukerhistorie: Som spiller trenger jeg at det å miste liv har konsekvenser.
* Akseptansekriterier: Livene til spilleren representeres på et vis, samt at endringer i variablen skal oppdateres. Når spilleren dør, dvs. ikke har flere liv igjen, skal en form for spilltilstand endres til “gameover” eller lignende.
* Arbeidsoppgaver: Implementere antall liv spilleren har. Når et liv mistes skal variablen endres, samt at det skal innføres funksjonalitet slik at når spilleren er tom for liv endres statusen til spillet til “gameover”.

8. **Spilleren får poeng via et poengsystem**
* Brukerhistorie: Som spiller trenger jeg å få poeng for å kunne sammenligne resultatet med tidligere resultater.
* Akseptansekriterier: Når spilleren utfører en handling som å samle mynter eller drepe fiender, skal en poengsum oppdateres. Tidligere poengsummer skal lagres i en fil, og spilleren skal kunne sammenligne egen prestasjon mot tidligere prestasjoner.
* Arbeidsoppgaver: I klassen det måtte gjelde skal spillerens totale antall poeng holdes styr på,
  denne variablen skal oppdateres når spilleren utfører handlinger som m
  dette. Poengsummen skal fremstilles grafisk til brukeren.

9. **Når spiller dør kommer en slutt-skjerm der spiller kan velge å starte banen på nytt**
* Brukerhistorie: Som spiller trenger jeg å kunne prøve på nytt dersom jeg mislyktes.
* Akseptansekriterier: Sluttskjerm vises med eventuell statistikk over poeng/cash(?)/hvor langt den kom(?). Det skal være instruksjon på skjermen om hvordan man starter et nytt spill. Dersom spiller følger instruksjonene skal et nytt spill starte, og alt av poeng/cash(?)/liv/ spiller status være resatt.
* Arbeidsoppgaver: Lage en sluttskjerm som skal vises når spillet er over. Skjermen skal inneholde poengsummen spilleren oppnådde ved siste spill, sammenligning mot tidligere spill, samt instruksjoner om hvordan man kan starte spillet igjen og prøve på nytt.

10. **Når spillet åpnes møtes spiller med en start skjerm**
* Brukerhistorie: Som spiller trenger jeg å vite hvordan man skal spille spillet.
* Akseptansekriterier: En startskjerm vises når prosjektet kjøres. Den inneholder instruksjoner om hvordan spille spillet.
* Arbeidsoppgaver: Lage startskjerm med instruksjoner/informasjon om hvordan spiller skal spille spillet. Av dette inngår målet med spiller, funksjonaliteten til karakteren, samt hvordan man starter spillet.


### Team prosess

- ukentlige møter på onsdager, torsdager og mandager
  - ca 2t hver
- benyttet kanban i Jira
- kommunikasjonskanal - Discord
  - til kommunikasjon mellom møter og ellers
- fordeler oppgaver på møte, 	
  - Hva har blitt gjort siden sist møte,
  - hva skal bli gjort i dag?
  - hva skal bli gjort til neste møte?
  - store oppgaver legges i Jira, små blir gjort under møte, noteres i møtereferat
- oppfølging
  - dersom en sitter fast må det nevnes i discord eller på møter
- Rolle/ansvars fordeling
  - har fordelt områder for hovedansvar, ikke spikret i stein, og vi hjelper hverandre.
  - **Teamleader: Endre**
    - kanban/Jira
    - holde styr på krav semesteroppgaven stiller
    - tilleggsoppgaver:
      - lyd - freesound.org
      - musikk?
  - **Designer / grafisk: Julie**
    - sprites
    - bakgrunn
  - **User interface: Aurora**
    - Sjekke at det er brukervennlig?
  - **Utvikler: Thale**
    - Implementer funksjonaliteter.
  - **Arkitekt: Eivind**
    - Passer på at all kode henger i sammen og gir mening.
    - Holder styr på klasser og strukturen til prosjektet.
  - **Tester: Stine**
    - Passe på at det blir skrevet nok tester.
    - Tenke over hvilke grensetilfeller som finnes
    - Brukertesting (sammen med Eivind)
  - **Dokumentasjon/kommentering: Thale**
    - dokumentasjon i kode


## A4

- (Eivind) har satt opp Box2D og LibGDX
- Alle har laget egne Branches og har satt seg inn i rammeverk og eksperimentert med koden
  - har testet diverse git kommandoer


## A5

Oppsummert:
- har hatt litt dårlig oversikt over frist og krav på oblig1 før siste uken
  - litt vanskelig å forstå hva som var oblig1 og hva den krevde (Oppgaveteksten uoversiktelig/ eller ikke satt oss godt nok inn i det)
  - Endre ser nøyere på oppgave krav og tidsfrister til senere
- Box2d vil være stor del av programmet vi lager
- kan bli vanskelig med å få skylapper under koding, oppgaven krever mer enn bare programmering
  - men ønsker ikke ett "dårlig" spill
- Kan bli mye på Eivind om han skal definere hele akritekturen (UML-diagram)
  - vil komme med forslag, men ønsker en bekreftelse fra teamet
- må ta en felles forventningsavklaring på ambisjoner vi har for oppgaven
- må bli flinkere til å identifisere flaskehalser (en pers bør ikke jobbe med noe for at resten kan komme videre)
- vi må si i fra om vi føler vi gjør for mye/ for lite
  - hvordan dokumenteres/ vurderes mengde arbeider per team medlem?
- Fremover kan vi ha ulike møtereferenter på møtene
- Skal se videre på (par)programmering
- Fungerte veldig bra å dele opp konkrete oppgaver
