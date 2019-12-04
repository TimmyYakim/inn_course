package teacher15;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source: github.com/avsharapov/inno20/tree/master/src/stc/inno
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mobile", "postgres", "qwerty")) {

            // Метаданные текущего подключения
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(
                    "Database: " + metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion());
            System.out.println("Driver: " + metaData.getDriverName() + " " + metaData.getDriverVersion());
            System.out.println("Username" + metaData.getUserName());
            System.out.println("MaxConnection: " + metaData.getMaxConnections());
            System.out.println(
                    "TYPE_FORWARD_ONLY resultSet: " + metaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
            System.out.println("TYPE_SCROLL_INSENSITIVE resultSet: " + metaData
                    .supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
            System.out.println(
                    "TYPE_SCROLL_SENSITIVE resultSet: " + metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
            System.out.println("CONCUR_READ_ONLY resultSet: " + metaData
                    .supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY));
            System.out.println("CONCUR_UPDATABLE resultSet: " + metaData
                    .supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE));
            System.out.println("CLOSE_CURSORS_AT_COMMIT resultSet: " + metaData
                    .supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT));
            System.out.println("HOLD_CURSORS_OVER_COMMIT resultSet: " + metaData
                    .supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT));
            System.out.println("\n");
            // Набор данных поддерживаемых типов
            ResultSet rs = metaData.getTypeInfo();
            System.out.println("Набор примитивных типов, поддерживаемых данным типом приложения\n");
            while (rs.next()) {
                //System.out.println(rs.getString(1));
            }
        }
    }
}