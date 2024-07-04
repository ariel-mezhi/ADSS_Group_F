package DataAccessLayer;

import DomainLayer.Employee;
import DomainLayer.Shift;
import DomainLayer.Transport;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftDAO implements IDAO<Shift>{
    private Connection conn;

    public ShiftDAO() throws SQLException {
        conn = Database.connect();
    }

    @Override
    public ArrayList<Shift> getAll() throws SQLException {
        ArrayList<Shift> shifts = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM shiftTable");

        while (rs.next()) {
             int branchID = rs.getInt("BID");
             LocalDate date = stringToDate(rs.getString("DATE"));
             ArrayList<String[]> employees = splitWords(rs.getString("EMPLOYEES"));
             int shiftID = rs.getInt("SID");
             int sign = rs.getInt("SIGN");
             boolean isCurrentWeek = rs.getBoolean("IS_CURRENT_WEEK");
             boolean isNextWeek = rs.getBoolean("IS_NEXT_WEEK");
             boolean isHoliday = rs.getBoolean("IS_HOLIDAY");
             ///
             String availableByID = rs.getString("AVAILABLE");
             String[] arr = availableByID.split("\\s+");
             ArrayList<Employee> availableEmployees = new  ArrayList<>();
             for (String st : arr){
                 if (!st.isEmpty()) {
                     availableEmployees.add(convertStringToEmployee(st));
                 }
             }
             ///
             HashMap<String, Integer> requirements = new HashMap<>();
             String[] requirementsArr = rs.getString("REQUIREMENTS").split("\\s+");
             for (String st : requirementsArr){
                   String[] arrMap = st.split("\\.");
                   requirements.put(arrMap[0], Integer.parseInt(arrMap[1]));
             }
             //


             Shift shift = new Shift(shiftID, branchID, date, sign, isHoliday);
             shift.setCurrentWeek(isCurrentWeek);
             shift.setNextWeek(isNextWeek);
             shift.setRequirements(requirements);
             shift.setAvailableEmployees(availableEmployees);
             shift.setEmployees(employees);

             shifts.add(shift);
        }
        return shifts;

    }

    @Override
    public Shift getById(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM shiftTable WHERE SID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int branchID = rs.getInt("BID");
            LocalDate date = stringToDate(rs.getString("DATE"));
            ArrayList<String[]> employees = splitWords(rs.getString("EMPLOYEES"));
            int shiftID = rs.getInt("SID");
            int sign = rs.getInt("SIGN");
            boolean isCurrentWeek = rs.getBoolean("IS_CURRENT_WEEK");
            boolean isNextWeek = rs.getBoolean("IS_NEXT_WEEK");
            boolean isHoliday = rs.getBoolean("IS_HOLIDAY");
            ///
            String availableByID = rs.getString("AVAILABLE");
            String[] arr = availableByID.split("\\s+");
            ArrayList<Employee> availableEmployees = new  ArrayList<>();
            for (String st : arr){
                availableEmployees.add(convertStringToEmployee(st));
            }
            ///
            HashMap<String, Integer> requirements = new HashMap<>();
            String[] requirementsArr = rs.getString("REQUIREMENTS").split("\\s+");
            for (String st : requirementsArr){
                String[] arrMap = st.split("\\.");
                requirements.put(arrMap[0], Integer.parseInt(arrMap[1]));
            }
            //

            Shift shift = new Shift(shiftID, branchID, date, sign, isHoliday);
            shift.setCurrentWeek(isCurrentWeek);
            shift.setNextWeek(isNextWeek);
            shift.setRequirements(requirements);
            shift.setAvailableEmployees(availableEmployees);
            shift.setEmployees(employees);

            return shift;
        }
        return null;
    }

    @Override
    public void addT(Shift s) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO shiftTable " +
                "(SID, BID, DATE, EMPLOYEES, SIGN, IS_CURRENT_WEEK, IS_NEXT_WEEK, IS_HOLIDAY, AVAILABLE, REQUIREMENTS)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        int sid = s.getShiftID();
        int bid = s.getBranchID();
        String date = s.getDate().toString();
        String employees = "";
        for (String[] arr : s.getEmployees()){
            employees = employees + " " + arr[0] + "." + arr[1] + "." + arr[2] + "." + arr[3];
        }
        if(!employees.isEmpty()) {
            employees = employees.substring(1);
        }
        int sign = s.getSign();
        boolean isCurrentWeek = s.isCurrentWeek();
        boolean isNextWeek = s.isNextWeek();
        boolean isHoliday = s.isHoliday();
        String available = "";
        for (Employee e : s.getAvailableEmployees()){
            available = available + " " + e.getEID();
        }
        if (!available.isEmpty()) {
            available = available.substring(1);
        }
        StringBuilder requirements = new StringBuilder();
        s.getRequirements().forEach((role, num) -> {
            requirements.append(" ").append(role).append(".").append(num);
        });
        String Requirements = requirements.substring(1);


        stmt.setInt(1,sid);
        stmt.setInt(2,bid);
        stmt.setString(3,date);
        stmt.setString(4,employees);
        stmt.setInt(5,sign);
        stmt.setBoolean(6,isCurrentWeek);
        stmt.setBoolean(7,isNextWeek);
        stmt.setBoolean(8,isHoliday);
        stmt.setString(9,available);
        stmt.setString(10,Requirements);
        stmt.executeUpdate();

    }

    @Override
    public void updateT(Shift s) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE shiftTable SET" +
                " BID = ?, DATE = ?, EMPLOYEES = ?, SIGN = ?, IS_CURRENT_WEEK = ?, IS_NEXT_WEEK = ?, IS_HOLIDAY = ?, AVAILABLE = ?, REQUIREMENTS = ? WHERE SID = ?");
        int bid = s.getBranchID();
        String date = s.getDate().toString();
        String employees = "";
        for (String[] arr : s.getEmployees()){
            if (arr.length > 1) {
                employees = employees + " " + arr[0] + "." + arr[1] + "." + arr[2] + "." + arr[3];
            }
        }
        if (!employees.isEmpty()) {
            employees = employees.substring(1);
        }
        int sign = s.getSign();
        boolean isCurrentWeek = s.isCurrentWeek();
        boolean isNextWeek = s.isNextWeek();
        boolean isHoliday = s.isHoliday();
        String available = "";
        for (Employee e : s.getAvailableEmployees()){
            available = available + " " + e.getEID();
        }
        if (!available.isEmpty()) {
            available = available.substring(1);
        }
        StringBuilder requirements = new StringBuilder();
        s.getRequirements().forEach((role, num) -> {
            requirements.append(" ").append(role).append(".").append(num);
        });

        String Requirements = requirements.substring(1);

        stmt.setInt(1,bid);
        stmt.setString(2,date);
        stmt.setString(3,employees);
        stmt.setInt(4,sign);
        stmt.setBoolean(5,isCurrentWeek);
        stmt.setBoolean(6,isNextWeek);
        stmt.setBoolean(7,isHoliday);
        stmt.setString(8,available);
        stmt.setString(9,Requirements);
        stmt.setInt(10,s.getShiftID());
        stmt.executeUpdate();
    }

    @Override
    public void deleteT(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM shiftTable WHERE SID = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    // -- helping functions -- //
    private LocalDate stringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private ArrayList<String[]> splitWords(String input) {
        // Split the input string by whitespace               //ariel.manager matan.chef
        String[] wordsArray = input.split("\\s+");      //worldsArray = [ariel.manager, matan.chef]

        // Convert the array to an ArrayList
        ArrayList<String[]> wordsList = new ArrayList<>();    //wordList = {}
        for (String word : wordsArray) {                      //ariel.manager ---> worldList = {[ariel, manager]}
            String[] parts = word.split("\\.");
            wordsList.add(parts);
        }

        return wordsList;
    }


    private Employee convertStringToEmployee(String EID) throws SQLException {
        int eid = Integer.parseInt(EID);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee = employeeDAO.getById(eid);
        return employee;
    }





}
