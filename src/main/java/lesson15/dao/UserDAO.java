package lesson15.dao;

import lesson15.pojo.User;
import lesson15.util.DBConnection;

import java.sql.*;

/**
 * Пользователь
 *
 * @author Timofey Yakimov
 */
public class UserDAO implements IUserDAO {

    private final String dbName = "usr";

    private Savepoint savepoint;

    private final Connection connection = DBConnection.getConnection();

    public UserDAO() throws SQLException, ClassNotFoundException {
    }

    @Override
    public User addUser(User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into " + dbName + " (name, birthday, login_id, city, email, description) " +
                        "values (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
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
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                user.setId(result.getInt(1));
            }
        } catch (NullPointerException e) {
            connection.rollback(this.savepoint);
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return user;
    }

    @Override
    public User addUser(User user, String savepoint) throws SQLException {
        this.savepoint = connection.setSavepoint(savepoint);
        return addUser(user);
    }

    @Override
    public User getUserByLoginAndName(int login_ID, String name) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from " + dbName + " where login_ID = ? and name = ?;")) {
            statement.setInt(1, login_ID);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setBirthday(resultSet.getDate(3));
                user.setLoginId(resultSet.getInt(4));
                user.setCity(resultSet.getString(5));
                user.setEmail(resultSet.getString(6));
                user.setDescription(resultSet.getString(7));
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

}
