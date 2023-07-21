%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxcoldstorageservice, "localhost",  "TCP", "8038").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( coldroom, ctxcoldstorageservice, "it.unibo.coldroom.Coldroom").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( sonar, ctxcoldstorageservice, "sonarHCSR04Support23").
  qactor( datacleaner, ctxcoldstorageservice, "rx.dataCleaner").
  qactor( distancefilter, ctxcoldstorageservice, "rx.distanceFilter").
  qactor( sonar23, ctxcoldstorageservice, "it.unibo.sonar23.Sonar23").
  qactor( led, ctxcoldstorageservice, "it.unibo.led.Led").
  qactor( controllerled, ctxcoldstorageservice, "it.unibo.controllerled.Controllerled").
  qactor( guimok, ctxcoldstorageservice, "it.unibo.guimok.Guimok").
