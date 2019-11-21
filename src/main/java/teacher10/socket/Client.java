package teacher10.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Source: github.com/lifeandfree/network
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", Server.SERVER_PORT);

        BufferedWriter bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Scanner scanner = new Scanner(System.in);
        String message;
        while (!(message = scanner.nextLine()).isEmpty()) {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            System.out.println("server echo " + bufferedReader.readLine());
        }
//        socket.close();
    }
}