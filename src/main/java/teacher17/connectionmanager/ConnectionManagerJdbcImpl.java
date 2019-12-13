package teacher17.connectionmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *   Source: github.com/avsharapov/inno20Testing
 */
public class ConnectionManagerJdbcImpl implements ConnectionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManagerJdbcImpl.class);
    private static ConnectionManager connectionManager;

    private ConnectionManagerJdbcImpl() {
    }

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManagerJdbcImpl();
        }
        return connectionManager;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5433/mobile",
                    "postgres",
                    "qwerty");
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Some thing wrong in getConnection method", e);
        }
        return connection;
    }

    @Override public int get15() {
        return 15;
    }
}