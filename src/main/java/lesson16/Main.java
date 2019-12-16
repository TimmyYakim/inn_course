package lesson16;

import lesson16.dao.RoleDAO;
import lesson16.dao.UserDAO;
import lesson16.dao.UserRoleDAO;
import lesson16.pojo.Role;
import lesson16.pojo.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Взять за основу ДЗ_15,
 * покрыть код логированием
 * в основных блоках try покрыть уровнем INFO
 * с исключениях catch покрыть уровнем ERROR
 * настроить конфигурацию логера, что бы все логи записывались в БД, таблица LOGS,
 * колонки ID, DATE, LOG_LEVEL, MESSAGE, EXCEPTION
 * @author Timofey Yakimov
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        User user1 = new User("user1", new Date(294328934L), 1, "user1", "user1", "user1");
        UserDAO userDAO = new UserDAO();
        user1 = userDAO.addUser(user1);
        System.out.println(userDAO.getUserByLoginAndName(1, "user"));
        User user2 = new User("user2", new Date(294328934L), 2, "user2", "user2", "user2");
        user2 = userDAO.addUser(user2);
        Role role1 = new Role(Role.RoleName.Administration, "role1");
        Role role2 = new Role(Role.RoleName.Clients, "role2");
        RoleDAO roleDAO = new RoleDAO();
        role1 = roleDAO.addRole(role1);
        role2 = roleDAO.addRole(role2);
        UserRoleDAO userRoleDAO = new UserRoleDAO();
        userRoleDAO.addUserRole(user1, role1);
        userRoleDAO.addUserRole(user2, role2);
        System.out.println();
    }



}
