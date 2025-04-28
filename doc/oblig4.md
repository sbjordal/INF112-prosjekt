# Rapport – innlevering 4
## Team:
* Gruppe 6 - *"Sjette_Etasje"*
- Medlemmer og roller:
  - Endre H. Aspøy: Teamleder, utvikler
  - Thale Marie Bjerkreim: Dokumentasjonsansvarlig, utvikler
  - Aurora Bjørnerud: Kundekontakt, ansvar for user Interface, utvikler
  - Stine Bjordal: Testansvarlig, utvikler
  - Julie Sandanger: Designansvarlig, utvikler
  - Eivind H. Naasen: Arkitekt, utvikler

## Prosjektrapport:

1. **Hvordan fungerer rollene i teamet?** Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?

2. **Trenger dere andre roller?** Skriv ned noen linjer om hva de ulike rollene faktisk innebærer for dere.
- Vi ser fremdeles ikke behov for flere roller enn de vi har. Vi ser at alle rollene har kommet mer frem i denne delen
  av prosjektet.
- **Hva rollene innebærer for oss:**
  - Teamlead: (Endre)
    - Teamlead skal lede teamet på best mulig måte. Vedkommende sørger for å distribuere viktig informasjon ang prosjektet,
      samt sørge for at vi overholder tidsfrister og møter de kravene som er gitt i oppgaven.
      Personen oppretter og fordeler arbeidsoppgaver i Jira, eller delegerer i henhold til det som er naturlig.
  - Arkitekt (Eivind)
    - Arkitekten er ansvarlig for å sørge for at spillet henger sammen. Med dette menes det at vedkommende setter opp et
      forslag til arkitektur for spillet. Ens jobb er å identifisere hvilke klasser det er behov for, og hvilken
      funksjonalitet de behøver. Arkitekten laget et førsteutkast av arkitektur, som et forslag og et grunnlag vi bytter
      videre på. Etterhvert som vi har sett flere behov eller utfordringer har utvikler sparret med arkitekten om hvordan
      man skal løse utfordringene.
  - Kundekontakt (Aurora)
    - Kundekontakten skal være ansvarlig for at spillet vårt er brukervennlig. Av dette inngår det blant annet å sørge
      for at brukeren forstår hensikten med spillet, spillets regler, samt hvordan det spilles. Ting som valg av farger
      for å tilrettelegge for også fargesvake vil falle innunder denne kategorien.
  - Testansvarlig (Stine)
    - Testansvarlig sin jobb er å sørge for at koden vår har nok tester, og at testene er tilstrekkelig, dette innebærer
      blant annet at prosjektet har 75% coverage. Det faller ikke innunder rollen at personen skal skrive samtlige tester,
      men heller at vedkommende skal ha oversikt over hvor mye og hva vi tester.
  - Dokumentasjonsansvarlig (Thale)
    - Denne rollen skal sørge for at all dokumentasjon i koden vår er i henhold til standarder, samt at alt som burde
      dokumenteres er dokumentert.
  - Designansvarlig (Julie)
    - Designansvarlig skal designe det grafiske til spillet. Vedkommende skal sørge for at teamet har de ressursene man
      trenger for det grafiske. Dette innebærer å lage/hente sprites for ulike objekter og bakgrunn. Det skal også sørges
      for at alt av ressurser som hentes skal det oppgis kilder til, og at lover og regler om opphavsrettigheter følges.

3. **Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne?** Synes teamet at de valgene
   dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?


4. **Hvordan er gruppedynamikken? Er det uenigheter som bør løses?**


5. **Hvordan fungerer kommunikasjonen for dere?**


6. **Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres.**
   Dette skal handle om prosjektstruktur, ikke kode. Dere kan selvsagt diskutere kode, men dette handler ikke om
   feilretting, men om hvordan man jobber og kommuniserer.



7. Under vurdering vil det vektlegges at alle bidrar til kodebasen. Hvis det er stor forskjell i hvem som committer, må
   dere legge ved en kort forklaring for hvorfor det er sånn. Husk å committe alt. (Også designfiler).



8. Referat fra møter siden forrige leveranse skal legges ved (mange av punktene over er typisk ting som havner i referat).
   - Se Wiki

9. Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.

   1. Jira


  2. Oppgavefordeling


  3. Mål for ferdigstilling


## Krav og spesifikasjon
1. **Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang.** Er dere
   kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.


- **Oppsummert hva som er gjort siden sist:**
  - 


