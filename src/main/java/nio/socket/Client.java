package nio.socket;// A Java program for a Client

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private DataInputStream server=null;

    // constructor to put ip address and port
    public Client(String address, int port) {
        // establish a connection
            Scanner scanner = null;
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            scanner = new Scanner(System.in);

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());

            server = new DataInputStream(socket.getInputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }

        // string to read message from input
        String line = "";
        line=scanner.nextLine();

        // keep reading until "Over" is input
        while (!line.equals("Over")) {
            try {
                line =scanner.nextLine();
                String serverMsg = server.readUTF();
                System.out.println(serverMsg);
                out.writeUTF(line);
                out.flush();

            } catch (IOException i) {
                System.out.println(i);
            }
        }

        // close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 8881);
    }
} 
