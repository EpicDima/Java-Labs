package storage.base.dao.base;

import java.util.List;

public interface BaseDAO<T> {
    boolean create(T item);
    List<T> read();
    boolean update(T item);
    boolean delete(T item);
}
