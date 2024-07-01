package supply_Data;

import supply_domain.Item_type;

import java.sql.SQLException;

public interface TypeDao {
    public Item_type read(int type_id) throws SQLException;
    public void create(Item_type item_type) throws SQLException; // add line to DB
}
