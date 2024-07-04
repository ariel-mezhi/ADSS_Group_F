package DataAccessLayer;

import DomainLayer.Shift;
import DomainLayer.Transport;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransportDAO implements IDAO<Transport> {
    private Connection conn;

    public TransportDAO() throws SQLException {
        conn = Database.connect();
    }

    @Override
    public ArrayList<Transport> getAll() throws SQLException {
        ArrayList<Transport> transports = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM transportTable");

        while (rs.next()) {
             int TID = rs.getInt("TID");
             int BID = rs.getInt("BID");
             LocalDate date = stringToDate(rs.getString("DATE"));
             int weight = rs.getInt("WEIGHT");
             int shiftSign = rs.getInt("SHIFT_SIGN");
             boolean isConfirmed = rs.getBoolean("IS_CONFIRMED");
             String driver = rs.getString("DRIVER");

             Transport t = new Transport(TID, BID, date, weight, shiftSign);
             t.setDriver(driver);
             t.setConfirmed(isConfirmed);
             transports.add(t);

        }
        return transports;
    }

    @Override
    public Transport getById(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM transportTable WHERE TID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int TID = rs.getInt("TID");
            int BID = rs.getInt("BID");
            LocalDate date = stringToDate(rs.getString("DATE"));
            int weight = rs.getInt("WEIGHT");
            int shiftSign = rs.getInt("SHIFT_SIGN");
            boolean isConfirmed = rs.getBoolean("IS_CONFIRMED");
            String driver = rs.getString("DRIVER");

            Transport t = new Transport(TID, BID, date, weight, shiftSign);
            t.setDriver(driver);
            t.setConfirmed(isConfirmed);
            return t;
        }
        return null;
    }

    @Override
    public void addT(Transport t) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO transportTable " +
                "(TID, BID, DATE, WEIGHT, SHIFT_SIGN, IS_CONFIRMED, DRIVER) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");
        int TID = t.getTID();
        int BID = t.getBID();
        String date = t.getDate().toString();
        int weight = t.getWeight();
        int shiftSign = t.getShiftSign();
        boolean isConfirmed = t.isConfirmed();

        String driver = t.getDriver();
        stmt.setInt(1, TID);
        stmt.setInt(2, BID);
        stmt.setString(3, date);
        stmt.setInt(4, weight);
        stmt.setInt(5, shiftSign);
        stmt.setBoolean(6, isConfirmed);
        stmt.setString(7, driver);
        stmt.executeUpdate();
    }

    @Override
    public void updateT(Transport t) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE transportTable SET" +
                " BID = ?, DATE = ?, WEIGHT = ?, SHIFT_SIGN = ?, IS_CONFIRMED = ?, DRIVER = ? WHERE TID = ?");
        int BID = t.getBID();
        String date = t.getDate().toString();
        int weight = t.getWeight();
        int shiftSign = t.getShiftSign();
        boolean isConfirmed = t.isConfirmed();
        String driver = t.getDriver();

        stmt.setInt(1, BID);
        stmt.setString(2, date);
        stmt.setInt(3, weight);
        stmt.setInt(4, shiftSign);
        stmt.setBoolean(5, isConfirmed);
        stmt.setString(6, driver);
        stmt.setInt(7, t.getTID());
        stmt.executeUpdate();

    }

    @Override
    public void deleteT(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM transportTable WHERE TID = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public ArrayList<Integer> getTIDList() throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT TID FROM transportTable");
        while (rs.next()) {
            list.add((Integer.parseInt(rs.getString("TID"))));
        }
        return list;
    }




    // -- helping functions -- //
    private LocalDate stringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
