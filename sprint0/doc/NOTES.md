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
