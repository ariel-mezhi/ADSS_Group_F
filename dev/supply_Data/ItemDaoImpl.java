package supply_Data;

import supply_domain.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemDaoImpl implements ItemDao{
    private Connection conn;

    public ItemDaoImpl() throws SQLException{
        conn = DataBase.connect();
    }

    @Override
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM items");
        while (rs.next()) {
            int serial_num = rs.getInt("serial number");
            int type_id = rs.getInt("type id");
            String string_exp_date = rs.getString("expiration date");
            String string_creation_date = rs.getString("creation date");
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
            Item item = new Item(serial_num,type_id,exp_date,create_date);
            items.add(item);
        }
        return items;
    }

    @Override
    public Item read(int serial_num) throws SQLException { // returning item
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE id = ?");
        stmt.setInt(1, serial_num);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int type_id = rs.getInt("type id");
            String string_exp_date = rs.getString("expiration date");
            String string_creation_date = rs.getString("creation date");
            return item; // when returning new item, if it already exists in runtime it will be discarded
        }
        else return null;
    }

    @Override
    public void create(Item item) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO items (serial number, type id, expiration date, creation date) VALUES (?, ?, ?, ?)");
        stmt.setInt(1, item.getSerialNum());
        stmt.setInt(2, item.getType().getType_id());
        stmt.setString(3, item.getExp_date().toString());
        stmt.setString(4, item.getCreation_date().toString());
        stmt.executeUpdate();
    }

    @Override
    public void delete(Item item) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM items WHERE id = ?");
        stmt.setInt(1, item.getSerialNum());
        stmt.executeUpdate();
    }
}
