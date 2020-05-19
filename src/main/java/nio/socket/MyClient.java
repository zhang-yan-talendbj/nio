package nio.socket;

import java.net.*;
import java.io.*;

class MyClient {
    public static void main(String args[]) throws Exception {
        for (int i = 0; i<1000; i++) {
            new CleintThread().start();
        }

        Thread.sleep(1000*1000);
    }

    private static void writeToServer(String str, DataOutputStream dout) throws IOException {
        dout.writeUTF(str);
        dout.flush();
    }

    private static void readFromServer(DataInputStream din) throws IOException {
        String serverResponse;
        serverResponse = din.readUTF();
        System.out.println("Server says: " + serverResponse);
    }

    private static class CleintThread extends Thread {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            String name = thread.getName();
            try {
                final Socket s = new Socket("localhost", 9000);
                final DataInputStream din = new DataInputStream(s.getInputStream());
                final DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                while (true) {
                    String str = "hi this is client:" + name, serverResponse = "";
                    while (!str.equals("stop")) {
                        writeToServer(str, dout);
                        readFromServer(din);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}