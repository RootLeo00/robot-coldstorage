%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxsonar, "127.0.0.1",  "TCP", "8040").
context(ctxcoldstorageservice, "localhost",  "TCP", "8038").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( coldroom, ctxcoldstorageservice, "it.unibo.coldroom.Coldroom").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( sonar, ctxsonar, "it.unibo.sonar.Sonar").
  qactor( led, ctxsonar, "it.unibo.led.Led").
  qactor( controllerled, ctxsonar, "it.unibo.controllerled.Controllerled").
