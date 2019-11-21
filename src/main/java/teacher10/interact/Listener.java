package teacher10.interact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Source: github.com/lifeandfree/network
 */

public class Listener extends Thread {
    private int port;

    public Listener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}