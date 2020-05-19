package nio.socket;

import java.net.*;
import java.io.*;

class MyServer {
    public static void main(String args[]) throws Exception {
        ServerSocket ss = new ServerSocket(9000);
        while (true) {
            Socket s = ss.accept();
            consumeClient(s);
        }
    }

    private static void consumeClient(Socket s) {
        new Thread(new WorkThread(s)).start();
    }

    private static class WorkThread implements Runnable {
        private Socket s;

        public WorkThread(Socket socket) {
            this.s = socket;
        }

        @Override
        public void run() {

            try {
                DataInputStream din = new DataInputStream(s.getInputStream());
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());

                String str = "";
                while (!str.equals("stop")) {
                    str = readClient(din);
                    writeToClient(dout);
//                    Thread.sleep(500);
                }
                din.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            }

        }
    }

    private static void writeToClient(DataOutputStream dout) throws IOException {
        dout.writeUTF("Hi this is server");
        dout.flush();
    }

    private static String readClient(DataInputStream din) throws IOException {
        String str;
        str = din.readUTF();
        System.out.println("client says: " + str);
        return str;
    }
}