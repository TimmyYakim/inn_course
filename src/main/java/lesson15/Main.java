package lesson15;

import lesson15.dao.RoleDAO;
import lesson15.dao.UserDAO;
import lesson15.dao.UserRoleDAO;
import lesson15.pojo.Role;
import lesson15.pojo.User;
import lesson15.util.ConnectionManager;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * 2)      Через jdbc интерфейс сделать запись данных(INSERT) в таблицу
 * a)      Используя параметризированный запрос
 * b)      Используя batch процесс
 * 3)      Сделать параметризированную выборку по login_ID и name одновременно
 * 4)      Перевести connection в ручное управление транзакциями
 * a)      Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
 * между sql операциями установить логическую точку сохранения(SAVEPOINT)
 * б)   Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
 * между sql операциями установить точку сохранения (SAVEPOINT A), намеренно ввести некорректные данные на последней операции, что бы транзакция откатилась к логической точке SAVEPOINT A
 *
 * @author Timofey Yakimov
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        ConnectionManager dbConnection = new ConnectionManager();
        try {
            // Добавление пользователя
            User user1 = new User("user", new Date(294328934L), 1, "user", "user", "user");
            UserDAO userDAO = new UserDAO();
            userDAO.add(user1);
            // Сделать параметризированную выборку по login_ID и name одновременно
            User u = userDAO.getUserByLoginAndName(1, "user");
            System.out.println(u);
            // установить логическую точку сохранения(SAVEPOINT)
            User user2 = new User("user2", new Date(294328934L), 2, "user2", "user2", "user2");
            userDAO.addUser(user2, "SAVEPOINT");
            Role role1 = new Role(Role.RoleName.Administration, "role1");
            Role role2 = new Role(Role.RoleName.Clients, "role2");
            RoleDAO roleDAO = new RoleDAO();
            roleDAO.add(role1);
            roleDAO.add(role2);
            UserRoleDAO userRoleDAO = new UserRoleDAO();
            userRoleDAO.addUserRole(user1, role1);
            userRoleDAO.addUserRole(user2, role2);
            // установить точку сохранения (SAVEPOINT A), намеренно ввести некорректные данные на последней операции,
            // что бы транзакция откатилась к логической точке SAVEPOINT A
            userDAO.addUser(null, "SAVEPOINT_A");
        } finally {
            dbConnection.close();
        }
    }
}
