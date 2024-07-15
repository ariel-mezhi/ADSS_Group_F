package DomainLayer;

import DataAccessLayer.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class InitSystem {

    public static InitSystem getInstance() throws SQLException {
        if (instance == null) {
            instance = new InitSystem();
        }
        return instance;
    }


    private static InitSystem instance = null;

    public TableBuilder tableBuilder;
    private BranchDAO branchDAO;
    private EmployeeDAO employeeDAO;
    private ShiftDAO shiftDAO;
    private RoleDAO roleDAO;
    private TransportDAO transportDAO;

    private InitSystem() throws SQLException {
        tableBuilder = new TableBuilder();
        branchDAO = new BranchDAO();
        employeeDAO = new EmployeeDAO();
        shiftDAO = new ShiftDAO();
        roleDAO = new RoleDAO();
        transportDAO = new TransportDAO();
        tableBuilder.run();
    }



    public ArrayList<Employee> loadEmployees() throws SQLException {
        ArrayList<Employee> employees = employeeDAO.getAll();
        if (employees.isEmpty()){
            Employee HR = new Employee("HR", 0);
            employeeDAO.addT(HR);
            employees.add(HR);
            Employee TM = new Employee("TM", 1);
            employeeDAO.addT(TM);
            employees.add(TM);
            Employee SM = new Employee("SM", 2);
            employeeDAO.addT(SM);
            employees.add(SM);
        }
        return employees;

//        int i = 3;
//        for (String role : Role.getInstance().getRoles()){
//            add_20_Employees(role,i);
//            i +=20;
//        }
//        Employee Ariel = new Employee(0, 2, "ariel", "0");
//        Ariel.getRoles().add("shiftManager");
//        employees.add(Ariel);
//        Employee HR = new Employee("HR", 0);
//        employees.add(HR);
//        Employee TM = new Employee("TM", 1);
//        employees.add(TM);
    }

    public ArrayList<Shift> loadShifts() throws SQLException {
        ArrayList<Shift> shifts = shiftDAO.getAll();
        return shifts;
        //buildWeek();
    }

    public ArrayList<Integer> loadBranches() throws SQLException {
        ArrayList<Integer> branches = branchDAO.getAll();
        if (branches.isEmpty()){
            branchDAO.addT(0);
            branches.add(0);
        }
        return branches;
    }

    public ArrayList<String> loadRoles() throws SQLException {
        ArrayList<String> roles = roleDAO.getAll();
        if (roles.isEmpty()){
            String[] _roles = {"shiftManager", "cashier", "storekeeper", "driver_B", "driver_C1", "driver_C"};
            int i = 0;
            for (String r : _roles){
                roles.add(r);
                r = i + r;
                roleDAO.addT(r);
                i ++;
            }
        }
        return roles;
    }

    public ArrayList<Transport> loadTransports() throws SQLException {
        ArrayList<Transport> transports = transportDAO.getAll();
        return transports;
    }




//    public void add_20_Employees(String role, int id){
//        String name = "e_";
//        for (int i = 0; i<20; i++){
//            Employee e = new Employee(0, id + i , name+String.valueOf(i)+"_"+role, "1234");
//            e.getRoles().add(role);
//            employees.add(e);
//        }
//    }
//
//    public static void buildWeek(){
//        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
//        for (int i = 0; i<14; i++){
//            Shift shift = new Shift(i+1, 0, sunday.plusDays(i/2), i,i >= 11);
//            shift.setCurrentWeek(true);
//            if (!shift.isHoliday()){
//                for (String role : Role.getInstance().getRoles()){
//                    String startTime = shift.getStart().toString();
//                    String endTime = shift.getEnd().toString();
//                    String[] employee = {"e_"+String.valueOf(i)+"_"+role, role, startTime, endTime};
//                    shift.getEmployees().add(employee);
//                    shift.setCurrentWeek(true);
//                }
//            }
//            shifts.add(shift);
//        }
//
//        LocalDate newSunday = sunday.plusDays(7);
//        int sid = 15;
//        for (int i = 0; i<14; i++) {
//            Shift shift = new Shift(i + 1, 0, sunday.plusDays(i / 2), i, i >= 11);
//            shift.setNextWeek(true);
//            shifts.add(shift);
//        }
    }

