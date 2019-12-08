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

    private static DBConnection dbConnection;
    private Connection connection;
    private String url = "jdbc:h2:~/test";
    private String password = "";
    private String user = "sa";


    public DBConnection() {
        try {
            Class.forName("org.h2.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
            return dbConnection;
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

}
