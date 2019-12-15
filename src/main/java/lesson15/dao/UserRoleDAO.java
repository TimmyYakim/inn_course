package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.pojo.User;
import lesson15.util.DBConnection;

import java.sql.*;

/**
 * Многие ко многим (Пользователь - Роль)
 *
 * @author Timofey Yakimov
 */
public class UserRoleDAO implements IUserRoleDAO {

    private final String dbName = "user_role";

    private final Connection connection = DBConnection.getConnection();

    public UserRoleDAO() throws SQLException, ClassNotFoundException {
    }

    @Override
    public boolean addUserRole(User user, Role role) {
        try (
             PreparedStatement statement = connection.prepareStatement(
                     "insert into " + dbName + " (user_id, role_id) values (?, ?)")){
            statement.setInt(1, user.getId());
            statement.setInt(2, role.getId());
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
