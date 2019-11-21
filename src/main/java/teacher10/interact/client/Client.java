package teacher10.interact.client;

import teacher10.interact.Listener;
import teacher10.interact.server.Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Source: github.com/lifeandfree/network
 */
public class Client {
    public static Integer CLIENT_PORT = 4998;

    public static void main(String[] args) {
        Listener listenerThread = new Listener(CLIENT_PORT);
        listenerThread.start();
        try (Socket socket = new Socket("127.0.0.1", Server.SERVER_PORT);
             BufferedWriter bufferedWriter = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream()))) {
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