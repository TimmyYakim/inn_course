package lesson16.dao;

import lesson16.pojo.Role;
import lesson16.pojo.User;
import lesson16.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Многие ко многим (Пользователь - Роль)
 *
 * @author Timofey Yakimov
 */
public class UserRoleDAO implements IUserRoleDAO {

    private static final Logger logger = LogManager.getLogger(UserRoleDAO.class);

    private final String dbName = "user_role";

    private final Connection connection = DBConnection.getConnection();

    public UserRoleDAO() throws SQLException {
    }

    @Override
    public boolean addUserRole(User user, Role role) {
        logger.info("Start adding user_role");
        try (
             PreparedStatement statement = connection.prepareStatement(
                     "insert into " + dbName + " (user_id, role_id) values (?, ?);")){
            connection.setAutoCommit(false);
            statement.setInt(1, user.getId());
            statement.setInt(2, role.getId());
            statement.executeUpdate();
            connection.commit();
            logger.info("Link between user and role was created");
        } catch (SQLException e) {
            logger.error("Runtime error ", e);
            return false;
        }
        return true;
    }
}
