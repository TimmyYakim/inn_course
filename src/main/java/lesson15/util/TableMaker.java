package lesson15.util;

import com.sun.xml.internal.ws.Closeable;
import lesson16.util.DBConnection;

import javax.xml.ws.WebServiceException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * Создание таблиц
 *
 * @author Timofey Yakimov
 */
public class TableMaker implements Closeable {

    private Connection connection;
    private Statement statement;

    public TableMaker() throws SQLException, ClassNotFoundException {
        connection = DBConnection.getConnection();
        statement = connection.createStatement();
    }

    public Connection createTables() throws SQLException {
        connection.setAutoCommit(false);
        for (String q: queries) {
            statement.addBatch(q);
        }
        statement.executeBatch();
        statement.close();
        connection.setAutoCommit(true);
        return connection;
    }

    @Override
    public void close() throws WebServiceException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String userTable = "drop table user if exists; " +
            "create table user (" +
            "id INTEGER not null, " +
            "name VARCHAR(255) not null, " +
            "birthday DATE, " +
            "login_ID INTEGER not null, " +
            "city VARCHAR(255), " +
            "email VARCHAR(255), " +
            "description VARCHAR(1024)" +
            ")";
    private String roleTable = "drop table role if exists; " +
            "create table role (" +
            "id INTEGER not null, " +
            "name VARCHAR(255), " +
            "description VARCHAR(1024)" +
            ")";
    private String userRoleTable = "drop table user_role if exists; " +
            "create table user_role (" +
            "id INTEGER auto_increment, " +
            "user_id INTEGER, " +
            "role_id  INTEGER" +
            ")";
    private String logsTable = "drop table LOGS if exists; " +
            "create table LOGS (" +
            "ID INTEGER auto_increment, " +
            "DATE TIMESTAMP, " +
            "LOG_LEVEL VARCHAR(1024), " +
            "MESSAGE VARCHAR(1024), " +
            "EXCEPTION VARCHAR(1024)" +
            ")";

    private List<String> queries = Arrays.asList(userTable, userRoleTable, roleTable, logsTable);
}
