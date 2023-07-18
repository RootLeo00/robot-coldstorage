
## Introduzione
### Goal conseguiti nello sprint1
- prototipazione del core business ColdStorageService + TransportTrolley
### architettura logica sprint0
![[coldstorageservicearchsprint0V4.png]]
### Goal dello sprint3
- introduzione del alarm requirement nel prototipo sviluppato allo sprint1


## Requirements

The system includes a Sonar and a Led connected to a RaspberryPi.
The Sonar is used as an ‘alarm device’: when it measures a distance less that a prefixed value **DLIMT**, the transport trolley must be stopped; it will be resumed when Sonar detects again a distance higher than **DLIMT**.

The Led is used as a _warning devices_, according to the following scheme:
> - the Led is **off** when the transport trolley is at HOME
>     
> - the Led **blinks** while the transport trolley is moving
>     
> - the Led is **on** when transport trolley is stopped.

SERVICE USER STORY
5) While the transport trolley is moving, the [Alarm requirements](file:///home/leo/github/sw-eng/issLab23/iss23Material/html/TemaFinale23.html#alarm-requirements) should be satisfied. However, the transport trolley should not be stopped if some prefixed amount of time (**MINT** msecs) is not passed from the previous stop.

## Requirements analysis
- per RaspberryPi si intende la versione 4 B
- Per Sonar si intende ...
- Per Led si intende ...
Da chiedere al committente: 
- dov'è il sonar?
- il transport trolley è in home qualsiasi sia la direzione del muso del robot ?


### Architettura logica requisiti


## Problem Analysis

### Controller Sonar-Led

### Misurazione continua Sonar

### Alarm Transport Trolley
per fermare il robot si fa affidamento al comando "alarm", già formalizzato dai messaggi presenti in questa documentazione: [BasicRobot23.html](file:///home/leo/github/sw-eng/issLab23/iss23Material/html/BasicRobot23.html#basicrobot23-messaggi)

### Responsabilità del ColdStorageService
Nessuna

### Timer MINT




## Architettura logica dopo analisi del problema
Il sistema è composto da:
  - *ColdStorageService*: prende in carico richieste di generazione ticket e richieste di invio di un camion; si interfaccia con la ColdRoom per saperne lo stato; fa partire le deposit action;
  - *Transport Trolley*: invia al Basic Robot la sequenza di comandi necessari per effettuare una deposit action
  - *Basic Robot*: esegue i comandi del Transport Trolley
  - *ServiceAccessGui*: si interfaccia con ColdStorageService per la richiesa di ticket
  - *Cold Room*: aggiorna lo stato della quantità di kg
  - *Sonar* : effettua la misurazione e emette eventi. E' inserito in un contesto diverso perchè è specificato nei requisiti che è presente un Raspberry alla quale sarà collegato.
  - *Led*: viene inserito nello stesso contesto di Sonar
  - *ControllerSonarLed*: entità che comunica con Led, Sonar e Transport Trolley
  ![[coldstorageservicearchsprint1.png]]

## Test plan
si prevede di testare le seguenti funzionalità del sistema


### Test effettuati in Junit



<div style="background-color:rgba(86, 56, 253, 0.9); width:60%;text-align:left;color:white">
        By Caterina Leonelli email: caterina.leonelli2@studio.unibo.it,
        GIT repo: https://github.com/RootLeo00/sw-eng.git
    </div>

![[cate_img.png]]

<div style="background-color:rgba(86, 56, 253, 0.9); width:60%;text-align:left;color:white">
By Matteo Longhi email: matteo.longhi5@studio.unibo.it
GIT repo: https://github.com/carnivuth/iss_2023_matteo_longhi.git
</div>


![[longhi_img.png]]