package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Роль
 *
 * @author Timofey Yakimov
 */
public class RoleDAO implements IRoleDAO {

    private static final String dbName = "role";

    private final Connection connection;

    public RoleDAO() throws SQLException, ClassNotFoundException {
        connection = ConnectionManager.getConnection();
    }

    @Override
    public List<Role> getAll() throws SQLException {
        List<Role> roles = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getAllRolesQuery)) {
            ResultSet resultSet = statement.executeQuery();
            roles = getRoles(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Can't get the roles because of SQLException");
            throw new SQLException("Can't get the roles because of SQLException");
        }
        return roles;
    }

    private List<Role> getRoles(ResultSet resultSet) throws SQLException {
        List<Role> roles = new ArrayList<>();
        if (Objects.isNull(resultSet)) {
            System.out.println("Result set is null");
            return roles;
        }
        while (resultSet.next()) {
            roles.add(new Role(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return roles;
    }

    @Override
    public boolean add(Role role) throws SQLException {
        if (role == null) {
            System.out.println("Can't add as role is null");
            return false;
        }
        if (role.equals(getRoleByNameAndDescription(
                Role.RoleName.valueOf(role.getName()),
                role.getDescription()))) {
            System.out.println("Such role already exists");
            return false;
        }
        try (PreparedStatement statement = connection.prepareStatement(addNewRoleQuery)){
            connection.setAutoCommit(false);
            statement.setString(1, role.getName());
            statement.setString(2, role.getDescription());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Can't add the role because of SQLException");
            throw new SQLException("Can't add the role because of SQLException");
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
        return true;
    }

    @Override
    public boolean delete(int roleId) throws SQLException {
        if (roleId < 1) {
            System.out.println("Can't delete as role id is zero or negative");
            return false;
        }
        try (PreparedStatement statement = connection.prepareStatement(deleteRoleByIdQuery)) {
            connection.setAutoCommit(false);
            statement.setInt(1, roleId);
            int updatedRows = statement.executeUpdate();
            connection.commit();
            return updatedRows > 0;
        } catch (SQLException e) {
            System.out.println("Can't delete a role because of SQLException");
            throw new SQLException("Can't delete a role because of SQLException");
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(int roleId, Role role) throws SQLException {
        if (roleId < 1) {
            System.out.println("Can't delete as role id is zero or negative");
            return false;
        }
        if (Objects.isNull(role)) {
            System.out.println("Can't delete as role is null");
            return false;
        }
        try (PreparedStatement statement = connection.prepareStatement(updateRoleByIdQuery)) {
            connection.setAutoCommit(false);
            statement.setString(1, role.getName());
            statement.setString(2, role.getDescription());
            statement.setInt(3, roleId);
            int updatedRows = statement.executeUpdate();
            connection.commit();
            return updatedRows > 0;
        } catch (SQLException e) {
            System.out.println("Can't update the role because of SQLException");
            throw new SQLException("Can't update the role because of SQLException");
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Role getRoleByNameAndDescription(Role.RoleName name, String description) throws SQLException {
        List<Role> roles = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getRoleByNameAndDescriptionQuery)) {
            statement.setString(1, name.toString());
            statement.setString(2, description);
            ResultSet resultSet = statement.executeQuery();
            roles = getRoles(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Can't get a role because of SQLException");
        }
        return !roles.isEmpty() ? roles.get(0) : null;
    }


    public static final String getAllRolesQuery = "select * from " + dbName + ";";
    public static final String getRoleByNameAndDescriptionQuery = "select * from " + dbName + " where name=? and description=?;";
    public static final String addNewRoleQuery = "insert into " + dbName + " (name, description) values (?, ?);";
    public static final String deleteRoleByIdQuery = "delete from " + dbName + " where id = ?;";
    public static final String updateRoleByIdQuery = "update " + dbName + " set name=?, description=? where id = ?;";


}
