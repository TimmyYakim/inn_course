package lesson10.task;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Задание 1. Разработать приложение - многопользовательский чат, в котором участвует произвольное количество клиентов.
 * Каждый клиент после запуска отправляет свое имя серверу. После чего начинает отправлять ему сообщения.
 * Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
 * Задание 2.  Усовершенствовать задание 1:
 * a.      добавить возможность отправки личных сообщений (unicast).
 * b.      добавить возможность выхода из чата с помощью написанной в чате команды «quit»
 *
 *
 * @author Timofey Yakimov
 */
public class Server implements Runnable {

    public static final Integer SERVER_PORT = ...;
    private final Socket clientSocket;
    // пул клиентов, имеющих подключение к серверу
    private static ConcurrentMap<String, Socket> clients = new ConcurrentHashMap<>();
    // счетчик клиентов
    private static int clientsCounter;
    private String clientName;

    public static String unicastPrefix = "To_"; // начало...
    public static String unicastPostfix = "_To."; // ... и конец имени адресата
    public static String sessionEnd = "quiet"; // условие для выхода клиента из сессии

    private static MulticastSocket multicastSocket; // сокет широковещательной рассылки
    private static InetAddress group;
    public static String GROUP = "230.0.0.0"; // группа широковещаетельной рассылки
    public static int MC_SERVER_PORT = ...; // порт широковещаетельной рассылки

    public Server(Socket socket) throws IOException {
        // привязываем нового клиента
        clientSocket = socket;
        clientName = "Client_" + clientsCounter++;
        clients.put(clientName, socket);
        // создаем группу широковещаетельной рассылки
        multicastSocket = new MulticastSocket(MC_SERVER_PORT);
        group = InetAddress.getByName(GROUP);
        multicastSocket.joinGroup(group);
    }

    @Override
    public void run() {
        try {
            System.out.println("New client " + clientName + " was connected");
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;
            while ((message = reader.readLine()) != null) {
                // если клиент желаетпокинуть сессию
                if (message.equals(sessionEnd)) {
                    this.clientSocket.close();
                    System.out.println("Socket " + clientSocket.toString() + " was closed");
                    break;
                }
                // сообщение от клиента
                System.out.println(this.clientName + ": " + message);
                // если клиент передает сообщение конкретному адресату...
                if (message.startsWith(unicastPrefix)) {
                    String[] strings = message.substring(unicastPrefix.length()).split(unicastPostfix);
                    String receiverName = strings[0];
                    Socket receiverSocket = clients.get(receiverName);
                    BufferedWriter bufferedWriter = new BufferedWriter(
                            new OutputStreamWriter(receiverSocket.getOutputStream()));
                    bufferedWriter.write(message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    System.out.println("To " + receiverName + " was sent: " + message);
                    // иначе рассылаем всем
                } else {
                    writeToAllClients(message);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Широковещательная рассылка
     * @param str сообщение
     * @throws IOException
     */
    private static void writeToAllClients(String str) throws IOException {
        byte[] msg = str.getBytes();
        DatagramPacket packet = new DatagramPacket(msg, msg.length, InetAddress.getByName(GROUP), MC_SERVER_PORT);
        multicastSocket.send(packet);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new Server(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}