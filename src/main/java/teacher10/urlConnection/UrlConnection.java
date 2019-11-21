package teacher10.urlConnection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.List;
import java.util.Map;

/**
 * Source: github.com/lifeandfree/network
 */
public class UrlConnection {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://yandex.ru/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage());

        /*(InputStream) connection.getContent()*/
        try (InputStream in = new BufferedInputStream(connection.getInputStream())) {
            int t;
            while ((t = in.read()) != -1) {
                System.out.print((char) t);
            }
        } finally {
            connection.disconnect(); // рвем соединение
        }
        System.out.println("\nЧтение заголовков");
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            System.out.println(header.getKey() + " = " + header.getValue());
        }
    }
}
