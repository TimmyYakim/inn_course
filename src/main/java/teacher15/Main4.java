package teacher15;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * Source: github.com/avsharapov/inno20/tree/master/src/stc/inno
 */
public class Main4 {
    private static final String INSERT_SQL = "INSERT INTO mobile "
            + "(model, price, manufacturer) VALUES (?,?,?)";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/mobile", "postgres", "qwerty")) {
            conn.setAutoCommit(false);
            try (PreparedStatement insertStmt = conn.prepareStatement(INSERT_SQL)) {
                // 1 запись
                insertStmt.setString(1, "F1");
                insertStmt.setInt(2, 200);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();

                // 2 запись
                insertStmt.setString(1, "F2");
                insertStmt.setInt(2, 400);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();

                // 3 запись
                insertStmt.setString(1, "F3");
                insertStmt.setInt(2, 800);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();

                // Создание Savepoint
                Savepoint savepoint = conn.setSavepoint("premium");

                // 4 запись
                insertStmt.setString(1, "F1");
                insertStmt.setInt(2, 200);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();

                // 5 запись
                insertStmt.setString(1, "F1");
                insertStmt.setInt(2, 200);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();


                // Rollback к savepoint
                conn.rollback(savepoint);

                // Commit транзакции
                conn.commit();
            }
        }
    }
}