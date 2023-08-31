package coaptester;


import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;

public class CoapJunitTester {

    public static void main(String[] args) {

        CoapClient client = new CoapClient("coap://iot.eclipse.org:5683/obs");


        CoapObserveRelation relation = client.observe(new CoapHandler() {

            @Override public void onLoad(CoapResponse response) {
                System.out.println( response.getResponseText() );
            }

            @Override public void onError() {
                System.err.println("Failed");
            }
        });

        relation.proactiveCancel();
    }



}
