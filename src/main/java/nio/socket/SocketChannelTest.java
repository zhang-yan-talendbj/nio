package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {

    public static void main(String[] args) throws IOException {
        InetSocketAddress aa= new InetSocketAddress("127.0.0.1",8882);
        SocketChannel socketChannel = SocketChannel.open(aa);
        Socket socket = socketChannel.socket();

        socketChannel.write(ByteBuffer.allocate(10));

        while (!socketChannel.finishConnect()) {

        }

        System.out.println(socketChannel.isConnected());
        System.out.println(socket.getChannel()==socketChannel);//true;

    }

}
