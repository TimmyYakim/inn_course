package lesson10.task;

import lesson10.finall.UDPMulticastServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Для подключения клиента к широковещаетльной рассылке
 *
 * @author Timofey Yakimov
 */
class MulticastHandler implements Runnable {

    private MulticastSocket multicastSocket;
    private InetAddress group;

    MulticastHandler() {
        try {
            multicastSocket = new MulticastSocket(Server.MC_SERVER_PORT);
            group = InetAddress.getByName(Server.GROUP);
            multicastSocket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run()  {
        byte[] buffer = new byte[UDPMulticastServer.MAX_BUF_LENGTH];
        while (true) {
//            if (UDPMulticastClient.isStopped) {
//                break;
//            }
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                multicastSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println(msg);
        }
//        try {
//            multicastSocket.leaveGroup(group);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        multicastSocket.close();
    }
}