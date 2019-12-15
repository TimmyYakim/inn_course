package lesson15.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Вспомогательный класс для создания connection с БД.
 *
 * @author Timofey Yakimov
 */
public class DBConnection {

    private static Connection connection;
    private static String url = "jdbc:h2:~/test";
    private static String password = "";
    private static String user = "sa";

    public DBConnection() {}

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

}
