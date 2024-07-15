package DataAccessLayer;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\omert\\Desktop\\adss_f_final_ver\\ADSS_Group_F\\projectDataBase.db";
    // matan url: jdbc:sqlite:C:/Users/מתן איסר/Desktop/projectNituz/projectDataBase.db
    private static Connection connection = null;

    private Database() {}

    // Get a single instance of the database connection
    public static synchronized Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    // Close the database connection
    public static synchronized void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }




    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employeeTable (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT NOT NULL,\n"
                + " capacity REAL\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            System.out.println("Executing table creation statement...");
            stmt.execute(sql);
            System.out.println("Table created successfully.");


        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }

        String sql2 = """
                CREATE TABLE IF NOT EXISTS items (
                 serial_number INTEGER PRIMARY KEY AUTOINCREMENT,
                 type_id INTEGER,
                 expiration_date TEXT,
                 creation_date TEXT,
                 location TEXT
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
            stmt.execute(sql2);
            stmt.execute(sql1);
            System.out.println("Table created successfully.");


        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertItem() throws SQLException {
        String sql = "INSERT INTO items (serial_number, type_id, expiration_date, creation_date,location) VALUES (?, ?, ?, ?,?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement
                     (sql)) {
            // Insert first item
            stmt.setInt(1, 1);
            stmt.setInt(2, 10);
            stmt.setString(3, "1.1.1111");
            stmt.setString(4, "1.1.1111");
            stmt.setString(5, "storage");
            stmt.executeUpdate();

            // Insert second item
            stmt.setInt(1, 2);
            stmt.setInt(2, 10);
            stmt.setString(3, "1.1.1111");
            stmt.setString(4, "1.1.1111");
            stmt.setString(5, "storage");
            stmt.executeUpdate();

            // Insert third item
            stmt.setInt(1, 3);
            stmt.setInt(2, 20);
            stmt.setString(3, "1.1.1111");
            stmt.setString(4, "1.1.1111");
            stmt.setString(5, "storage");
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
            stmt.setInt(7, 2);
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
            stmt.setInt(7, 1);
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