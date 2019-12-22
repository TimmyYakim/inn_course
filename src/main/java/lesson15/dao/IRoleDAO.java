package lesson15.dao;

import lesson15.pojo.Role;

import java.sql.SQLException;

/**
 * Роль
 *
 * @author Timofey Yakimov
 */
public interface IRoleDAO extends DAO<Role> {

    Role getRoleByNameAndDescription(Role.RoleName name, String description) throws SQLException;

}
