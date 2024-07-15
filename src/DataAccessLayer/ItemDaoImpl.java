package DataAccessLayer;

import DomainLayer.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static DataAccessLayer.Database.connect;

public class ItemDaoImpl implements ItemDao{
    private final Connection conn;
    private final ItemTypeDaoImpl type_dao;

    public ItemDaoImpl() throws SQLException{
        conn = Database.connect();
        this.type_dao = new ItemTypeDaoImpl();
    }

    @Override
    public Item read(int serial_num) throws SQLException {
        String sql = "SELECT * FROM items WHERE serial_number = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement
                     (sql)){
            stmt.setInt(1, serial_num);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int type_id = rs.getInt("type_id");
                String string_exp_date = rs.getString("expiration_date");
                String string_creation_date = rs.getString("creation_date");
                String location = rs.getString("location");
                String[] dateparts = string_exp_date.split("\\.");
                int year = Integer.parseInt(dateparts[2]);
                int month = Integer.parseInt(dateparts[1]);
                int day = Integer.parseInt(dateparts[0]);
                Date exp_date = new Date(year,month,day);
                dateparts = string_creation_date.split("\\.");
                year = Integer.parseInt(dateparts[2]);
                month = Integer.parseInt(dateparts[1]);
                day = Integer.parseInt(dateparts[0]);
                Date create_date = new Date(year,month,day);
                return new Item(type_dao.read(type_id),exp_date,create_date,serial_num,location);
            }
            else return null;
        }
        catch (SQLException e) {
            System.out.println( e.getMessage());
        }
        return null;
    }

    @Override
    public void create(Item item) throws SQLException {
        String sql = "INSERT INTO items (serial_number, type_id, expiration_date, creation_date,location) VALUES (?, ?, ?, ?,?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement
                     (sql)){
            stmt.setInt(1, item.getSerialNum());
            stmt.setInt(2, item.getType().getType_id());
            String day = Integer.toString(item.getExp_date().getDate());
            String month = Integer.toString(item.getExp_date().getMonth()+1);
            String year = Integer.toString(item.getExp_date().getYear()+1900);
            stmt.setString(3, day + "." + month + "." + year);
            day = Integer.toString(item.getCreation_date().getDate());
            month = Integer.toString(item.getCreation_date().getMonth()+1);
            year = Integer.toString(item.getCreation_date().getYear()+1900);
            stmt.setString(4, day + "." + month + "." + year);
            stmt.setString(5, item.getLocation());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println( e.getMessage());
        }
    }

    @Override
    public void delete(Item item) throws SQLException {
        String sql = "DELETE FROM items WHERE serial_number = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement
                     (sql)){
            stmt.setInt(1, item.getSerialNum());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println( e.getMessage());
        }
    }
}
