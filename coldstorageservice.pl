%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxserviceaccessgui, "localhost",  "TCP", "8720").
context(ctxcoldstorageservice, "localhost",  "TCP", "8720").
context(ctxsonar, "localhost",  "TCP", "8720").
context(ctxbasicrobot, "10.0.0.3",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( coldroom, ctxcoldstorageservice, "it.unibo.coldroom.Coldroom").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( serviceaccessgui, ctxserviceaccessgui, "it.unibo.serviceaccessgui.Serviceaccessgui").
  qactor( sonar, ctxsonar, "it.unibo.sonar.Sonar").
  qactor( led, ctxsonar, "it.unibo.led.Led").
