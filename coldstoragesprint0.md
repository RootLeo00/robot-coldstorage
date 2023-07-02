## Introduzione
### Goal dello sprint0

- individuare un architettura logica iniziale che definisca le macro-entità del sistema e le loro interazioni
- definire un piano di lavoro iniziale 


## Requirements

I requisiti sono scritti dal committente al seguente in TemaFinale23.

## Requirements analysis

Dopo opportuni colloqui con il committente, possiano affermare che :
- <div class="kp">le operazioni di carico e di scarico della ColdRoom potrebbero essere effettuate in parallelo oppure in maniera sequenziale. Per semplicità di realizzazione, dato che il committente non ha espresso riflessioni in materia, vengono effettuate in maniera sequenziale, ma nel caso realistico esse verrebbero fatte in parallelo.</div>

- <div class="kp"> Non deve succedere che un camion, ricevuto il proprio ticket si veda rifiutata l'operazione di scarico una volta arrivato in INDOOR.</div>
- <div class="kp">E' possibile che il transport trolley non riesca a scaricare un intero truck tutto in un solo viaggio</div>
- <div class="kp">la richiesta di un ticket può avvenire mentre sono ancora in corso operazioni di scarico precedenti</div>




Dai requisiti possiamo asserire che:
-  il sistema è **distribuito** su più nodi di elaborazione;
	
- Le macroentità sono:
	- ColdStorageService
	- Transport Trolley (fisico o virtuale)
	- ServiceAcessGUI 
![[macrocomponents 1.png]]
## Problem Analysis
### Il problema delle operazioni parallele
- dato che i ticket possono essere erogati in maniera parallela è possibile che il sistema eroghi un ticket che non possa essere accettato in fase di scarico merce dato che la dimensione disponibile nella COLDROOM è stata ridotta da una operazione di scarico precedente
- per ovviare a questo problema si predispone uno **STATO GHOST** che consideri la capacita attuale della COLDROOM e tutti i ticket emessi in un dato momento che non sono stati portati a termine
### Mostrare lo stato della serviceaccessGUI
- per via della introduzione dello **STATO GOST** la ServiceAcessGUI deve mostrare lo stato attuale compreso dei ticket attivi nel sistema
### Accesso al sistema
- Il ticket ha al suo interno un "ticket number", e per ragioni di sicurezza due o più Fridge truck driver non devono poter conoscere i ticket degli altri, e non devono poter inserire i ticket degli altri né in maniera malevola né incidentale 

## Architettura logica
Il sistema è composto da:
  - ColdStorageService: prende in carico richieste di generazione ticket e richieste di invio di un camion; si interfaccia con la ColdRoom per saperne lo stato; fa partire le deposit action;
  - Transport Trolley: invia al Basic Robot la sequenza di comandi necessari per effettuare una deposit action
  - Basic Robot: esegue i comandi del Transport Trolley
  - Sonar-Led Controller: invia i comandi al led in base allo stato del robot; è Observer del Sonar di modo da rilevare la misura della distanza.
  - Sonar: effettua la misurazione e emette eventi
  - ServiceAccessGui: si interfaccia con ColdStorageService per la richiesa di ticket
  - Cold Room: aggiorna lo stato della quantità di kg
  - Scarico: entità esterna che effettua una operazione di scarico della Cold Room, diminuendo i kg presenti in essa
![[appl1qakarch.png]]
### la struttura containerizzata
- la software house possiede degli strumenti per l'interazione con il robot ddr sotto forma di progetti qak, per facilitare lo sviluppo si prevede di containerizzare questi componenti in modo da avere una infrastruttura system independent per lo sviluppo e il testing della logica applicativa.
## Piano di lavoro
prevediamo di suddividere in sprint lo sviluppo del sistema secondo il seguente elenco
#### SPRINT 1
nello sprint 1 prevediamo due fasi principali 
- prototipazione delle funzionalità base del sistema (gestione dei ticket, controllo del transport trolley), tempo stimato 1 giorno 
- creazione di una infrastruttura containerizzata per facilitare il testing tempo stimato 3 ore
si prevede di svolgere queste due fasi in parallelo in quanto non fortemente dipendenti tra loro
- test finale del sistema per mezzo della infrastruttura containerizzata tempo stimato 2 ore
#### SPRINT 2
- introduzione del alarm requirement nel prototipo sviluppato allo sprint1, tempo stimato 1 giorno
- test delle funzionalità introdotte tempo stimato 2 ore
#### SPRINT 3
- introduzione della  la GUI nel sistema tempo stimato, 1 giorno
- test della GUI del sistema, tempo stimato 2 ore
- test finale del sistema nella sua interezza, tempo stimato 2 ore
#### SPRINT 4
- deployment su robot fisico, tempo stimato 2 giorni

## Test plan
si prevede di testare le seguenti funzionalità del sistema
- i movimenti del transport trolley all'interno della mappa secondo i requisiti
- la correttezza dello stato della  COLDROOM durante le fasi di richiesta del ticket e di scarico merce
### realizzazione mediante eventi 
per implementare i test si prevede di sfruttare la generazione degli eventi da parte del transport trolley e della cooldroom in modo da essere il meno invasivi possibile sul sistema 
```
inserire codice dei test per mezzo di junit
```
