package DomainLayer;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class EmployeeController {
    private static EmployeeController instance;
    private ArrayList<Employee> employees;
    private ArrayList<Employee> firedEmployees;
    private ArrayList<Integer> branches;


    private EmployeeController(){
        Config c = new Config();
        c.use();
        branches = new ArrayList<>();
        employees = new ArrayList<>();
        firedEmployees = new ArrayList<>();

        loadBranches();

        employees.add(new Employee());
    }

    private void loadBranches(){
        this.branches.add(0);
    }


    public static EmployeeController getInstance(){
        if (instance == null){
            instance = new EmployeeController();
        }
        return instance;
    }



    public void login(String userName, String password){
        Employee e = getEmployee(userName);
        if (e == null){
            //for test -> HR creation
            if (userName.equals("HR")){
                employees.add(new Employee());
            }
            else{
                throw new RuntimeException("user don't exist");
            }
        }
        if (!e.getPassword().equals(password)){
            throw new RuntimeException("wrong password");
        }
        e.setIsLoggedIn(true);
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

    public void changePassword(String userName, String newPassword){
        Employee e = getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user don't exist");
        }
        if (!e.getIsLoggedIn()){
            throw new RuntimeException("not logged in");
        }
        e.setPassword(newPassword);
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

    public void addRoleToEmployee(String employeeName, String role){
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("wrong employee name given");
        }
        if (!checkIfValidRole(role)){
            throw new RuntimeException("invalid role");
        }
        if (role.equals("shiftManager")){
            e.setShiftManager(true);
            for (String _role : Role.getInstance().getRoles()){
                if (!e.getRoles().contains(_role)){
                    e.getRoles().add(_role);
                }
            }
        }
        else if (!e.getRoles().contains(role)){
            e.getRoles().add(role);
        }
    }

    public void hireEmployee(String BID, String employeeName, String bankAccount, String role){
        Employee e = getEmployee(employeeName);
        if (e != null){
            throw new RuntimeException("userName is taken, pick another");
        }
        if (!checkIfValidUserName(employeeName)){
            throw new RuntimeException("userName cannot include white spaces");
        }
        if (!checkIfValidRole(role)){
            throw new RuntimeException("invalid role");
        }
        int bid = convertStringToInt(BID);
        Employee employee = new Employee(bid, employeeName, bankAccount);
        employees.add(employee);
        addRoleToEmployee(employeeName, role);
    }

    public String fireEmployee(String employeeName){
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("userName isn't exist");
        }
        e.setEndContract(LocalDate.now());
        employees.remove(e);
        firedEmployees.add(e);
        return employeeName + " is fired";
    }

    public void changeEmployeeGlobal(String employeeName, String newGlobalSalary){
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("userName isn't exist");
        }
        int newSalary = convertStringToInt(newGlobalSalary);
        e.setHourlySalary(0);
        e.setGlobalSalary(newSalary);
    }

    public void changeEmployeeHourly(String employeeName, String newHourlySalary){
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("userName isn't exist");
        }
        int newSalary = convertStringToInt(newHourlySalary);
        e.setHourlySalary(newSalary);
        e.setGlobalSalary(0);
    }

    public void extendContract(String employeeName, String endContractDate){
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("userName isn't exist");
        }
        LocalDate date = stringToDateOrException(endContractDate);
        e.setEndContract(date);
    }

    public String showEmployeesPerformance(){
        String st = "";
        for (Employee e : employees){
            st = st + "name:  " + e.getUserName() + "..........performance: " + e.getPerformance() + "\n";
        }
        return st;
    }

    public void changePerformance(String employeeName, String performance){
        Employee e = getEmployee(employeeName);
        if (e == null){
            throw new RuntimeException("incorrect employee name");
        }
        int _performance = convertStringToInt(performance);
        if (_performance < 1 || _performance > 10){
            throw new RuntimeException("performance should be 1-10");
        }
        e.setPerformance(_performance);
    }


    public Employee getEmployee(String userName){
        for (Employee e : employees){
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
            throw new RuntimeException("hey boss, please login");
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

    public boolean checkIfValidRole(String role){
        return Role.getInstance().containsRole(role);
    }

    public void exceptionIfInvalidBID(String BID){
        int bid = convertStringToInt(BID);
        if (branches.contains(bid)){
            throw new RuntimeException("illegal BID");
        }
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

    public void Config_setEmployees(ArrayList<Employee> _employees){
        for (Employee e : _employees){
            this.employees.add(e);
        }
    }
}
