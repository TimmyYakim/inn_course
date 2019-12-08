package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Роль
 *
 * @author Timofey Yakimov
 */
public class RoleDAO implements IRoleDAO {

    private final String dbName = "role";

    private final Connection connection = DBConnection.getInstance().getConnection();

    @Override
    public boolean addRole(Role role) {
        try (PreparedStatement statement = connection.prepareStatement(
                     "insert into " + dbName + " values (?, ?, ?)")){
                statement.setInt(1, role.getId());
                statement.setString(2, role.getName());
                statement.setString(3, role.getDescription());
                statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
