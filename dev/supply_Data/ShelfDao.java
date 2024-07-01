package supply_Data;

import supply_domain.Item_type;
import supply_domain.Shelf;

import java.sql.SQLException;

public interface ShelfDao {
    public Shelf read(String shelf_description) throws SQLException;
    public void create(Shelf shelf) throws SQLException; // add line to DB
}
