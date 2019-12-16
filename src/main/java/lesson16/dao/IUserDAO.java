package lesson16.dao;

import lesson16.pojo.User;

/**
 * Пользователь
 *
 * @author Timofey Yakimov
 */
public interface IUserDAO {

    public User addUser(User user);
    public User getUserByLoginAndName(int login_id, String name);


}
