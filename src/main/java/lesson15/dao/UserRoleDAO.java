package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.pojo.User;
import lesson15.util.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Многие ко многим (Пользователь - Роль)
 *
 * @author Timofey Yakimov
 */
public class UserRoleDAO implements IUserRoleDAO {

    private static final Logger logger = LogManager.getLogger(UserRoleDAO.class);

    private static final String dbName = "user_role";

    private final Connection connection;

    public UserRoleDAO() throws SQLException, ClassNotFoundException {
        connection = ConnectionManager.getConnection();
    }

    public UserRoleDAO(ConnectionManager connectionManager) throws SQLException, ClassNotFoundException {
        connection = connectionManager.getConnectionReturn();
    }

    @Override
    public boolean addUserRole(User user, Role role) throws SQLException, ClassNotFoundException {
        logger.info("Start adding a link between user and role");
        if (Objects.isNull(user) || Objects.isNull(role)) {
            logger.info("No such user or role");
            return false;
        }
        UserDAO userDAO = new UserDAO();
        RoleDAO roleDAO = new RoleDAO();
        user = userDAO.getUserByLoginAndName(user.getLoginId(), user.getName());
        role = roleDAO.getRoleByNameAndDescription(Role.RoleName.valueOf(role.getName()), role.getDescription());
        try (PreparedStatement statement = connection.prepareStatement(addUserRoleQuery)){
            connection.setAutoCommit(false);
            statement.setInt(1, user.getId());
            statement.setInt(2, role.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Can't link role and user because of " + e.getClass());
            throw new SQLException("Can't link role and user because of " + e.getClass());
        }
        return true;
    }

    public static final String addUserRoleQuery = "insert into " + dbName + " (user_id, role_id) values (?, ?);";


}
