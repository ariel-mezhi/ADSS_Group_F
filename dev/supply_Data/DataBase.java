package supply_Data;

import java.sql.*;

public class DataBase {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/Yuval Ellins/Desktop/projects/ADSS_Group_F/MyDataBase.db";
    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(DB_URL);
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

    public static void main(String[] args) throws SQLException {
        createTable();
    }
}
