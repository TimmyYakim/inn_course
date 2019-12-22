package lesson15.dao;

import lesson15.pojo.User;
import lesson15.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Пользователь
 *
 * @author Timofey Yakimov
 */
public class UserDAO implements IUserDAO {

    private static final String dbName = "usr";

    private Savepoint savepoint;

    private final Connection connection;

    public UserDAO() throws SQLException, ClassNotFoundException {
        connection = ConnectionManager.getConnection();
    }

    @Override
    public boolean addUser(User user, String savepoint) throws SQLException {
        if (Objects.isNull(savepoint)) {
            System.out.println("Can't add as savepoint is null");
            return false;
        }
        connection.setAutoCommit(false);
        this.savepoint = connection.setSavepoint(savepoint);
        return add(user);
    }

    @Override
    public User getUserByLoginAndName(int loginId, String name) throws SQLException {
        if (loginId < 1) {
            System.out.println("Can't get as login id is zero or negative");
            return null;
        }
        if (Objects.isNull(name)) {
            System.out.println("Can't get as name is null");
            return null;
        }
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getUserByLoginIdAndNameQuery)) {
            statement.setInt(1, loginId);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            users = getUsers(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Can't get a user because of " + e.getClass());
            throw new SQLException("Can't get a user because of " + e.getClass());
        }
        return !users.isEmpty() ? users.get(0) : null;
    }

    @Override
    public List<User> getAll()  throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from " + dbName + ";")) {
            ResultSet resultSet = statement.executeQuery();
            users = getUsers(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Can't get the users because of " + e.getClass());
            throw new SQLException("Can't get the users because of " + e.getClass());
        }
        return users;
    }

    private List<User> getUsers(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        if (Objects.isNull(resultSet)) {
            System.out.println("Can't get as result set id is null");
            return users;
        }
        while (resultSet.next()) {
            users.add(new User(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return users;
    }

    @Override
    public boolean add(User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(addUserQuery)) {
            if (user.equals(getUserByLoginAndName(user.getLoginId(), user.getName()))) {
                System.out.println("Such user already exists");
                return false;
            }
            connection.setAutoCommit(false);
            statement.setString(1, user.getName());
            statement.setDate(2, new Date(user.getBirthday().getTime()));
            statement.setInt(3, user.getLoginId());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getDescription());
            statement.addBatch();
            statement.executeBatch();
            connection.commit();
        } catch (NullPointerException e) {
            if (connection != null) {
                connection.rollback(this.savepoint);
                System.out.println("Can't add the user because of " + e.getClass());
            }
            throw new NullPointerException("Can't add the user because of " + e.getClass());
        } catch (SQLException e) {
            System.out.println("Can't add the user because of " + e.getClass());
            throw new SQLException("Can't add the user because of " + e.getClass());
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
        return true;
    }

    @Override
    public boolean delete(int userId) throws SQLException {
        if (userId < 1) {
            System.out.println("Can't get as user id is zero or negative");
            return false;
        }
        try (PreparedStatement statement = connection.prepareStatement(deleteUserByIdQuery)) {
            connection.setAutoCommit(false);
            statement.setInt(1, userId);
            int updatedRows = statement.executeUpdate();
            connection.commit();
            return updatedRows > 0;
        } catch (SQLException e) {
            System.out.println("Can't delete a user because of "+ e.getClass());
            throw new SQLException("Can't delete a user because of "+ e.getClass());
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(int userId, User user) throws SQLException {
        if (userId < 1) {
            System.out.println("Can't update as user id is zero or negative");
            return false;
        }
        if (Objects.isNull(user)) {
            System.out.println("Can't update as login id is null");
            return false;
        }
        try (PreparedStatement statement = connection.prepareStatement(updateUserByIdQuery)) {
            connection.setAutoCommit(false);
            statement.setString(1, user.getName());
            statement.setDate(2, new Date(user.getBirthday().getTime()));
            statement.setInt(3, user.getLoginId());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getDescription());
            statement.setInt(7, userId);
            int updatedRows = statement.executeUpdate();
            connection.commit();
            return updatedRows > 0;
        } catch (SQLException e) {
            System.out.println("Can't update the user because of " + e.getClass());
            throw new SQLException("Can't update the user because of " + e.getClass());
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static final String updateUserByIdQuery = "update " + dbName + " set name=?, birthday=?, login_id=?, " +
            "city=?, email=?, description=? where id = ?;";
    public static final String deleteUserByIdQuery = "delete from " + dbName + " where id = ?;";
    public static final String addUserQuery = "insert into " + dbName + " (name, birthday, login_id, city, email, description) " +
            "values (?, ?, ?, ?, ?, ?);";
    public static final String getAllUsersQuery = "select * from " + dbName + ";";
    public static final String getUserByLoginIdAndNameQuery = "select * from " + dbName + " where login_ID = ? and name = ?;";


}
