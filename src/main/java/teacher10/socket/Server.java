package teacher10.socket;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Source: github.com/lifeandfree/network
 */
public class Server {
    public static final Integer SERVER_PORT = 4999; // Прослушиваемый порт
    public static ServerSocket serverSocket = null; //TCP socket

    public static void main(String[] args) throws IOException {

        serverSocket = new ServerSocket(SERVER_PORT,0, InetAddress.getByName("127.0.0.1"));
        Socket socket = serverSocket.accept(); // Слушать!

        InputStream fromClient = socket.getInputStream();
        OutputStream toClient = socket.getOutputStream();
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(fromClient));
        BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(toClient));

        String message;
        while ((message = clientReader.readLine()) != null) {
            System.out.println("Server read: " + message);
            clientWriter.write("\"" + message + "\" received \n");
            clientWriter.flush();
        }
    }
}