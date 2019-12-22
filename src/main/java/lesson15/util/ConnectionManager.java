package lesson15.util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Вспомогательный класс для создания connection с БД.
 *
 * @author Timofey Yakimov
 */
public class ConnectionManager implements Closeable {

    private static Connection connection;
    private static String url = "jdbc:postgresql://localhost:5432/inno_lesson15";
    private static String password = "85173219";
    private static String user = "usr";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    @Override
    public void close() throws IOException {
        connection = null;
    }

}
