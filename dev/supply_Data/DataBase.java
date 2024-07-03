package supply_Data;

import java.sql.*;

public class DataBase {
    private static final String DB_URL = "yuval url: jdbc:sqlite:C:/Users/Yuval Ellins/Desktop/projects/ADSS_Group_F/MyDataBase.db";
    // yuval url: jdbc:sqlite:C:/Users/Yuval Ellins/Desktop/projects/ADSS_Group_F/MyDataBase.db
    // omer url : jdbc:sqlite:C:/Users/omert/Desktop/sem4 project/ADSS_Group_F/MyDataBase.db
    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }



    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS items (
                 serial_number INTEGER PRIMARY KEY AUTOINCREMENT,
                 type_id INTEGER,
                 expiration_date TEXT,
                 creation_date TEXT
                );""";
        String sql1 = """
                CREATE TABLE IF NOT EXISTS types (
                 type_id INTEGER PRIMARY KEY AUTOINCREMENT,
                 producer TEXT ,
                 category TEXT ,
                 sub_category TEXT ,
                 size TEXT ,
                 amount_on_shelves INTEGER,
                 amount_in_storage INTEGER,
                 selling_price FLOAT,
                 cost_price FLOAT,
                 minimal_amount INTEGER,
                 percentage_sale INTEGER,
                 amount_of_days_left_sale INTEGER,
                 supplier_sale INTEGER
                );""";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            System.out.println("Executing table creation statement...");
            stmt.execute(sql);
            stmt.execute(sql1);
            System.out.println("Table created successfully.");


        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertItem() throws SQLException {
        String sql = "INSERT INTO items (serial_number, type_id, expiration_date, creation_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement
                (sql)) {
            // Insert first item
            stmt.setInt(1, 1);
            stmt.setInt(2, 10);
            stmt.setString(3, "1.1.1111");
            stmt.setString(4, "1.1.1111");
            stmt.executeUpdate();

            // Insert second item
            stmt.setInt(1, 2);
            stmt.setInt(2, 10);
            stmt.setString(3, "1.1.1111");
            stmt.setString(4, "1.1.1111");
            stmt.executeUpdate();

            // Insert third item
            stmt.setInt(1, 3);
            stmt.setInt(2, 20);
            stmt.setString(3, "1.1.1111");
            stmt.setString(4, "1.1.1111");
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
        sql = "INSERT INTO types (type_id,producer,category,sub_category,size,amount_on_shelves,amount_in_storage,selling_price," +
                "cost_price,minimal_amount,percentage_sale,amount_of_days_left_sale,supplier_sale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement
                     (sql)) {
            stmt.setInt(1, 10);
            stmt.setString(2, "tara");
            stmt.setString(3, "dairy");
            stmt.setString(4, "milk");
            stmt.setString(5, "100ml");
            stmt.setInt(6, 0);
            stmt.setInt(7, 0);
            stmt.setFloat(8, 7);
            stmt.setFloat(9, 7);
            stmt.setInt(10, 2);
            stmt.setInt(11, 0);
            stmt.setInt(12, 0);
            stmt.setInt(13, 0);
            stmt.executeUpdate();

            stmt.setInt(1, 20);
            stmt.setString(2, "tara");
            stmt.setString(3, "meat");
            stmt.setString(4, "stake");
            stmt.setString(5, "100g");
            stmt.setInt(6, 0);
            stmt.setInt(7, 0);
            stmt.setFloat(8, 10);
            stmt.setFloat(9, 10);
            stmt.setInt(10, 1);
            stmt.setInt(11, 0);
            stmt.setInt(12, 0);
            stmt.setInt(13, 0);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        createTable();
        insertItem();
    }
}
