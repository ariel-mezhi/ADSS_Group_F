package supply_Data;

import java.sql.*;

public class DataBase {
    private static final String DB_URL = "add our url";
    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }
}
