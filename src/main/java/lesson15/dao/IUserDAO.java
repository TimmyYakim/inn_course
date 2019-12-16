package lesson15.dao;

import lesson15.pojo.User;

import java.sql.SQLException;

/**
 * Пользователь
 *
 * @author Timofey Yakimov
 */
public interface IUserDAO {

    public User addUser(User user) throws SQLException;
    public User addUser(User user, String savepoint) throws SQLException;
    public User getUserByLoginAndName(int login_id, String name);


}
