package lesson16.dao;

import lesson16.pojo.Role;
import lesson16.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Роль
 *
 * @author Timofey Yakimov
 */
public class RoleDAO implements IRoleDAO {

    private static final Logger logger = LogManager.getLogger(RoleDAO.class);

    private final String dbName = "role";

    private final Connection connection = DBConnection.getConnection();

    public RoleDAO() throws SQLException {
    }

    @Override
    public Role addRole(Role role) {
        logger.info("add role " + role.getName());
        try (PreparedStatement statement = connection.prepareStatement(
                     "insert into " + dbName + " (name, description) values (?, ?);", Statement.RETURN_GENERATED_KEYS)){
                connection.setAutoCommit(false);
                statement.setString(1, role.getName());
                statement.setString(2, role.getDescription());
                statement.executeUpdate();
                connection.commit();
                ResultSet result= statement.getGeneratedKeys();
                if (result.next()) {
                    role.setId(result.getInt(1));
                }
                logger.info("Role was successfully added");
        } catch (SQLException e) {
            logger.error("SQLException ", e);
            e.printStackTrace();
            return null;
        }
        return role;
    }

}
