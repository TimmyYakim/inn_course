package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.pojo.User;
import lesson15.util.ConnectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Задание1
 * Взять за основу ДЗ_15. Написать тест на CRUD операции
 * Инициализацию соединение с БД произвести один раз перед началом тестов, после завершения всех тестов, закрыть соединие с БД
 * Подготовку данных для CRUD операций производить в методе Init использую @Before
 * Задание 2
 * Использую Spy и Mockito создать заглушки для интерфейса JDBC
 *
 * @author Timofey Yakimov
 */
class UserRoleDAOTest {

    private ConnectionManager connectionManager;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private UserRoleDAO spyUserRoleDAO;
    private UserDAO userDAO;
    private RoleDAO roleDAO;

    @BeforeEach
    void init() {
        connectionManager = mock(ConnectionManager.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        spyUserRoleDAO = spy(UserRoleDAO.class);
        userDAO = mock(UserDAO.class);
        roleDAO = mock(RoleDAO.class);
    }

    @Test
    void addUserRoleSuccess() throws SQLException, ClassNotFoundException {
        String name = "user";
        Date date = new Date(1223434535);
        String city = "city";
        String email = "email";
        String description = "description";
        User user = new User(name, date, 1, city, email, description);
        Role role = new Role(Role.RoleName.Administration, "admin");
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserRoleDAO.addUserRoleQuery);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        doReturn(user).when(userDAO).getUserByLoginAndName(user.getLoginId(), user.getName());
        doReturn(role).when(roleDAO)
                .getRoleByNameAndDescription(Role.RoleName.valueOf(role.getName()),
                        role.getDescription());
        UserRoleDAO userRoleDAO = new UserRoleDAO(connectionManager);
        assertTrue(userRoleDAO.addUserRole(user, role));
    }

    @Test
    void addUserRoleFailNullableUser() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserRoleDAO.addUserRoleQuery);
        Role role = new Role(Role.RoleName.Administration, "admin");
        doReturn(resultSet).when(preparedStatement).executeQuery();
        UserRoleDAO userRoleDAO = new UserRoleDAO(connectionManager);
        assertFalse(userRoleDAO.addUserRole(null, role));
    }

    @Test
    void addUserRoleFailNullableRole() throws SQLException, ClassNotFoundException {
        String name = "user";
        Date date = new Date(1223434535);
        String city = "city";
        String email = "email";
        String description = "description";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserRoleDAO.addUserRoleQuery);
        User user = new User(name, date, 1, city, email, description);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        UserRoleDAO userRoleDAO = new UserRoleDAO(connectionManager);
        assertFalse(userRoleDAO.addUserRole(user, null));
    }

    @Test
    void addUserRoleFailSQLException() throws SQLException, ClassNotFoundException {
        String name = "user";
        Date date = new Date(1223434535);
        String city = "city";
        String email = "email";
        String description = "description";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(UserRoleDAO.addUserRoleQuery)).thenThrow(SQLException.class);
        UserRoleDAO userRoleDAO = new UserRoleDAO(connectionManager);
        User user = new User(name, date, 1, city, email, description);
        Role role = new Role(Role.RoleName.Administration, "admin");
        Throwable thrown = assertThrows(
                SQLException.class, () -> {userRoleDAO.addUserRole(user, role);});
        assertTrue(thrown.getMessage().contains("Can't link role and user"));
    }


}