package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.util.DBConnection;

import java.sql.*;

/**
 * Роль
 *
 * @author Timofey Yakimov
 */
public class RoleDAO implements IRoleDAO {

    private final String dbName = "role";

    private final Connection connection = DBConnection.getConnection();

    public RoleDAO() throws SQLException, ClassNotFoundException {
    }

    @Override
    public Role addRole(Role role) {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return role;
    }

}
