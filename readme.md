# Hagspár
## Hópur 27

## Lýsing

Hagspár er Android app sem keyrir á Efnahagsspá Spring Web app bakenda. 

Forritið gerir notendum kleyft að búa til efnahagsspár miðað við ákveðnar forsendur. Spár má svo bæði nálgast í appinu sjálfu og í netviðmótinu Efnahagsspá

## Hvernig skal keyra Hagspár

Til þessa að þýða appið þarf að nota Gradle, annað hvort með command line eða í gegnum IDE eins og Android Studio (appið var unnið í Android Studio).

Forritið notar web appið Efnahagsspá (sjá: [Efnahagsspá](https://github.com/HBV501Group8/Verkefni)) sem bakenda.

Til þess að keyra forritið þarf því einnig að sækja Efnahagsspá (sem er Maven project), þýða það og keyra samhliða Android appinu. Hugsanlega þarf að breyta
vistfanginu sem appið notar til þess að tala við bakendann (geymt sem strengur í klasanum NetworkManager). Verkefnið er stillt þannig að vistfangið sendi fyrirspurnir
á http://10.0.2.2:8080/android/, sem er vistfangið sem notað er þegar við samskipti milli Android hermis (e. emulator) og vélarinnar sem hýsir hann.

## Skipulag forritunartexta

Forritið fylgir MVC aðferðarfræði og er sett upp skv. hefð miðað við Android forrit.

Manifest má finna í .\app\src\main

Viðmótsklasar eru geymdir í ./app/src/main/java/com/example/hagspar

Á sama stað má einnig finna þrjá pakka:

Pakkinn forecast inniheldur alla klasa sem vinna með spár - þ.e.a.s. spáhluti og klasann ForecastManager sem sækir og uppfærir spár frá bakenda (með hjálp NetworkManager). 

Pakkinn networking inniheldur klasann NetworkManager sem sér um öll bein samskipti við bakenda - þ.e.a.s. sendir fyrirspurnir og tekur við svörum.

Pakkinn adapters_utils inniheldur adapter klasa sem notaðir eru til þess að teikna inn lista, valmyndir og "loading" vikni í XML útlit.

XML útlitsskrár og skilgreiningar á strengjum má finna í .\app\src\main\res