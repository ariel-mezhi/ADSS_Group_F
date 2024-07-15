package DataAccessLayer;

import DomainLayer.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO implements IDAO<Integer>{
    private Connection conn;

    public BranchDAO() throws SQLException {
        conn = Database.connect();
    }
    @Override
    public ArrayList<Integer> getAll() throws SQLException {
        ArrayList<Integer> branches = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM branchTable");
        while (rs.next()) {
            branches.add(rs.getInt("BID"));
        }
        return branches;
    }

    @Override
    public Integer getById(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM branchTable WHERE BID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("BID");
        }
        return null;
    }

    public boolean getChangesAllowed(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM branchTable WHERE BID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getBoolean("CHANGES_ALLOWED");
        }
        throw new RuntimeException("Something went wrong :P");
    }

    public boolean getIsPublished(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM branchTable WHERE BID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getBoolean("IS_PUBLISHED");
        }
        throw new RuntimeException("Something went wrong :P");
    }

    @Override
    public void addT(Integer BID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO branchTable (BID, CHANGES_ALLOWED, IS_PUBLISHED) VALUES (?,?,?)");
        stmt.setInt(1, BID);
        stmt.setBoolean(2, true);
        stmt.setBoolean(3, false);
        stmt.executeUpdate();
    }

    @Override
    public void updateT(Integer object) throws SQLException {
        object -= 1000;
        boolean allowChanges = (object / 100) != 0;
        object = object % 100;
        boolean isPublished = (object / 10) != 0;
        int BID = object% 10;

        PreparedStatement stmt = conn.prepareStatement("UPDATE branchTable SET" +
                " CHANGES_ALLOWED = ?, IS_PUBLISHED = ? WHERE BID = ?");

        stmt.setBoolean(1, allowChanges);
        stmt.setBoolean(2, isPublished);
        stmt.setInt(3, BID);
        stmt.executeUpdate();
    }

    public void updateChangesAllow(int bid, boolean allow) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE branchTable SET" +
                " CHANGES_ALLOWED = ? WHERE BID = ?");

        stmt.setBoolean(1, allow);
        stmt.setInt(2, bid);
        stmt.executeUpdate();
    }

    public void updateIsPublished(int bid, boolean publish) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE branchTable SET" +
                " IS_PUBLISHED = ? WHERE BID = ?");

        stmt.setBoolean(1, publish);
        stmt.setInt(2, bid);
        stmt.executeUpdate();
    }

    @Override
    public void deleteT(int id) throws SQLException {
    }
}
