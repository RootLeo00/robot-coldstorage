package it.unibo.ctxcoldstorageservice;

import java.io.*;
import java.net.Socket;

import org.junit.Test;

import static org.junit.Assert.*;

public class CtxColdStorageServiceTest{


    @Test
    public void mainUseCaseTest(){
        //connect to port
        try{
        Socket client= new Socket("localhost", 8038);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //send message
        out.write("msg(storefood,Request,tester,coldstorageservice,storefood( 10 ),10)");
        out.flush();
        //wait for response
        String response= in.readLine();
        //aspected ticketaccepted reply
        assertTrue(response.contains("ticketaccepted"));
        //some string manipulation to get parameters from response
        String ticket= response.split(",")[4].split("(")[1];
        String secret= response.split(",")[5];
        out.write("msg(sendticket,Request,tester,coldstorageservice,sendticket( "+ticket+","+secret+" ),20)");
        response= in.readLine();
        //aspected chargetaken dispatch
        assertTrue(response.contains("chargetaken"));
        }catch(Exception e){
            fail();
            System.out.println(e.getStackTrace());
        }



    }
}
