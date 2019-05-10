# Connect4

Aquest repositori conté el joc Connect 4 implementant en Android.

## SplashScreen
Aquesta aplicació en executar-se ens mostrés una pantalla de càrrega on veurem el nom de l'aplicació i el nom dels creadors.
Aquesta pantalla dura 1 segon i a continuació ira en la pantalla d'Inici.

## Pantalla d'Inici
Trobarem 3 botons:
- Botó d'Ajuda: on ens portés a una pantalla on ens mostrés la informació per a ajudar a l'usuari a com funciona l'aplicació
i com funciona el Connect 4.
- Botó Sortir: aquest botó el que farà és tancar l'aplicació.
- Botó Començar partida: Aquest botó ens portés a la pantalla de configuració.

## Pantalla de Configuració de la partida
En aquesta pantalla trobarem tota la configuració que podem fer sobre el joc.

Primer de tot podrem introduir el nom del jugador, en cas de no introduir el nom l'aplicació ens mostrés un toast
dient que és necessari introduir un nom abans de començar la partida. També podrem introduir un altre jugador per a poder jugar contra un altre.

Una altra opció de configuració és triar la graella. Les opcions són de 7 columnes per a totes i només canvia les files. I per últim
l'altra opció és si volem tenir un temps màxim perquè algun jugador guanyi.

## Pantalla de Joc
Aquí podrem veure el joc. Depenent de les configuracions la pantalla canviés. Si hem seleccionat el control de temps el temps és pondrà en vermell i ira baixant fins a 0. Si el temps arriba a 0, el joc acaba. Els noms dels jugadors estan al costat del color de la fitxa que se li han assignat. Les fletxes on triarem on volem que vagi la fitxa ens indica de qui és el torn depenent del color que sigui. Per últim també podem llevar la cancion de fons premut la icona de música.

Una vegada la partida acabi ens mostrés un toast modificat depenent que hagi passat i ens portés a la pantalla de resultats.

## Pantalla de Resultats
Ens mostrés el resultat de la partida, la data del dispositiu mòbil i per últim un edit text on podrem introduir el correu electrònic si volem que ens enviï els resultats per correu electrònic. En cas de prémer el botó d'enviar e-mail si l'edit text no està emplenat, ens saltés un toast on ens informés que és necessari per a enviar un e-mail.

Els altres dos botons un ens portés a la pantalla de configuració per a tornar a jugar, mentre que l'altre tancar la pantalla.

## Millora intuïció pels usuaris
Cada vegada que un usuari prem un botó és reproduirà un so per a indiciar a l'usuari que ha premut un botó. També com hem
comentant anteriorment els toast estan modificats, hi ha toast de color vermell on indica que no pots avançar si no completes
els passos que t'indica el missatge del toast o també que has perdut en la partida. Un altre color és el color verd on ens indiqués
que hem guanyat i el nom del jugador. I per últim el color blau on ens indicarà que ningú ha guanyat.
