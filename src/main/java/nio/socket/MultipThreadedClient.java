package nio.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MultipThreadedClient implements Runnable {


    private final int port;

    public MultipThreadedClient(int port) {
        this.port=port;
    }

    public void run() {
        while (true) {

            try {
                Socket s = new Socket("localhost", port);

                DataInputStream din = new DataInputStream(s.getInputStream());
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());

                String str2 = "";
                dout.writeUTF("hi server");
                dout.flush();
//                str2 = din.readUTF();
//                System.out.println("Server says: " + str2);

                dout.close();

                s.close();

                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new Thread(new MultipThreadedClient(8881)).start();
//        new Thread(new MultipThreadedClient(8881)).start();
//        new Thread(new MultipThreadedClient(8881)).start();
        try {
            Thread.sleep(10000*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
//        server.stop();
    }
}