package supply_Data;

import supply_domain.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {
    public List<Item> getAllItems() throws SQLException;
    public Item read(int id) throws SQLException;
    public void create(Item item) throws SQLException; // add line to DB
    public void delete(Item item) throws SQLException;
}
