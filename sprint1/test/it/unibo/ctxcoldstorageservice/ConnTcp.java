package it.unibo.ctxcoldstorageservice;



import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.interfaces.Interaction2023;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.Connection;
import unibo.basicomm23.utils.TimerForRequest;

public class ConnTcp implements Interaction2023 {
    private Interaction conn;

    public ConnTcp(String hostAddr, int port) throws Exception{
        conn = TcpClientSupport.connect(hostAddr,port,10);
        ColorsOut.outappl("ConnTcp createConnection DONE:" + conn, ColorsOut.GREEN);
     }

    @Override
    public void forward(IApplMessage msg) throws Exception {
        try {
            //ColorsOut.outappl("ConnTcp forward:" + msg   , ColorsOut.GREEN);
            conn.forward(msg.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IApplMessage request(IApplMessage msg) throws Exception {
        String answer = conn.request(msg.toString());
        return new ApplMessage(answer);
    }

    @Override
    public IApplMessage request(IApplMessage msg, int timeout) throws Exception {
        final TimerForRequest t = new TimerForRequest(timeout);
        t.start();
        (new Thread() {
            public void run() {
                try {
                    String answer = conn.request(msg.toString());
                    CommUtils.outmagenta("request with tout answer:" + answer);
                    t.setExpiredSinceAnswer(answer);
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        }).start();
        String answer = t.waitTout();
        if (answer == null) {
            throw new Exception("request timeout");
        } else {
            return new ApplMessage(answer);
        }
    }


    @Override
    public void reply(IApplMessage msg) throws Exception {
        conn.reply(msg.toString());
    }

    @Override
    public IApplMessage receive() throws Exception {
        return conn.receive();
    }


    public void close() throws Exception {

        conn.close();
    }

}

