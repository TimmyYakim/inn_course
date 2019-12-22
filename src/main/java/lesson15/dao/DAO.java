package lesson15.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Timofey Yakimov
 */
public interface DAO<T> {

    List<T> getAll() throws SQLException;

    boolean add(T entity) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean update(int id, T entity) throws SQLException;
}
