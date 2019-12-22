package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.pojo.User;
import lesson15.util.ConnectionManager;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

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
class UserDAOTest {


    private ConnectionManager connectionManager;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private UserDAO spyUserDAO;

    @BeforeEach
    void init() {
        connectionManager = mock(ConnectionManager.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        spyUserDAO = spy(UserDAO.class);
    }

    @Test
    void getAllSuccess() throws SQLException, ClassNotFoundException {
        User user1 = new User("user1", new Date(1223434535), 1, "city1", "email1", "description1");
        User user2 = new User("user2", new Date(1223434535), 2, "city2", "email2", "description2");
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getAllRolesQuery);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        doReturn(Arrays.asList(user1, user2)).when(spyUserDAO).getUsers(any());
        Assert.isNonEmpty(spyUserDAO.getAll());
    }

    @Test
    void getAllEmpty() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getAllRolesQuery);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        doReturn(new ArrayList()).when(spyUserDAO).getUsers(any());
        Assert.isEmpty(spyUserDAO.getAll());
    }

    @Test
    void getAllSQLException() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(UserDAO.getAllUsersQuery)).thenThrow(SQLException.class);
        UserDAO userDAO = new UserDAO(connectionManager);
        Throwable thrown = assertThrows(
                SQLException.class, () -> {userDAO.getAll();});
        assertTrue(thrown.getMessage().contains("Can't get the users because of"));
    }

    @Test
    void addUserSuccess() throws SQLException, ClassNotFoundException {
        String name = "user";
        Date date = new Date(1223434535);
        String city = "city";
        String email = "email";
        String description = "description";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.addUserQuery);
        doReturn(new User(name, date, 2, city, email,  description))
                .when(spyUserDAO).getUserByLoginAndName(1, "user");
        doReturn(resultSet).when(preparedStatement).executeQuery();
        assertTrue(spyUserDAO.addUser(new User(name, date, 1, city, email,  description), "savepoint"));
    }

    @Test
    void addUserFail() throws SQLException, ClassNotFoundException {
        String name = "user";
        Date date = new Date(1223434535);
        String city = "city";
        String email = "email";
        String description = "description";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.addUserQuery);
        doReturn(new User(name, date, 1, city, email,  description))
                .when(spyUserDAO).getUserByLoginAndName(1, "user");
        doReturn(resultSet).when(preparedStatement).executeQuery();
        assertFalse(spyUserDAO.addUser(new User(name, date, 1, city, email,  description), "savepoint"));
    }

    @Test
    void addUserFailNullableUser() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        UserDAO userDAO = new UserDAO(connectionManager);
        Throwable thrown = assertThrows(
                NullPointerException.class, () -> {userDAO.addUser(null, "savepoint");});
        assertTrue(thrown.getMessage().contains("Can't add the user because of"));
    }

    @Test
    void addUserFailSQLException() throws SQLException, ClassNotFoundException {
        String name = "user";
        Date date = new Date(1223434535);
        String city = "city";
        String email = "email";
        String description = "description";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(UserDAO.addUserQuery)).thenThrow(SQLException.class);
        UserDAO userDAO = new UserDAO(connectionManager);
        User user = new User(name, date, 1, city, email, description);
        Throwable thrown = assertThrows(
                SQLException.class, () -> {userDAO.addUser(user, "savepoint");});
        assertTrue(thrown.getMessage().contains("Can't add the user because of"));
    }

    @Test
    void addUserFailNullableSavepoint() throws SQLException, ClassNotFoundException {
        String name = "user";
        Date date = new Date(1223434535);
        String city = "city";
        String email = "email";
        String description = "description";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        assertFalse(spyUserDAO.addUser(new User(name, date, 1, city, email,  description), null));
    }

    @Test
    void addSuccess() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.addUserQuery);
        doReturn(new User("user", new Date(1223434535), 2, "city", "email", "description"))
                .when(spyUserDAO).getUserByLoginAndName(1, "user");
        doReturn(resultSet).when(preparedStatement).executeQuery();
        User user = new User("user", new Date(1223434535), 1, "city", "email", "description");
        assertTrue(spyUserDAO.add(user));
    }

    @Test
    void addFail() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.addUserQuery);
        doReturn(new User("user", new Date(1223434535), 1, "city", "email", "description"))
                .when(spyUserDAO).getUserByLoginAndName(1, "user");
        doReturn(resultSet).when(preparedStatement).executeQuery();
        User user = new User("user", new Date(1223434535), 1, "city", "email", "description");
        assertFalse(spyUserDAO.add(user));
    }

    @Test
    void getUserByLoginAndNameSuccess() throws SQLException, ClassNotFoundException {
        final int loginId = 1;
        final String name = "user";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.getUserByLoginIdAndNameQuery);
        doReturn(Arrays.asList(new User(name, new Date(1223434535), loginId, "city", "email", "description")))
                .when(spyUserDAO).getUsers(any());
        Assert.isNonEmpty(spyUserDAO.getUserByLoginAndName(loginId, name));
    }

    @Test
    void getUserByLoginAndNameFail() throws SQLException, ClassNotFoundException {
        final int loginId = 1;
        final String name = "user";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.getUserByLoginIdAndNameQuery);
        List<User> user = new ArrayList<>();
        doReturn(user).when(spyUserDAO).getUsers(any());
        Assert.isEmpty(spyUserDAO.getUserByLoginAndName(loginId, name));
    }

    @Test
    void getUserByLoginAndNameFailNegativeId() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        assertNull(spyUserDAO.getUserByLoginAndName(-1, "user"));
    }

    @Test
    void getUserByLoginAndNameFailNullableName() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        assertNull(spyUserDAO.getUserByLoginAndName(1, null));
    }

    @Test
    void getUserByLoginAndNameFailNullableNameFailSQLException() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(UserDAO.getUserByLoginIdAndNameQuery)).thenThrow(SQLException.class);
        UserDAO userDAO = new UserDAO(connectionManager);
        Throwable thrown = assertThrows(
                SQLException.class, () -> {userDAO.getUserByLoginAndName(
                        1,
                        "user");});
        assertTrue(thrown.getMessage().contains("Can't get a user because of"));
    }

    @Test
    void deleteUserSuccess() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.deleteUserByIdQuery);
        doReturn(1).when(preparedStatement).executeUpdate();
        UserDAO userDAO = new UserDAO(connectionManager);
        assertTrue(userDAO.delete(2));
    }

    @Test
    void deleteFail() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.deleteUserByIdQuery);
        doReturn(0).when(preparedStatement).executeUpdate();
        UserDAO userDAO = new UserDAO(connectionManager);
        assertFalse(userDAO.delete(2));
    }

    @Test
    void deleteFailNegativeIdx() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        UserDAO userDAO = new UserDAO(connectionManager);
        assertFalse(userDAO.delete(-1));
    }

    @Test
    void deleteFailSQLException() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(UserDAO.deleteUserByIdQuery)).thenThrow(SQLException.class);
        UserDAO userDAO = new UserDAO(connectionManager);
        Throwable thrown = assertThrows(
                SQLException.class, () -> {userDAO.delete(1);});
        assertTrue(thrown.getMessage().contains("Can't delete a user"));
    }

    @Test
    void updateSuccess() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.updateUserByIdQuery);
        doReturn(1).when(preparedStatement).executeUpdate();
        UserDAO userDAO = new UserDAO(connectionManager);
        User user = new User("user",
                new Date(1223434535),
                1,
                "city",
                "email",
                "description");
        assertTrue(userDAO.update(2, user));
    }

    @Test
    void updateFail() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(UserDAO.updateUserByIdQuery);
        doReturn(0).when(preparedStatement).executeUpdate();
        UserDAO userDAO = new UserDAO(connectionManager);
        User user = new User("user",
                new Date(1223434535),
                1,
                "city",
                "email",
                "description");
        assertFalse(userDAO.update(2, user));
    }

    @Test
    void updateFailNegativeIdx() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        UserDAO userDAO = new UserDAO(connectionManager);
        User user = new User("user",
                new Date(1223434535),
                1,
                "city",
                "email",
                "description");
        assertFalse(userDAO.update(-1, user));
    }

    @Test
    void updateFailNullableUser() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        UserDAO userDAO = new UserDAO(connectionManager);
        assertFalse(userDAO.update(1, null));
    }

    @Test
    void updateFailSQLException() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(UserDAO.updateUserByIdQuery)).thenThrow(SQLException.class);
        UserDAO userDAO = new UserDAO(connectionManager);
        User user = new User("user",
                new Date(1223434535),
                1,
                "city",
                "email",
                "description");
        Throwable thrown = assertThrows(
                SQLException.class, () -> {userDAO.update(1, user);});
        assertTrue(thrown.getMessage().contains("Can't update the user"));
    }


}