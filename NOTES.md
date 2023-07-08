il nostro robotpos è il transport trolley.

il messaggio charge taken è un dispatch.
scambio request response: camionista che richiede il ticket con due possibili reply

coldstorage è un attore. Infatti coldstorageservice non ha la responsabilità di gestire lo scarico del frigorifero. Decidere: possiamo scaricare/caricare in parallelo oppure uno alla volta? Cioè, c'è una porta oppure due porte. Appuntare questo come keypoint dell'analisi (cioè c ome punto critico

da chiedere:
- il frigo è indipendente? Oppure è gestito da coldstorageservice
R: 
- serviceaccessgui va nello stesso contesto di coldstorageservice

D: cosa si intende per reject del camion?
R: nel caso l'app dica che non c'è spazio, il camion non verrà inviato. Quando viene bloccato un camion, poi rimane in attesa che venga liberato. Volendlo se no, il committente può avere una gui con lo stato del frigo, cowsì se il frigo è pieno, allora non gli conviene fare la richiesta.

D: cosa c'è in service status gui
R: tickets scartati, stato del frigo

D: si possono emettere dei ticket anche quando il robot sta portando a termine un'altra procedura?
R: si

D: le info della gui sono condivise da tutti i camionisti ?
R: i ticket devono essere visibili solo ai proprietari. Lo stato del frigo invece può essere visto da tutti

D: il robot quanti kg può trasportare alla volta ?
R: si, potrebbe darsi. Se arriva un carico di 100 kg, ma il robot ne può trasportare 50 alla volta, allora il robot dovrà fare due giri.

D: 
R:
-

#todo
- trovare il modo di allacciare il controller del sonar a ColdStorageRobot
- memorizzare le richieste di charge taken quando il robot è occupato: 
	- uso discardMsg Off 

D: quanto è lo spazione del frigo?
### REQUIREMENTS SPRINT1
The transport trolley is used to perform a deposit action that consists in the following phases:
- pick up a food-load from a Fridge truck located on the INDOOR
- go from the INDOOR to the PORT of the ColdRoom
- deposit the food-load in the ColdRoom
### SERVICE USER STORY
- A Fridge truck driver uses the ServiceAcessGUI to send a request to store its load of FW kg. If the request is accepted, the driver drives its truck to the INDOOR of the service, before the ticket exipration time TICKETTIME.
- When the truck is at the INDOOR of the service, the driver uses the ServiceAcessGUI to enter the ticket number and waits until the message charge taken (sent by the ColdStorageService) appears on the ServiceAcessGUI. At this  point, the truck should leave the INDOOR.
- When the service accepts a ticket, the transport trolley reaches the INDOOR, picks up the food, sends the charge taken message and then goes to the ColdRoom to store the food.
- When the deposit action is terminated, the transport trolley accepts another ticket (if any) or returns to HOME.

## domande sprint1
il transporttrolley puo muoversi verso la coldroom anche mentre coldstorageservice sta inviando chargetaken
