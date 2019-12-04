package teacher15;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source: github.com/avsharapov/inno20/tree/master/src/stc/inno
 */
public class Main3 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mobile", "postgres", "qwerty")) {

            JdbcRowSet jdbcRS = RowSetProvider.newFactory().createJdbcRowSet();
            jdbcRS.addRowSetListener(new ExampleListener());
            jdbcRS.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
            String sql = "SELECT * FROM mobile WHERE id = ?";
            jdbcRS.setCommand(sql);
            jdbcRS.setInt(1, 1);
            jdbcRS.execute();
            while (jdbcRS.next()) {
                System.out.println("id = " + jdbcRS.getString(1));
                System.out.println("model = " + jdbcRS.getString(2));
                System.out.println("price = " + jdbcRS.getString(2));
            }
        }
    }
}