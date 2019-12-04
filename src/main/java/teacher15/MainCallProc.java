package teacher15;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source: github.com/avsharapov/inno20/tree/master/src/stc/inno
 */
public class MainCallProc {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mobile", "postgres", "qwerty")) {

            try (CallableStatement cs = connection.prepareCall("call insert_data(?)")) {
                cs.setInt(1, 1);
                cs.execute();
            }
        }
    }
}