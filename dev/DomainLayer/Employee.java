package DomainLayer;
import java.util.ArrayList;
import java.time.LocalDate;

public class Employee {
    private String userName;
    private String password;
    private int branchID;
    private boolean isLoggedIn;
    private String bankAccount;
    private boolean isShiftManager;
    private ArrayList<String> roles;
    private int hourlySalary;
    private int globalSalary;
    private int performance;
    private LocalDate startContract;
    private LocalDate endContract;

    public Employee(int BID, String userName, String bankAccount){
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
    }

    public Employee(){
        this.userName = "HR";
        this.password = "HR";
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

    public boolean isShiftManager() {
        return isShiftManager;
    }

    public void setShiftManager(boolean isShiftManager) {
        this.isShiftManager = isShiftManager;
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

}
