package DomainLayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Employee {
    private final int EID;
    private String userName;
    private String password;
    private int branchID;
    private boolean isLoggedIn;
    private String bankAccount;
    private ArrayList<String> roles;
    private int hourlySalary;
    private int globalSalary;
    private int performance;
    private LocalDate startContract;
    private LocalDate endContract;
    private String license;


    public Employee(int BID, int eid, String userName, String bankAccount){
        this.EID = eid;
        this.branchID = BID;
        this.userName = userName;
        this.bankAccount = bankAccount;
        this.roles = new ArrayList<>();
        this.hourlySalary = 30;
        this.globalSalary = 0;
        this.startContract = LocalDate.now();
        this.endContract = startContract.plusYears(1);
        this.password = userName;
        this.isLoggedIn = false;
        this.performance = 5;
        this.license = "";
    }

    public Employee(String name, int eid){
        this.EID = eid;
        this.userName = name;
        this.password = name;
        this.branchID = -1;
        this.bankAccount = name;
        this.roles = new ArrayList<>();
        this.hourlySalary = 0;
        this.globalSalary = 0;
        this.startContract = LocalDate.now();
        this.endContract = startContract.plusYears(10);
        this.isLoggedIn = false;
        this.performance = 10;
    }

    public boolean isStoreKeepeer(){
        return this.roles.contains("storekeeper");
    }




    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public int getHourlySalary() {
        return hourlySalary;
    }

    public void setHourlySalary(int hourlySalary) {
        this.hourlySalary = hourlySalary;
    }

    public int getGlobalSalary() {
        return globalSalary;
    }

    public void setGlobalSalary(int globalSalary) {
        this.globalSalary = globalSalary;
    }

    public LocalDate getStartContract() {
        return startContract;
    }

    public void setStartContract(LocalDate startContract) {
        this.startContract = startContract;
    }

    public LocalDate getEndContract() {
        return endContract;
    }

    public void setEndContract(LocalDate endContract) {
        this.endContract = endContract;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public int getEID() {
        return EID;
    }

    public String getLicense() {
        return license;
    }
    public void setLicense() {
        if (roles.contains("driver_C")){
            license = "C";
            roles.add("driver_C1");
            roles.add("driver_B");
        }
        else if (roles.contains("driver_C1")){
            license = "C1";
            roles.add("driver_B");
        }
        else if (roles.contains("driver_B")){
            license = "B";
        }
        else{
            license = "";
        }
    }

    public int getMaximalWeight(){
        if (this.license.equals("B")){
            return 3500;
        }
        if (this.license.equals("C1")){
            return 12000;
        }
            return Integer.MAX_VALUE;
    }
    @Override
    public boolean equals(Object employee){
        if (employee instanceof Employee){
            return this.EID == ((Employee) employee).getEID();
        }
        return false;
    }

    @Override
    public String toString() {
        String name = "name: " + userName +"\n";
        String branch = "branch: " + branchID + "\n";
        String roles = "roles: ";
        for (String role : this.roles){
            roles = roles + "   " + role;
        }
        roles += "\n";
        String salary = ((globalSalary == 0)? "hourly salary: " + this.hourlySalary : "global salary: " + this.globalSalary) + "\n";
        String performance = "performance: " + this.performance + "\n";
        String start = "start of contract date: " + this.startContract.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n";
        String end = "end of contract date: " + this.endContract.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n";
        return "employee's data: \n" + name + branch + roles + salary + performance + start + end;
    }
}
