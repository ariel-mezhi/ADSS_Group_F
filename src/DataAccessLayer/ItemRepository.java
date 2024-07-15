package DataAccessLayer;

import DomainLayer.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemRepository {
    Item get(int serial_num) throws SQLException;
    void add(Item item) throws SQLException;
    void remove(Item item) throws SQLException;
}
