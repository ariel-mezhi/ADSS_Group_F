package DataAccessLayer;

import DomainLayer.Item;
import DomainLayer.Item_type;

import java.sql.SQLException;
import java.util.List;

public interface ItemTypeRepositoryImpl {

    Item_type get(int type_id) throws SQLException;
    void add(Item_type item_type) throws SQLException;
    List<Item_type> getAll() throws SQLException;
    void update(Item_type item_type) throws SQLException;

}
