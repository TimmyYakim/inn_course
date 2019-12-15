package lesson15.dao;

import lesson15.pojo.User;
import lesson15.util.DBConnection;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * Пользователь
 *
 * @author Timofey Yakimov
 */
public class UserDAO implements IUserDAO {

    private final String dbName = "user";

    private Savepoint addUserSavepoint = null;

    private final Connection connection = DBConnection.getConnection();

    public UserDAO() throws SQLException, ClassNotFoundException {
    }

    @Override
    public boolean addUser(User user) {
        if (user != null) {
            return addUsers(Arrays.asList(user));
        }
        return false;
    }

    public boolean addUser(User user, String savepoint) throws SQLException {
        addUserSavepoint = connection.setSavepoint(savepoint);
        return addUsers(Arrays.asList(user));
    }

    @Override
    public boolean addUsers(List<User> users) {
        try (PreparedStatement statement = connection.prepareStatement(
                     "insert into " + dbName + " values (?, ?, ?, ?, ?, ?, ?)")){
            connection.setAutoCommit(false);
            for (User u: users) {
                statement.setInt(1, u.getId());
                statement.setString(2, u.getName());
                statement.setDate(3, new Date(u.getBirthday().getTime()));
                statement.setInt(4, u.getLogin_ID());
                statement.setString(5, u.getCity());
                statement.setString(6, u.getEmail());
                statement.setString(7, u.getDescription());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (NullPointerException e) {
            try {
                connection.rollback(addUserSavepoint);
                System.out.println("Can't add null. Data base is roll back to the savepoint " + addUserSavepoint.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User getUserByLoginAndName(int login_ID, String name) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(
                     "select * from " + dbName + " where login_ID = ? and name = ?")){
                statement.setInt(1, login_ID);
                statement.setString(2, name);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt(1));
                    user.setName(resultSet.getString(2));
                    user.setBirthday(resultSet.getDate(3));
                    user.setLogin_ID(resultSet.getInt(4));
                    user.setCity(resultSet.getString(5));
                    user.setEmail(resultSet.getString(6));
                    user.setDescription(resultSet.getString(7));
                }
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
