package DataAccessLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IDAO<T> {

    public ArrayList<T> getAll() throws SQLException;
    public T getById(int id) throws SQLException;
    public void addT(T object) throws SQLException;
    public void updateT(T object) throws SQLException;
    public void deleteT(int id) throws SQLException;
}
