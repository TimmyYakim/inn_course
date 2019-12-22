package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.pojo.User;

import java.sql.SQLException;

/**
 * Многие ко многим (Пользователь - Роль)
 *
 * @author Timofey Yakimov
 */
public interface IUserRoleDAO {

    boolean addUserRole(User user, Role role) throws SQLException, ClassNotFoundException;

}
