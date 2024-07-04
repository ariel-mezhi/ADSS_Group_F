package DataAccessLayer;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/מתן איסר/Desktop/projectNituz/projectDataBase.db";
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
    }

    public static void addTo() throws SQLException {
        Connection conn = Database.connect();
        Statement stmt = conn.createStatement();
        PreparedStatement _stmt = conn.prepareStatement("INSERT INTO employeeTable (id, name, capacity) VALUES (?, ?, ?)");
        _stmt.setString(1, "1");
        _stmt.setString(2, "Matan");
        _stmt.setString(3, "2.0");
        _stmt.executeUpdate();
    }

    public static void getIt() throws SQLException {

        Connection conn = Database.connect();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employeeTable WHERE id = ?");
        stmt.setInt(1, 1);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            System.out.println("Name: " + rs.getString("name"));
        }
    }


    public static void main(String[] args) throws SQLException {
        //createTable();
        //addTo();
        //getIt();
    }
}