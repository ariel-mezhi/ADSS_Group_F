package supply_Data;

import supply_domain.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemDaoImpl implements ItemDao{
    private final Connection conn;
    private final ItemTypeDaoImpl type_dao;

    public ItemDaoImpl() throws SQLException{
        conn = DataBase.connect();
        this.type_dao = new ItemTypeDaoImpl();
    }

    @Override
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM items");
        while (rs.next()) {
            int serial_num = rs.getInt("serial_number");
            int type_id = rs.getInt("type_id");
            String string_exp_date = rs.getString("expiration_date");
            String string_creation_date = rs.getString("creation_date");
            String[] dateparts = string_exp_date.split("\\.");
            int year = Integer.parseInt(dateparts[2]);
            int month = Integer.parseInt(dateparts[1]);
            int day = Integer.parseInt(dateparts[0]);
            java.util.Date exp_date = new java.util.Date(year-1900,month-1,day);
            dateparts = string_creation_date.split("\\.");
            year = Integer.parseInt(dateparts[2]);
            month = Integer.parseInt(dateparts[1]);
            day = Integer.parseInt(dateparts[0]);
            java.util.Date create_date = new Date(year-1900,month-1,day);
            Item item = new Item(type_dao.read(type_id),exp_date,create_date,serial_num);
            items.add(item);
        }
        return items;
    }

    @Override
    public Item read(int serial_num) throws SQLException { // returning item
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE serial_number = ?");
        stmt.setInt(1, serial_num);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int type_id = rs.getInt("type_id");
            String string_exp_date = rs.getString("expiration_date");
            String string_creation_date = rs.getString("creation_date");
            String[] dateparts = string_exp_date.split("\\.");
            int year = Integer.parseInt(dateparts[2]);
            int month = Integer.parseInt(dateparts[1]);
            int day = Integer.parseInt(dateparts[0]);
            java.util.Date exp_date = new java.util.Date(year-1900,month-1,day);
            dateparts = string_creation_date.split("\\.");
            year = Integer.parseInt(dateparts[2]);
            month = Integer.parseInt(dateparts[1]);
            day = Integer.parseInt(dateparts[0]);
            java.util.Date create_date = new Date(year-1900,month-1,day);
            return new Item(type_dao.read(type_id),exp_date,create_date,serial_num);
        }
        else return null;
    }

    @Override
    public void create(Item item) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO items (serial_number, type_id, expiration_date, creation_date) VALUES (?, ?, ?, ?)");
        stmt.setInt(1, item.getSerialNum());
        stmt.setInt(2, item.getType().getType_id());
        stmt.setString(3, item.getExp_date().toString());
        stmt.setString(4, item.getCreation_date().toString());
        stmt.executeUpdate();
    }

    @Override
    public void delete(Item item) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM items WHERE serial_number = ?");
        stmt.setInt(1, item.getSerialNum());
        stmt.executeUpdate();
    }
}
