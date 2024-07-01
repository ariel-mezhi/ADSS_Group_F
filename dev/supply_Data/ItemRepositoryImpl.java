package supply_Data;

import supply_domain.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository{
    private ItemDaoImpl ItemDaoimpl;

    public ItemRepositoryImpl() throws SQLException {
        this.ItemDaoimpl = new ItemDaoImpl();
    }

    @Override
    public List<Item> getAllItems() throws SQLException {
        return this.ItemDaoimpl.getAllItems();
    }

    @Override
    public Item get(int serial_num) throws SQLException {
        return this.ItemDaoimpl.read(serial_num);
    }

    @Override
    public void add(Item item) throws SQLException {
        this.ItemDaoimpl.create(item); // assuming item is already added in runtime, just add to DB
    }

    @Override
    public void remove(Item item) throws SQLException {
        this.ItemDaoimpl.delete(item);
    }
}
