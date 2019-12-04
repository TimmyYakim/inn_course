package teacher15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Source: github.com/avsharapov/inno20/tree/master/src/stc/inno
 */
public class Main2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mobile", "postgres", "qwerty");
             Statement pstmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT)) {

            connection.setAutoCommit(false);

            ResultSet rs = pstmt.executeQuery("select * from mobile");
            while (rs.next()) {
                if (rs.getString("model").equalsIgnoreCase("FRY1")) {
                    rs.updateString("model", "FRY2");
                    rs.updateRow();
                    connection.commit();
                    //rs.close();
                    System.out.println(rs.isClosed());
                }
            }
            connection.commit();
        }
    }
}