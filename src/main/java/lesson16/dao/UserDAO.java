package lesson16.dao;

import lesson16.pojo.User;
import lesson16.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Пользователь
 *
 * @author Timofey Yakimov
 */
public class UserDAO implements IUserDAO {

    Logger logger = LogManager.getLogger(LogManager.class);

    private final String dbName = "usr";

    private final Connection connection = DBConnection.getConnection();

    public UserDAO() throws SQLException {
    }

    @Override
    public User addUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into " + dbName + " (name, birthday, login_id, city, email, description) " +
                        "values (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            logger.info("Add some users");
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
            logger.info("Some users were successfully added");
        } catch (NullPointerException e) {
            logger.error("Couldn't add any user because of null value ", e);
        } catch (SQLException e) {
            logger.error("Runtime error ", e);
            return null;
        }
        return user;
    }

    @Override
    public User getUserByLoginAndName(int login_ID, String name) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from " + dbName + " where login_ID = ? and name = ?;")) {
            logger.info("Getting a user");
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
            logger.info("Getting a user is done");
        } catch (SQLException e) {
            logger.error("Runtime error ", e);
            e.printStackTrace();
        }
        return user;
    }

}
