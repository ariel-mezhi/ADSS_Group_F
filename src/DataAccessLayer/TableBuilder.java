package DataAccessLayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableBuilder {
    private Connection conn;

    public TableBuilder() throws SQLException {
        conn = Database.connect();
    }

    public void createShiftTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS shiftTable ("
                + "SID INTEGER PRIMARY KEY, "
                + "BID INTEGER, "
                + "DATE TEXT, "
                + "EMPLOYEES TEXT, "
                + "SIGN INTEGER, "
                + "IS_CURRENT_WEEK BOOLEAN, "
                + "IS_NEXT_WEEK BOOLEAN, "
                + "IS_HOLIDAY BOOLEAN, "
                + "AVAILABLE TEXT, "
                + "REQUIREMENTS TEXT"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createBranchTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS branchTable ("
                + "BID INTEGER PRIMARY KEY, "
                + "CHANGES_ALLOWED BOOLEAN, "
                + "IS_PUBLISHED BOOLEAN"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createEmployeeTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS employeeTable ("
                + "EID INTEGER PRIMARY KEY, "
                + "USERNAME TEXT, "
                + "PASSWORD TEXT, "
                + "BID INTEGER, "
                + "BANK_ACCOUNT TEXT, "
                + "ROLES TEXT, "
                + "HOURLY_SALARY INTEGER, "
                + "GLOBAL_SALARY INTEGER, "
                + "PERFORMANCE INTEGER, "
                + "START_CONTRACT TEXT, "
                + "END_CONTRACT TEXT"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createRoleTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS roleTable ("
                + "ROLE_NAME TEXT PRIMARY KEY, "
                + "RID INTEGER"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void createTransportTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS transportTable ("
                + "TID INTEGER PRIMARY KEY, "
                + "BID INTEGER, "
                + "DATE TEXT, "
                + "WEIGHT INTEGER, "
                + "SHIFT_SIGN INTEGER, "
                + "IS_CONFIRMED BOOLEAN, "
                + "DRIVER TEXT"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void run() {
        try {
            createBranchTable();
            createRoleTable();
            createTransportTable();
            createEmployeeTable();
            createShiftTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
