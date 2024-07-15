package DataAccessLayer;

import DomainLayer.Item;

import java.sql.SQLException;

public class ItemRepositoryImpl implements ItemRepository{
    private final ItemDaoImpl ItemDaoimpl;

    public ItemRepositoryImpl() throws SQLException {
        this.ItemDaoimpl = new ItemDaoImpl();
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
