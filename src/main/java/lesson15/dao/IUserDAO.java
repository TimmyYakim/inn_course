package lesson15.dao;

import lesson15.pojo.User;

import java.sql.SQLException;

/**
 * Пользователь
 *
 * @author Timofey Yakimov
 */
public interface IUserDAO extends DAO<User> {

    boolean addUser(User user, String savepoint) throws SQLException;
    User getUserByLoginAndName(int login_id, String name) throws SQLException;

}
