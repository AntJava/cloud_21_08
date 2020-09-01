import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

public class NIOClient implements Runnable {
    @Override
    public void run() {
        try {
            int port = 8189;
            InetAddress hostIP = InetAddress.getLocalHost();
            InetSocketAddress myAddress = new InetSocketAddress(hostIP, port);

            SocketChannel channel = SocketChannel.open(myAddress);
            //channel.configureBlocking(false);
            //channel.connect(new InetSocketAddress("localhost", 8189));

            //Selector sel = Selector.open();
            //channel.register(sel, SelectionKey.OP_CONNECT);
            Scanner in = new Scanner(System.in);

            System.out.println("Ready to write");
            ByteBuffer buffer = ByteBuffer.allocate(256);
            //buffer.flip();
            while (channel.isOpen()) {
                String message = in.next();
                buffer.put(message.getBytes());
                buffer.flip();
                System.out.println("Written " + channel.write(buffer) + " bytes");
                buffer.clear();
            }

//            while (channel.isOpen()) {
//                if (sel.isOpen()) {
//                    int keys = sel.select();
//                    System.out.println(keys);
//                    if (keys > 0) {
//                        Set<SelectionKey> selectedKeys = sel.selectedKeys();
//                        for (SelectionKey sk : selectedKeys) {
//                            if (!sk.isValid()) {
//                                break;
//                            }
//                            if (sk.isConnectable()) {
//                                System.out.println("accepting");
//                                channel.finishConnect();
//                                channel.register(sel, SelectionKey.OP_WRITE);
//                                sk.interestOps(SelectionKey.OP_WRITE);
//                            }
//                            if (sk.isWritable()) {
//                                System.out.println("Ready to write");
//                                String message = in.next();
//                                SocketChannel ch = (SocketChannel) sk.channel();
//                                //FileChannel fileChannel = new FileInputStream(new File("test1.txt")).getChannel();
//                                ByteBuffer buffer = ByteBuffer.allocate(256);
//                                buffer.put(ByteBuffer.wrap(message.getBytes(),0,  message.length() -1));
//                                buffer.flip();
//                                //int bytes;
//                                //buffer.clear();
////                                int total = 0;
////                                while ((bytes = fileChannel.read(buffer)) > 0) {
////                                    total += bytes;
////                                    buffer.flip();
////                                    System.out.println("wrote " + bytes + " bytes");
//                                System.out.println("Written " + ch.write(buffer) + " bytes");
//                                    buffer.clear();
//
////                                }
////                                System.out.println("total wrote: " + total);
//                                //sk.interestOps(SelectionKey.OP_READ);
//                            }

//                        }
//                    }
//                }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Thread(new NIOClient()).start();

    }
}
