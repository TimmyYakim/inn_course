package lesson16.util;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Вспомогательный класс для создания connection с БД.
 *
 * @author Timofey Yakimov
 */
public class DBConnection {

    private static BasicDataSource dataSource;
    private static String url = "jdbc:postgresql://...";
    private static String driver = "org.postgresql.Driver";
    private static String password = "...";
    private static String user = "...";

    public DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(url);
            dataSource.setDriverClassName(driver);
            dataSource.setUsername(user);
            dataSource.setPassword(password);
        }
        return dataSource.getConnection();
    }

}
