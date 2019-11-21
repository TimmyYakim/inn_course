package teacher10.interact.server;

import teacher10.interact.Listener;
import teacher10.interact.client.Client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Source: github.com/lifeandfree/network
 */

public class Server {
    public static final Integer SERVER_PORT = 4999;

    //TCP socket
    public static void main(String[] args) {
        Listener listenerThread = new Listener(SERVER_PORT);
        listenerThread.start();

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(10));

        try (Socket socket = new Socket("127.0.0.1", Client.CLIENT_PORT);
             BufferedWriter bufferedWriter =
                     new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            Scanner scanner = new Scanner(System.in);
            String message;
            while (!(message = scanner.nextLine()).isEmpty()) {
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}