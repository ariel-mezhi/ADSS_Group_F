package supply_Data;

import supply_domain.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemRepository {
    public List<Item> getAllItems() throws SQLException;
    Item get(int serial_num) throws SQLException;
    void add(Item item) throws SQLException;
    void remove(Item item) throws SQLException;
}
