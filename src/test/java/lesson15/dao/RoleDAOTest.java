package lesson15.dao;

import lesson15.pojo.Role;
import lesson15.pojo.User;
import lesson15.util.ConnectionManager;
import net.bytebuddy.asm.Advice;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

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
class RoleDAOTest {

    private ConnectionManager connectionManager;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private RoleDAO spyRoleDAO;


    @BeforeEach
    void init() {
        connectionManager = mock(ConnectionManager.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        spyRoleDAO = spy(RoleDAO.class);
    }

    @Test
    void getAllSuccess() throws SQLException, ClassNotFoundException {
        Role role1 = new Role(Role.RoleName.Administration, "admin");
        Role role2 = new Role(Role.RoleName.Clients, "client");
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getAllRolesQuery);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        doReturn(Arrays.asList(role1, role2)).when(spyRoleDAO).getRoles(any());
        Assert.isNonEmpty(spyRoleDAO.getAll());
    }

    @Test
    void getAllEmpty() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getAllRolesQuery);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        doReturn(new ArrayList()).when(spyRoleDAO).getRoles(any());
        Assert.isEmpty(spyRoleDAO.getAll());
    }

    @Test
    void getAllFailSQLException() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(RoleDAO.getAllRolesQuery)).thenThrow(SQLException.class);
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        Throwable thrown = assertThrows(SQLException.class, () -> {roleDAO.getAll();});
        assertTrue(thrown.getMessage().contains("Can't get the roles because of SQLException"));
    }

    @Test
    void addNewRole() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.addNewRoleQuery);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getRoleByNameAndDescriptionQuery);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        Role role = new Role(Role.RoleName.Administration, "admin");
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        assertTrue(roleDAO.add(role));
    }

    @Test
    void addNull() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.addNewRoleQuery);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getRoleByNameAndDescriptionQuery);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        assertFalse(roleDAO.add(null));
    }

    @Test
    void addFailAlreadyExistingRole() throws SQLException, ClassNotFoundException {
        final Role.RoleName roleName = Role.RoleName.Administration;
        final String description = "admin";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getRoleByNameAndDescriptionQuery);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        doReturn(new Role(roleName, description)).when(spyRoleDAO).getRoleByNameAndDescription(roleName, description);
        Role newRole = new Role(roleName, description);
        assertFalse(spyRoleDAO.add(newRole));
    }

    @Test
    void addFailSQLException() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getRoleByNameAndDescriptionQuery);
        doReturn(resultSet).when(preparedStatement).executeQuery();
        when(connection.prepareStatement(RoleDAO.addNewRoleQuery)).thenThrow(SQLException.class);
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        Throwable thrown = assertThrows(SQLException.class, () -> {roleDAO.add(new Role(Role.RoleName.Administration, "admin"));});
        assertTrue(thrown.getMessage().contains("Can't add the role"));
    }

    @Test
    void deleteRoleSuccess() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.deleteRoleByIdQuery);
        doReturn(1).when(preparedStatement).executeUpdate();
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        assertTrue(roleDAO.delete(2));
    }

    @Test
    void deleteNoRoleFail() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.deleteRoleByIdQuery);
        doReturn(0).when(preparedStatement).executeUpdate();
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        assertFalse(roleDAO.delete(2));
    }

    @Test
    void deleteNegativeIdxFail() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.deleteRoleByIdQuery);
        doReturn(0).when(preparedStatement).executeUpdate();
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        assertFalse(roleDAO.delete(0));
    }


    @Test
    void deleteFailSQLException() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(RoleDAO.deleteRoleByIdQuery)).thenThrow(SQLException.class);
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        Throwable thrown = assertThrows(SQLException.class, () -> {roleDAO.delete(1);});
        assertTrue(thrown.getMessage().contains("Can't delete a role because of SQLException"));
    }


    @Test
    void updateRoleSuccess() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.updateRoleByIdQuery);
        doReturn(1).when(preparedStatement).executeUpdate();
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        assertTrue(roleDAO.update(2, new Role(Role.RoleName.Administration, "admin")));
    }

    @Test
    void updateRolesFail() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.updateRoleByIdQuery);
        doReturn(0).when(preparedStatement).executeUpdate();
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        assertFalse(roleDAO.update(2, new Role(Role.RoleName.Administration, "admin")));
    }

    @Test
    void updateNegativeIdxFail() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.updateRoleByIdQuery);
        doReturn(0).when(preparedStatement).executeUpdate();
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        assertFalse(roleDAO.update(-1, new Role(Role.RoleName.Administration, "admin")));
    }

    @Test
    void updateNullableRoleFail() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.updateRoleByIdQuery);
        doReturn(0).when(preparedStatement).executeUpdate();
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        assertFalse(roleDAO.update(1, null));
    }

    @Test
    void updateFailSQLException() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(RoleDAO.updateRoleByIdQuery)).thenThrow(SQLException.class);
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        Role role = new Role(Role.RoleName.Administration, "admin");
        Throwable thrown = assertThrows(SQLException.class, () -> {roleDAO.update(1, role);});
        assertTrue(thrown.getMessage().contains("Can't update the role because of SQLException"));
    }

    @Test
    void getRoleByNameAndDescriptionSuccess() throws SQLException, ClassNotFoundException {
        final Role.RoleName roleName = Role.RoleName.Administration;
        final String description = "admin";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getRoleByNameAndDescriptionQuery);
        doReturn(Arrays.asList(new Role(roleName, description))).when(spyRoleDAO).getRoles(any());
        Assert.isNonEmpty(spyRoleDAO.getRoleByNameAndDescription(roleName, description));
    }

    @Test
    void getRoleByNameAndDescriptionFail() throws SQLException, ClassNotFoundException {
        final Role.RoleName roleName = Role.RoleName.Administration;
        final String description = "admin";
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(RoleDAO.getRoleByNameAndDescriptionQuery);
        List<Role> role = new ArrayList<>();
        doReturn(role).when(spyRoleDAO).getRoles(any());
        Assert.isEmpty(spyRoleDAO.getRoleByNameAndDescription(roleName, description));
    }

    @Test
    void getRoleByNameAndDescriptionFailSQLException() throws SQLException, ClassNotFoundException {
        when(connectionManager.getConnectionReturn()).thenReturn(connection);
        when(connection.prepareStatement(RoleDAO.getRoleByNameAndDescriptionQuery)).thenThrow(SQLException.class);
        RoleDAO roleDAO = new RoleDAO(connectionManager);
        Throwable thrown = assertThrows(
                SQLException.class, () -> {roleDAO.getRoleByNameAndDescription(
                        Role.RoleName.Administration,
                        "admin");});
        assertTrue(thrown.getMessage().contains("Can't get a role"));
    }

}