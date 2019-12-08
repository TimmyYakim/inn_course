package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.pojo.User;

/**
 * Многие ко многим (Пользователь - Роль)
 *
 * @author Timofey Yakimov
 */
public interface IUserRoleDAO {

    public boolean addUserRole(User user, Role role);

}
