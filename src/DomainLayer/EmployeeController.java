package DomainLayer;

import DataAccessLayer.BranchDAO;
import DataAccessLayer.EmployeeDAO;
import ServiceLayer.Response;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class EmployeeController {
    private static EmployeeController instance;
    private ArrayList<Employee> employees;
    private ArrayList<Employee> firedEmployees;
    private ArrayList<Integer> branches;
    private InitSystem initSystem;
    public final BranchDAO branchDAO;
    public final EmployeeDAO employeeDAO;


    private EmployeeController() throws SQLException {
        initSystem = InitSystem.getInstance();
        branches = initSystem.loadBranches();
        employees = new ArrayList<>();
        firedEmployees = new ArrayList<>();
        branchDAO = new BranchDAO();
        loadEmployees();
        employeeDAO = new EmployeeDAO();
    }

    private void loadEmployees() throws SQLException {
        for (Employee e : initSystem.loadEmployees()){
            if (e.getEndContract().isAfter(LocalDate.now())){
                employees.add(e);
            }
            else {
                firedEmployees.add(e);
            }
        }
    }


    public static EmployeeController getInstance() throws SQLException {
        if (instance == null){
            instance = new EmployeeController();
        }
        return instance;
    }



    public void login(String userName, String password){
        Employee e = getEmployee(userName);
        if (e == null && !userName.equals("HR")){
            throw new RuntimeException("user don't exist");
        }
        if (!e.getPassword().equals(password)){
            throw new RuntimeException("wrong password");
        }
        e.setIsLoggedIn(true);
    }

    public boolean isStoreKeeper(String userName){
        Employee e = getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user don't exist");
        }
        return e.isStoreKeepeer();
    }

    public void logout(String userName){
        Employee e = getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user don't exist");
        }
        if (!e.getIsLoggedIn()){
            throw new RuntimeException("not logged in");
        }
        e.setIsLoggedIn(false);
    }

    public void changePassword(String userName, String newPassword) throws SQLException {
        Employee e = getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user don't exist");
        }
        if (!e.getIsLoggedIn()){
            throw new RuntimeException("not logged in");
        }
        e.setPassword(newPassword);
        employeeDAO.updateT(e);
    }

    public int daysLeftForContract(String userName){
        Employee e = getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user don't exist");
        }
        if (!e.getIsLoggedIn()){
            throw new RuntimeException("not logged in");
        }
        LocalDate end = e.getEndContract();
        LocalDate start = LocalDate.now();
        return (int) (ChronoUnit.DAYS.between(start, end));
    }

    public String addRoleToEmployee(String employeeName, String role) throws SQLException {
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("wrong employee name given");
        }
        if (!checkIfValidRole(role)){
            throw new RuntimeException("invalid role");
        }
        if (role.equals("shiftManager")){
            for (String _role : Role.getInstance().getRegularRoles()){
                if (!e.getRoles().contains(_role)){
                    e.getRoles().add(_role);
                    e.setLicense();
                }
            }
        }
        else if (!e.getRoles().contains(role)){
            e.getRoles().add(role);
            e.setLicense();
        }
        employeeDAO.updateT(e);
        return e.toString();
    }

    public void createNewRole(String role) throws SQLException {
        Role.getInstance().roleDAO.addT(Role.getInstance().getRoles().size() + role);
        Role.getInstance().addRole(role);
        for (Employee e : employees){
            if (e.getRoles().contains("shiftManager")){
                addRoleToEmployee(e.getUserName(), role);
            }
        }
    }

    public Employee hireEmployee(String BID, String employeeName, String bankAccount, String role) throws SQLException {
        Employee e = getEmployee(employeeName);
        Employee _e = getFiredEmployee(employeeName);
        if (e != null || _e != null){
            throw new RuntimeException("userName is taken, pick another");
        }
        if (!checkIfValidUserName(employeeName)){
            throw new RuntimeException("userName cannot include white spaces");
        }
        if (!checkIfValidRole(role)){
            throw new RuntimeException("invalid role");
        }
        int bid = convertStringToInt(BID);
        Employee employee = new Employee(bid, employees.size() + firedEmployees.size() , employeeName, bankAccount);
        employees.add(employee);
        addRoleToEmployee(employeeName, role);
        employeeDAO.addT(employee);
        return employee;
    }

    public String fireEmployee(String employeeName) throws SQLException {
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("userName isn't exist");
        }
        e.setEndContract(LocalDate.now());
        employees.remove(e);
        firedEmployees.add(e);
        employeeDAO.updateT(e);
        return "end of contract for " +e.getUserName() +": " +e.getEndContract();
    }

    public String changeEmployeeGlobal(String employeeName, String newGlobalSalary) throws SQLException {
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("userName isn't exist");
        }
        int newSalary = convertStringToInt(newGlobalSalary);
        e.setHourlySalary(0);
        e.setGlobalSalary(newSalary);
        employeeDAO.updateT(e);
        return e.toString();
    }

    public String changeEmployeeHourly(String employeeName, String newHourlySalary) throws SQLException {
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("userName isn't exist");
        }
        int newSalary = convertStringToInt(newHourlySalary);
        e.setHourlySalary(newSalary);
        e.setGlobalSalary(0);
        employeeDAO.updateT(e);
        return  e.toString();

    }

    public String extendContract(String employeeName, String endContractDate) throws SQLException {
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("userName isn't exist");
        }
        LocalDate date = stringToDateOrException(endContractDate);
        e.setEndContract(date);
        employeeDAO.updateT(e);
        return e.toString();
    }

    public String showEmployeesPerformance(){
        String st = "";
        for (Employee e : employees){
            st = st + "name:  " + e.getUserName() + "..........performance: " + e.getPerformance() + "\n";
        }
        return st;
    }

    public String changePerformance(String employeeName, String performance) throws SQLException {
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("incorrect employee name");
        }
        int _performance = convertStringToInt(performance);
        if (_performance < 1 || _performance > 10){
            throw new RuntimeException("performance should be 1-10");
        }
        e.setPerformance(_performance);
        employeeDAO.updateT(e);
        return e.toString();
    }


    public Employee getEmployee(String userName){
        for (Employee e : employees){
            if (e.getUserName().equals(userName)){
                return e;
            }
        }
        return null;
    }

    public Employee getFiredEmployee(String userName){
        for (Employee e : firedEmployees){
            if (e.getUserName().equals(userName)){
                return e;
            }
        }
        return null;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void getInOrKickOut(String userName){
        Employee e = getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user doesn't exist");
        }
        if (!e.getIsLoggedIn()){
            throw new RuntimeException("not logged in");
        }
    }

    public void HR_Or_KickOut(String userName){
        Employee e = getEmployee(userName);
        if (e == null || !e.getUserName().equals("HR")){
            throw new RuntimeException("you are not HR");
        }
        if (!e.getIsLoggedIn()){
            throw new RuntimeException("Hey boss, please login :D");
        }
    }
    public void TM_Or_KickOut(String userName){
        Employee e = getEmployee(userName);
        if (e == null || !e.getUserName().equals("TM")){
            throw new RuntimeException("you are not TM");
        }
        if (!e.getIsLoggedIn()){
            throw new RuntimeException("Hey boss, please login :^)");
        }
    }



    public ArrayList<Integer> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<Integer> branches) {
        this.branches = branches;
    }

    public void setFiredEmployees(ArrayList<Employee> firedEmployees) {
        this.firedEmployees = firedEmployees;
    }

    public ArrayList<Employee> getFiredEmployees() {
        return firedEmployees;
    }

    public boolean checkIfValidRole(String role) throws SQLException {
        return Role.getInstance().containsRole(role);
    }

    public void exceptionIfInvalidBID(String BID){
        int bid = convertStringToInt(BID);
        if (branches.contains(bid)){
            throw new RuntimeException("illegal BID");
        }
    }

    public void addBranch() throws SQLException {
        Integer BID = branches.size();
        branches.add(BID, BID);
        branchDAO.addT(BID);
    }

    private int convertStringToInt(String shift){
        int s;
        try{
            s = Integer.parseInt(shift);
        }catch (RuntimeException e){
            throw new RuntimeException("invalid value, you must provide a number");
        }
        return s;
    }

    private boolean checkIfValidUserName(String name){
        return !name.matches(".*\\s.*");
    }

    private LocalDate stringToDateOrException(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format. Please use yyyy-MM-dd format.");
        }
    }
}
