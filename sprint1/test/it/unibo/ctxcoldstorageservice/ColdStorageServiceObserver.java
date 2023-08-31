package it.unibo.ctxcoldstorageservice;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;

import unibo.basicomm23.utils.ColorsOut;

public class ColdStorageServiceObserver {

    private CoapObserveRelation relation = null;

    private String ipaddr      = "localhost:8033" ;		//5683 default
    private String context     = "ctxwaste" ;
    private String destactor   = "waste_service" ;
    private CoapClient client = null;


    public ColdStorageServiceObserver(){
        ColorsOut.outappl("ResourceObserver | start", ColorsOut.GREEN);

        client = new CoapClient("coap://"+ipaddr+"/"+context+"/"+destactor);
    }


    public void  observe( ) {
        relation = client.observe(
                new CoapHandler() {
                    @Override public void onLoad(CoapResponse response) {
                        String content = response.getResponseText();
                        ColorsOut.outappl("ResourceObserver | code="+ response.getCode()+ "value=" + content, ColorsOut.GREEN);
                    }
                    @Override public void onError() {
                        ColorsOut.outerr("OBSERVING FAILED (press enter to exit)");
                    }
                });
    }

    public void waitUserEnd() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("ResourceObserver | press enter to end ...");
        try { br.readLine(); } catch (IOException e) { }
        System.out.println("ResourceObserver | CANCELLATION");
        relation.proactiveCancel();
    }

    public static void main(String[] args) {
        ColdStorageServiceObserver rco = new ColdStorageServiceObserver();
        rco.observe( );
        rco.waitUserEnd();
    }

}