2. **For hvert krav dere jobber med, må dere lage 1) ordentlige brukerhistorier, 2) akseptansekriterier og 3) arbeidsoppgaver.**
   Husk at akseptansekriterier ofte skrives mer eller mindre som tester

  1.  **Bekjemp fiender ved å hoppe på dem**
  * Brukerhistorie: Som spiller trenger jeg å kunne bekjempe fiender for å gjøre det lettere å nå mål, samt kunne samle større poengsummer ved å utfordre risiko.
  * Akseptansekriterier: Spilleren kan hoppe. Fiender blir bekjempet av å bli hoppet på av spilleren. Fiender gir spilleren poeng ved bekjempelse. Mengden poeng er knyttet til typen av fienden.
  * Arbeidsoppgaver: Få spilleren til å kunne hoppe. Få fiender til å bli bekjempet av å bli hoppet på av spilleren. Få bekjempet fiender til å øke poengsummen til spilleren. Mengden økt poengsum skal bestemmes av fiende typen.

  2.  **Fiender beveger seg, og det er flere fiender**
  * Brukerhistorie: Som spiller trenger jeg ekstra utfordring angående manøvrering rundt fiender, samt flere fiender for å gjøre mål oppnåelse mer tilfredstillende.
  * Akseptansekriterier: Fiender beveger på seg i et bestemt bevegelses mønster. Flere fiender er plassert på spillbrettet.
  * Arbeidsoppgaver: Få fiender til å gå i en bestemt retning. Skift retning dersom fienden oppdager en kollisjon. Plasser flere fiender på spillbrettet.

  3.  **Nivåer lages dynamisk fra tekstfiler**
  * Brukerhistorie: Som spiller trenger jeg ulike ordninger av spillobjekter for å gi en mer varierende spillopplevelse.
  * Akseptansekriterier: En tekstfil opprettes dynamisk som inneholder ordninger av spillobjekter. Tekstfilen blir lest ved innlasting av nytt nivå.
  * Arbeidsoppgaver: Opprett tekstfiler ved hjelp av eksternt vertkøy som støtter plassering av objekter via et grafisk brukergrensesnitt. Opprett en klasse som håndterer lesing av tekstfiler ved nivå skift.

  4.  **Mulighet for å gå videre til neste nivå**
  * Brukerhistorie: Som spiller trenger jeg en mulighet for å kunne komme meg videre i spillet.
  * Akseptansekriterier: Neste nivå lastes inn når spilleren plukker opp en stjerne.
  * Arbeidsoppgaver: Opprett nytt stjerne objekt som laster inn nytt nivå ved opplukkelse. Plasser et stjerne objekt på spillbrettet.

  5.  **Når tiden er ute skal spilleren tape**
  * Brukerhistorie: Som spiller trenger jeg tidspress for å motivere meg til å fullføre hvert nivå.
  * Akseptansekriterier: En nedtelling vises på skjermen i form av sekund. Spilleren taper når nedtellingen er nådd 0 sekund.
  * Arbeidsoppgaver: Opprett en nedtelling som minsker for hvert sekund. Tegn nedtellingen på skjermen. Få spilleren til å tape når nedtellingen er nådd 0 sekund.

  6.  **Lag fiender ved hjelp av en fiende-fabrikk**
  * Brukerhistorie: Som spiller trenger jeg potensialet til å bli utfordret av et større antall fiender.
  * Akseptansekriterier: Fiender blir plassert på spillbrettet ved hjelp av en fiende-fabrikk.
  * Arbeidsoppgaver: Opprett en fiende-fabrikk som plasserer fiender på spillbrettet.

  7.  **Spilleren kan plukke opp power-ups**
  * Brukerhistorie: Som spiller trenger jeg en mulighet for å gjøre det lettere å bekjempe fiender.
  * Akseptansekriterier: Power-ups er plassert utover spillbrettet. Spilleren får fordeler når hen plukker opp power-ups.
  * Arbeidsoppgaver: Opprett en power-up i form av en banan som spilleren kan plukke opp. Bananen skal gi spilleren følgende fordeler: hoppe høyere, større fasong og ekstra liv som tapes ved angrep av fiender.

  8.  **Spill av lyd når mynter plukkes opp**
  * Brukerhistorie: Som spiller trenger jeg tilbakemelding i form av lyd for å gjøre spillet mer tilfredsstillende.
  * Akseptansekriterier: Lyd spilles av når en mynt plukkes opp.
  * Arbeidsoppgaver: Legg til en mynt-lyd som spilles av når en mynt blir plukket opp.

  9.  **Det bør være mulig å legge til nye power-ups uten å endre koden til spilleren**
  * Brukerhistorie: Som utvikler ønsker jeg å kunne legge til nye power-ups uten å endre eksisterende spiller-kode, slik at spillet blir enklere å vedlikeholde og utvide i fremtiden.
  * Akseptansekriterier: Nye power-ups kan legges til uten å endre spiller-klassen.
  * Arbeidsoppgaver: Refaktorer power-up koden på den måten slik at spiller-koden er uavhengig av power-up typen.


3. **Dersom dere har oppgaver som dere skal til å starte med, hvor dere har oversikt over både brukerhistorie, akseptansekriterier og arbeidsoppgaver, kan dere ta med disse i innleveringen også.**
  * Vi har oppnådd alle kravene våres for både MVP og post-MVP. Vi har derfor ingen planer om å legge til mer funksjonalitet som krever brukerhistorier.

4. **Forklar kort hvordan dere har prioritert oppgavene fremover**
   * 

5. **Har dere gjort justeringer på kravene som er med i MVP?** Forklar i så fall hvorfor. Hvis det er gjort endringer i rekkefølge utfra hva som er gitt fra kunde, hvorfor er dette gjort?

  - Vi har ikke endret på mvp krav siden sist innlevering.

6. **Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang.**


7. Husk å skrive hvilke bugs som finnes i de kravene dere har utført (dersom det finnes bugs).

- Bugs:
  - Hvis du holder inne hoppe knappen mens du pauser, så vil player hoppe konstant selv om du har slippet knappen.



## Produkt og kode
#### "Dette har vi gjort siden sist:"
* Bugs:

