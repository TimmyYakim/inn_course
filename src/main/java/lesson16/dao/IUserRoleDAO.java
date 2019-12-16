package lesson16.dao;

import lesson16.pojo.Role;
import lesson16.pojo.User;

/**
 * Многие ко многим (Пользователь - Роль)
 *
 * @author Timofey Yakimov
 */
public interface IUserRoleDAO {

    public boolean addUserRole(User user, Role role);

}
