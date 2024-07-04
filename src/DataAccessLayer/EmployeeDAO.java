package DataAccessLayer;

import DomainLayer.Employee;

import java.sql.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IDAO<Employee>{
    private Connection conn;

    public EmployeeDAO() throws SQLException {
        conn = Database.connect();
    }
    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM employeeTable");


        while (rs.next()) {
            int EID = rs.getInt("EID");
            String userName = rs.getString("USERNAME");
            String password = rs.getString("PASSWORD");
            int branchID = rs.getInt("BID");
            String bankAccount = rs.getString("BANK_ACCOUNT");
            ArrayList<String> roles = splitWords(rs.getString("ROLES"));
            int hourlySalary = rs.getInt("HOURLY_SALARY");
            int globalSalary = rs.getInt("GLOBAL_SALARY");
            int performance = rs.getInt("PERFORMANCE");
            LocalDate startContract = stringToDate(rs.getString("START_CONTRACT"));
            LocalDate endContract = stringToDate(rs.getString("END_CONTRACT"));
           // String license = rs.getString("LICENSE");

            Employee e = new Employee(branchID, EID, userName, bankAccount);
            e.setPassword(password);
            e.setRoles(roles);
            e.setHourlySalary(hourlySalary);
            e.setGlobalSalary(globalSalary);
            e.setPerformance(performance);
            e.setStartContract(startContract);
            e.setEndContract(endContract);
            e.setLicense();

            employees.add(e);
        }
        return employees;
     }



    @Override
    public Employee getById(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employeeTable WHERE EID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int EID = rs.getInt("EID");
            String userName = rs.getString("USERNAME");
            String password = rs.getString("PASSWORD");
            int branchID = rs.getInt("BID");
            String bankAccount = rs.getString("BANK_ACCOUNT");
            ArrayList<String> roles = splitWords(rs.getString("ROLES"));
            int hourlySalary = rs.getInt("HOURLY_SALARY");
            int globalSalary = rs.getInt("GLOBAL_SALARY");
            int performance = rs.getInt("PERFORMANCE");
            LocalDate startContract = stringToDate(rs.getString("START_CONTRACT"));
            LocalDate endContract = stringToDate(rs.getString("END_CONTRACT"));
            // String license = rs.getString("LICENSE");

            Employee e = new Employee(branchID, EID, userName, bankAccount);
            e.setPassword(password);
            e.setRoles(roles);
            e.setHourlySalary(hourlySalary);
            e.setGlobalSalary(globalSalary);
            e.setPerformance(performance);
            e.setStartContract(startContract);
            e.setEndContract(endContract);
            e.setLicense();
            return e;
        }
        return null;
    }

    @Override
    public void addT(Employee e) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO employeeTable " +
                "(EID, USERNAME, PASSWORD, BID, BANK_ACCOUNT, ROLES, HOURLY_SALARY, GLOBAL_SALARY, PERFORMANCE, START_CONTRACT, END_CONTRACT)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        int EID = e.getEID();
        String userName = e.getUserName();
        String password = e.getPassword();
        int branchID = e.getBranchID();
        String bankAccount = e.getBankAccount();
        ArrayList<String> Array_roles = e.getRoles();
        String roles ="";
        for (String role : Array_roles){
            roles = roles + " " + role;
        }
        if (!roles.isEmpty()) {
            roles = roles.substring(1);
        }

        int hourlySalary = e.getHourlySalary();
        int globalSalary = e.getGlobalSalary();
        int performance = e.getPerformance();
        String startContract = e.getStartContract().toString();
        String endContract = e.getEndContract().toString();

        stmt.setInt(1,EID);
        stmt.setString(2, userName);
        stmt.setString(3, password);
        stmt.setInt(4, branchID);
        stmt.setString(5, bankAccount);
        stmt.setString(6, roles);
        stmt.setInt(7, hourlySalary);
        stmt.setInt(8, globalSalary);
        stmt.setInt(9, performance);
        stmt.setString(10, startContract);
        stmt.setString(11, endContract);
        stmt.executeUpdate();
    }

    @Override
    public void updateT(Employee e) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE employeeTable SET" +
                " USERNAME = ?, PASSWORD = ?, BID = ?, BANK_ACCOUNT = ?, ROLES = ?, HOURLY_SALARY = ?, GLOBAL_SALARY = ?, PERFORMANCE = ?, START_CONTRACT = ?, END_CONTRACT = ? WHERE EID = ?");
        int EID = e.getEID();
        String userName = e.getUserName();
        String password = e.getPassword();
        int branchID = e.getBranchID();
        String bankAccount = e.getBankAccount();
        ArrayList<String> Array_roles = e.getRoles();
        String roles ="";
        for (String role : Array_roles){
            roles = roles + " " + role;
        }
        roles = roles.substring(1);

        int hourlySalary = e.getHourlySalary();
        int globalSalary = e.getGlobalSalary();
        int performance = e.getPerformance();
        String startContract = e.getStartContract().toString();
        String endContract = e.getEndContract().toString();

        stmt.setString(1, userName);
        stmt.setString(2, password);
        stmt.setInt(3, branchID);
        stmt.setString(4, bankAccount);
        stmt.setString(5, roles);
        stmt.setInt(6, hourlySalary);
        stmt.setInt(7, globalSalary);
        stmt.setInt(8, performance);
        stmt.setString(9, startContract);
        stmt.setString(10, endContract);
        stmt.setInt(11,EID);
        stmt.executeUpdate();
    }

    @Override
    public void deleteT(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM employeeTable WHERE EID = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }




    // -- helping functions -- //
    private LocalDate stringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
    private ArrayList<String> splitWords(String input) {
        // Split the input string by whitespace
        String[] wordsArray = input.split("\\s+");

        // Convert the array to an ArrayList
        ArrayList<String> wordsList = new ArrayList<>();
        for (String word : wordsArray) {
            wordsList.add(word);
        }

        return wordsList;
    }
}
