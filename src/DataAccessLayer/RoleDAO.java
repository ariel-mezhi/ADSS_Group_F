package DataAccessLayer;

import DomainLayer.Transport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO implements IDAO<String>{
    private Connection conn;

    public RoleDAO() throws SQLException {
        conn = Database.connect();
    }
    @Override
    public ArrayList<String> getAll() throws SQLException {
        ArrayList<String> roles = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM roleTable");
        while (rs.next()) {
            roles.add(rs.getString("ROLE_NAME"));
        }
        return roles;
    }

    @Override
    public String getById(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM roleTable WHERE RID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("ROLE_NAME");
        }
        return null;
    }

    @Override
    public void addT(String role) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO roleTable " +
                "(ROLE_NAME, RID)" +
                "VALUES (?, ?)");
        int RID = role.charAt(0) - 48;
        role = role.substring(1);
        stmt.setString(1, role);
        stmt.setInt(2, RID);
        stmt.executeUpdate();
    }

    @Override
    public void updateT(String role) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE roleTable SET" +
                "RID = ? WHERE ROLE_NAME = ?");
        int RID = role.charAt(0);
        role = role.substring(1);
        stmt.setInt(1, RID);
        stmt.setString(2, role);
        stmt.executeUpdate();
    }

    @Override
    public void deleteT(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM roleTable WHERE RID = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
        ArrayList<String> roles = getAll();
        int i = 0;
        for (String role : roles) {
            updateT(i+role);
            i ++;
        }
    }
}
