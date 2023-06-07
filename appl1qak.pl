%====================================================================================
% appl1qak description   
%====================================================================================
context(ctxall, "localhost",  "TCP", "8720").
context(ctxrobotpos, "127.0.0.1",  "TCP", "8111").
 qactor( worker, ctxrobotpos, "external").
  qactor( coldstorageservice, ctxall, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( coldstorage, ctxall, "it.unibo.coldstorage.Coldstorage").
  qactor( coldstoragerobot, ctxall, "it.unibo.coldstoragerobot.Coldstoragerobot").
  qactor( serviceaccessgui, ctxall, "it.unibo.serviceaccessgui.Serviceaccessgui").
