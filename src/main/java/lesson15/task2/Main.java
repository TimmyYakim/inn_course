package lesson15.task2;

import lesson15.dao.RoleDAO;
import lesson15.dao.UserDAO;
import lesson15.dao.UserRoleDAO;
import lesson15.pojo.Role;
import lesson15.pojo.User;
import lesson15.util.TableMaker;

import java.sql.Date;
import java.sql.SQLException;

/**
 * 2)      Через jdbc интерфейс сделать запись данных(INSERT) в таблицу
 * a)      Используя параметризированный запрос
 * b)      Используя batch процесс
 * 3)      Сделать параметризированную выборку по login_ID и name одновременно
 * 4)      Перевести connection в ручное управление транзакциями
 * a)      Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
 *         между sql операциями установить логическую точку сохранения(SAVEPOINT)
 * б)   Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE)
 *         между sql операциями установить точку сохранения (SAVEPOINT A), намеренно ввести некорректные данные на последней операции, что бы транзакция откатилась к логической точке SAVEPOINT A
 * @author Timofey Yakimov
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Создание БД
        TableMaker maker = new TableMaker();
        maker.createTables();
        // Добавление пользователя
        User user = new User(1, "user", new Date(294328934L), 1, "user", "user", "user");
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(user);
        // Сделать параметризированную выборку по login_ID и name одновременно
        User u = userDAO.getUserByLoginAndName(1, "user");
        System.out.println(u);
        // установить логическую точку сохранения(SAVEPOINT)
        User user2 = new User(2, "user2", new Date(294328934L), 2, "user2", "user2", "user2");
        userDAO.addUser(user2, "SAVEPOINT");
        Role role1 = new Role(1, Role.RoleName.Administration, "role1");
        Role role2 = new Role(2, Role.RoleName.Clients, "role2");
        RoleDAO roleDAO = new RoleDAO();
        roleDAO.addRole(role1);
        roleDAO.addRole(role2);
        UserRoleDAO userRoleDAO = new UserRoleDAO();
        userRoleDAO.addUserRole(user, role1);
        userRoleDAO.addUserRole(user2, role2);
        // установить точку сохранения (SAVEPOINT A), намеренно ввести некорректные данные на последней операции,
        // что бы транзакция откатилась к логической точке SAVEPOINT A
        userDAO.addUser(null, "SAVEPOINT_A");
        maker.close();
    }



}
