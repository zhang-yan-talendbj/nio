package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelTest {
    public static final String GREETING = "Hello I must be going.\r\n";

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ServerSocket serverSocket = ssc.socket();
        serverSocket.bind(new InetSocketAddress(8882));
        ssc.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());

        while (true) {
            System.out.println("Waiting for connections");
            SocketChannel sc = ssc.accept();
            if (sc==null) {
                // no connections, snooze a while
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: "
                        + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
