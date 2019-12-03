package lesson10.task;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client
 *
 * @author Timofey Yakimov
 */
public class Client implements Runnable {

    private static Socket clientSocket;

    public static void main(String[] args) {

        Thread mh = new Thread(new MulticastHandler());
        mh.start();
        try (Socket socket = new Socket(InetAddress.getLocalHost(), Server.SERVER_PORT);
             BufferedWriter bufferedWriter = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream()))) {
            clientSocket = socket;
            Thread t = new Thread(new Client());
            t.start();

            Scanner scanner = new Scanner(System.in);
            String message;
            while (!(message = scanner.nextLine()).isEmpty()) {
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                if (message.equals(Server.sessionEnd)) {
                    mh.stop();
                    t.stop();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отдельный поток на чтение индивидуальных сообщений
     */
    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while (true) {
                if ((message = bufferedReader.readLine()) != null) {
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}