package com.unibo.serviceaccessgui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Value("${spring.application.name}")
    String appName;
    String COLDSTORAGESERVICEIPADDRESS = "127.0.0.1";
    int COLDSTORAGESERVICEPORT = 8038;

    Socket client;
    BufferedReader reader;
    BufferedWriter writer;
    /// HOMEPAGE
    @GetMapping("/")
    public String homePage(Model model) {
        String msg = "msg(howmanykgavailable,request,webgui,coldstorageservice,howmanykgavailable(ARG),1)\n";
        try {
            // sending message
            connectToColdStorageService();
            writer.write(msg);
            writer.flush();
            System.out.println("message sent");
            // handling response
            String response = reader.readLine();
            model.addAttribute("spaceleft", getParameters(response)[0]);
            model.addAttribute("arg", appName);
            return "serviceaccessgui";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

    }
    /// INSERTTICKET USECASE
    @PostMapping("/insertticket")
    public String insertticket(Model model, @RequestParam(name = "ticketnumber") String ticketnumber,
            @RequestParam(name = "ticketsecret") String ticketsecret) {
        System.out.println(ticketnumber);
        System.out.println(ticketsecret);

        String msg = "msg(sendticket,request,webgui,coldstorageservice,sendticket(" +
                ticketnumber +
                "," +
                ticketsecret +
                "),1)\n";
        try {
            // sending message
            connectToColdStorageService();
            writer.write(msg);
            writer.flush();
            System.out.println("message sent");
            // handling response
            String response = reader.readLine();
            String msgtype=getMsgType(response);
            if(msgtype.equals("requestaccepted")){
                msg = "msg(loaddone,request,webgui,coldstorageservice,loaddone(arg),1)\n";
                 writer.write(msg);
                writer.flush();
                  response = reader.readLine();
                 msgtype=getMsgType(response);
            }
            model.addAttribute("msgtype", getMsgType(response));
            return "insertticket";

            

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

    }
    /// STOREFOOD USECASE
    @PostMapping("/storefood")
    public String storefood(Model model, @RequestParam(name = "fwg") String fw) {
        // storefood request
        String msg = "msg(storefood,request,webgui,coldstorageservice,storefood(" +
                fw +
                "),1)\n";
        String response;
        try {
            // sending message
            connectToColdStorageService();
            writer.write(msg);
            writer.flush();
            System.out.println("message sent");
            // handling response
            response = reader.readLine();
            System.out.println("message read");
            System.out.println(response);
            String msgType = getMsgType(response);
            System.out.println(msgType);
            if (msgType.equals("ticketaccepted")) {
                String[] parameters = getParameters(response);
                model.addAttribute("ticketcode", parameters[0]);
                model.addAttribute("ticketsecret", parameters[1]);
                return "storefood";
            } else {
                return "error";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

    }

    //// UTILITIES////////////////////////////////
    private void connectToColdStorageService() throws IOException {

        client = new Socket(COLDSTORAGESERVICEIPADDRESS, COLDSTORAGESERVICEPORT);
        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

    }

    private String[] getParameters(String msg) {
        return msg.split("\\(")[2].split("\\)")[0].split(",");
    }

    private String getMsgType(String msg) {
        return msg.split("\\(")[1].split(",")[0];
    }
}