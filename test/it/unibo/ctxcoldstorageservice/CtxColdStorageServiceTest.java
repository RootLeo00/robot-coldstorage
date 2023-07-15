import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.Test;
public class CtxColdStorageServiceTest{


    @Test
    public void mainUseCaseTest(){
        //connect to port
        try{
        Socket client= new Socket("localhost", 8038);
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //send message
        out.write("msg(storefood,Request,tester,coldstorageservice,storefood( 10 ),1)");
        //wait for response
        String response= in.readLine();
        //aspected ticketaccepted reply
        assertTrue(response.contains("ticketaccepted"));
        //some string manipulation to get parameters from response
        String ticket= response.split(",")[4].split("(")[1];
        String secret= response.split(",")[5];
        out.write("msg(sendticket,Dispatch,tester,coldstorageservice,sendticket( "+ticket+","+secret+" ),2)");
        response= in.readLine();
        //aspected chargetaken dispatch
        assertTrue(response.contains("chargetaken"));
        }catch(Exception e){
            System.out.println(e.getStackTrace());
        }



    }
}
