
## Introduzione
### Goal conseguiti nello sprint1
- prototipazione del core business ColdStorageService + TransportTrolley
### architettura logica sprint1
![[coldstorageservicearchsprint0V4.png]]
### Goal dello sprint2
- introduzione della la GUI nel sistema ottenuto nello sprint1 e testing
**Si noti che lo sviluppo del progetto dello sprint2 è da intendersi come estensione del progetto dello sprint1**

## Requirements
a ServiceAccessGUI that allows an human being to see the current weight of the material stored in the ColdRoom and to send to the ColdStorageService a request to store new **FW** kg of food. If the request is accepted, the services return a ticket that expires after a prefixed amount of time (**TICKETTIME** secs) and provides a field to enter the ticket number when a Fridge truck is at the INDOOR of the service.

### SERVICE USER STORY
- A Fridge truck driver uses the ServiceAccessGUI to send a request to store its load of FW kg. If the request is accepted, the driver drives its truck to the INDOOR of the service, before the ticket expiration time TICKETTIME.
- When the truck is at the INDOOR of the service, the driver uses the ServiceAccessGUI to enter the ticket number and waits until the message charge taken (sent by the ColdStorageService) appears on the ServiceAccessGUI. At this  point, the truck should leave the INDOOR.
- When the service accepts a ticket, the transport trolley reaches the INDOOR, picks up the food, sends the charge taken message and then goes to the ColdRoom to store the food.
- When the deposit action is terminated, the transport trolley accepts another ticket (if any) or returns to HOME.

## Requirements analysis

- Per **current weight** si intende il numero di kg contenuti nella ColdRoom effettivi. Viene quindi formalizzato con un numero intero positivo.
- Dal punto 1 della service user story, si evince che ci sarà un campo in input dove l'utente può inserire il numero di kg FW per richiedere il ticket.
- Una volta che l'utente ha inserito il ticket number, non deve essere possibile per lui inserire un altro ticket prima che otttenga alcuna risposta dalla ServiceAccessGui
- Deve essere possibile per l'utente poter richiedere più ticket anche se ha inserito il ticketruck driver mandando il camion con un altro ticket. Infatti, il camionista ddeve prima guidare il camion fino a indoor e poi può inserire il ticket number nella ServiceAccessGui e non potrebbe fisicamente guidare due camion in contemporanea.

Dal precedente sprint si ricorda che:
- Non deve succedere che un camion, ricevuto il proprio ticket si veda rifiutata l'operazione di scarico una volta arrivato in INDOOR.
- la richiesta di un ticket può avvenire mentre sono ancora in corso operazioni di scarico precedenti


### Ticket
 Il ticket è stato formalizzato nello sprint1 secondo la seguente struttura
 ```java
private String ticketSecret;  
private long timestamp;  
private int kgToStore;    
``` 
 


## Problem Analysis


## Architettura logica dopo analisi del problema
Il sistema è composto da:
  - *ColdStorageService*: prende in carico richieste di generazione ticket e richieste di invio di un camion; si interfaccia con la ColdRoom per saperne lo stato; fa partire le deposit action;
  - *Transport Trolley*: invia al Basic Robot la sequenza di comandi necessari per effettuare una deposit action
  - *Basic Robot*: esegue i comandi del Transport Trolley
  - *ServiceAccessGui*: si interfaccia con ColdStorageService per la richiesa di ticket
  - *Cold Room*: aggiorna lo stato della quantità di kg

  ![[coldstorageservicearchsprint1.png]]


## Test plan
si prevede di testare le seguenti funzionalità del sistema


### Test effettuati in Junit
creare un attore che funge da fake user e nel codice Kotlin inseriamo delle Assert
- fake user manda il messaggio **storefood**, mi aspetto di ricevere il ticket
- fake user inserisce il ticket e si aspetta un dispatch di chargetaken

```Java


```

### realizzazione mediante eventi 
per implementare i test si prevede di sfruttare la generazione degli eventi da parte del transport trolley e della cooldroom in modo da essere il meno invasivi possibile sul sistema.

## PROGETTAZIONE

Tutto il codice della parte di progettazione è consultabile al seguente link [github](https://github.com/RootLeo00/robot-coldstorage/tree/main/sprint2)
### l'attore GUIMOK 
si prevede di aggiungere all'architettura logica predisposta in analisi del problema un attore **GUIMOK** per simulare il comportamento di un utente che interagisce con la main logic di sistema

### Architettura finale progettazione
![[coldstorageservicearchprogettazione.png]]


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