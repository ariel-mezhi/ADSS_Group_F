package DataAccessLayer;

import DomainLayer.Item_type;

import java.sql.SQLException;
import java.util.List;

public interface TypeDao {
    public Item_type read(int type_id) throws SQLException;
    public void create(Item_type item_type) throws SQLException; // add line to DB
    public void update(Item_type item_type) throws SQLException;
    public List<Item_type> readAll() throws SQLException;
}
