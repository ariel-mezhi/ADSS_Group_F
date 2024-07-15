package DataAccessLayer;

import DomainLayer.Item_type;
import java.sql.SQLException;
import java.util.List;


public class TypeRepositoryImpl implements ItemTypeRepositoryImpl{

    private final ItemTypeDaoImpl type_dao;

    public TypeRepositoryImpl() throws SQLException {
        this.type_dao = new ItemTypeDaoImpl();
    }
    @Override
    public Item_type get(int type_id) throws SQLException {
        return this.type_dao.read(type_id);
    }

    @Override
    public void add(Item_type item_type) throws SQLException {
        this.type_dao.create(item_type);
    }

    @Override
    public List<Item_type> getAll() throws SQLException {
        return this.type_dao.readAll();
    }

    @Override
    public void update(Item_type item_type) throws SQLException {
        this.type_dao.update(item_type);
    }
}
