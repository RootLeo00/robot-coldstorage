%====================================================================================
% appl1qak description   
%====================================================================================
context(ctxall, "localhost",  "TCP", "8720").
context(ctxrobotpos, "10.0.0.3",  "TCP", "8020").
 qactor( robotpos, ctxrobotpos, "external").
  qactor( coldstorageservice, ctxall, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( coldroom, ctxall, "it.unibo.coldroom.Coldroom").
  qactor( coldstoragerobot, ctxall, "it.unibo.coldstoragerobot.Coldstoragerobot").
  qactor( serviceaccessgui, ctxall, "it.unibo.serviceaccessgui.Serviceaccessgui").
  qactor( fakeuser, ctxall, "it.unibo.fakeuser.Fakeuser").
