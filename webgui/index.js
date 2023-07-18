const express = require('express');
const path = require('path');
const net=require('net');
const app = express();
const PORT = 8039;
const QAKADDRESS = 'localhost';
const QAKPORT =8038;
var client = new net.Socket();
client.connect( QAKPORT,QAKADDRESS, function() {
 
    console.log('CONNECTED TO: ' + QAKADDRESS + ':' + QAKPORT);
    // Inviamo un messaggio al server non appena il client si è connesso, il server riceverà questo messaggio dal client.
   
    
});
  
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.listen(PORT, (error) =>{
    if(!error)
        console.log("Server is Successfully Running,and App is listening on port "+ PORT)
    else 
        console.log("Error occurred, server can't start", error);
    }
);
app.get("/",(req,res)=>{
res.status(200);
res.sendFile(path.join(__dirname,"webgui.html"));

});

app.post("/storefood",(req,res)=>{
    res.status(200);
    var fw= req.body.fwg;
    var message='msg(dostorefood,request,webgui,serviceaccessgui,dostorefood('+fw+'))'
    console.log(message);
    client.write(message);
    
res.send("TODO")
    //res.send("<h1>YOUR TICKET IS<h1> <div>"+ticketcode+"</div><h1>YOUR TICKET IS<h1> <div>"+ticketsecret+"</div>")
    
    
    });


app.post("/insertticket",(req,res)=>{
    res.status(200);
    res.send("TODO")
    
    
    });