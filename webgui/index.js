const express = require("express");
const path = require("path");
const net = require("net");

const app = express();
const PORT = 8039;
const QAKADDRESS = "localhost";
const QAKPORT = 8038;
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

//client connection
var client = new net.Socket();
client.connect(QAKPORT, QAKADDRESS, function () {
  console.log("CONNECTED TO: " + QAKADDRESS + ":" + QAKPORT);
});

//home route
app.get("/", (req, res) => {
  res.status(200);
  res.sendFile(path.join(__dirname, "webgui.html"));
});

//storefood route
app.post("/storefood", (req, res) => {
  var fw = req.body.fwg;
  var message =
    "msg(dostorefood,request,webgui,serviceaccessgui,dostorefood(" +
    fw +
    "),1)\n";
  client.write(message, "utf-8", function () {
    var msg = "";
    client.on("data", function (buffer = Buffer.alloc(93)) {
      msg += buffer.toString();
      if (msg.length >= 90) {
        var msgType = getMessageType(msg);
        if (msgType == "ticketaccepted") {
          param = getParameters(msg);
          console.log(msg);
          res.send(
            "<h1>YOUR TICKETNUMBER IS<h1> <div>" +
              param[0] +
              "</div><h1>YOUR TICKETSECRET IS<h1> <div>" +
              param[1] +
              '</div><a href="/">return home</a>'
          );
          client.removeAllListeners("data");
        }
      } else if (msg.length >= 60) {
        var msgType = getMessageType(msg);
        if (msgType != "ticketaccepted") {
          res.send("<h1>" + msgType + '<h1><a href="/">return home</a>');
          client.removeAllListeners("data");
        }
      }
    });
  });
});
//instertticket route
app.post("/insertticket", (req, res) => {
  var ticketnumber = req.body.ticketnumber;
  var ticketsecret = req.body.ticketsecret;
  var message =
    "msg(doinsertticket,request,webgui,serviceaccessgui,doinsertticket(" +
    ticketnumber +
    "," +
    ticketsecret +
    "),1)\n";
  console.log(message);
  client.write(message, "utf-8", function () {
    var msg = "";
    client.on("data", function (buffer = Buffer.alloc(93)) {
      msg += buffer.toString();
      console.log(msg);
      if (msg.length >= 60) {
        var msgType = getMessageType(msg);
        console.log(msg);
        res.status(200);
        res.send("<h1>" + msgType + '<h1><a href="/">return home</a>');
        client.removeAllListeners("data");
      }
    });
  });
});

app.listen(PORT, (error) => {
  if (!error)
    console.log(
      "Server is Successfully Running,and App is listening on port " + PORT
    );
  else console.log("Error occurred, server can't start", error);
});

//utility methods
function getParameters(msg) {
  return msg.split("(")[2].split(",");
}

function getMessageType(msg) {
  return msg.split("(")[1].split(",")[0];
}
