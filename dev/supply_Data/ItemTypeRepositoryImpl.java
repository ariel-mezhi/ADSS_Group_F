package supply_Data;

import supply_domain.Item;
import supply_domain.Item_type;

import java.sql.SQLException;

public interface ItemTypeRepositoryImpl {

    Item_type get(int type_id) throws SQLException;
    void add(Item_type item_type) throws SQLException;

    void update(Item_type item_type) throws SQLException;

}
