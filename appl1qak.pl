%====================================================================================
% appl1qak description   
%====================================================================================
context(ctxall, "localhost",  "TCP", "8720").
context(ctxrobotpos, "127.0.0.1",  "TCP", "8111").
 qactor( worker, ctxrobotpos, "external").
  qactor( appl, ctxall, "it.unibo.appl.Appl").
  qactor( consoleobs, ctxall, "it.unibo.consoleobs.Consoleobs").
  qactor( sonarobs, ctxall, "it.unibo.sonarobs.Sonarobs").
  qactor( obsforpath, ctxall, "it.unibo.obsforpath.Obsforpath").
  qactor( console, ctxall, "it.unibo.console.Console").
