package lesson15.dao;

import lesson15.pojo.User;

import java.util.List;

/**
 * Пользователь
 *
 * @author Timofey Yakimov
 */
public interface IUserDAO {

    public boolean addUser(User user);
    public boolean addUsers(List<User> user);
    public User getUserByLoginAndName(int login_id, String name);


}
