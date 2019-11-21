package teacher10.interact;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Source: github.com/lifeandfree/network
 */

public class Datagramm {
    public static void main(String[] args) {
        /* UDP socket */
        try {
            byte[] data = "hello".getBytes();
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            DatagramPacket pack = new DatagramPacket(data, data.length, addr, 6226);
            DatagramSocket ds = new DatagramSocket();
            ds.send(pack);
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}