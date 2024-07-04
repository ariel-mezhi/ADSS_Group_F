package DomainLayer;

import DataAccessLayer.BranchDAO;
import DataAccessLayer.EmployeeDAO;
import DataAccessLayer.ShiftDAO;
import DataAccessLayer.TransportDAO;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
public class ShiftController {

    private static ShiftController instance;
    private ArrayList<Branch> branches;
    private ArrayList<Shift> shifts;
    private EmployeeController employeeController;
    private TransportController transportController;
    private InitSystem initSystem;
    private final ShiftDAO shiftDAO;

    private ShiftController() throws SQLException {
        this.initSystem = InitSystem.getInstance();
        this.employeeController = EmployeeController.getInstance();
        this.transportController = TransportController.getInstance();
        this.shifts = initSystem.loadShifts();
        this.branches = new ArrayList<>();
        this.shiftDAO = new ShiftDAO();
        
        loadTransportsToShifts();
        loadNextWeeks();
    }


    private void loadNextWeeks() throws SQLException {
        for (int i = 0; i<employeeController.getBranches().size(); i++){
            int BID = i;

            Shift[] currentWeek = getCurrentWeekByBID(BID);
            Shift[] nextWeek = getNextWeekByBID(BID);
            if (currentWeek == null){
                currentWeek = firstInitCurrent(BID);
                for (Shift s : currentWeek){
                    shifts.add(s);
                    shiftDAO.addT(s);
                }
            }
            if (nextWeek == null){
                nextWeek = firstInitNext(currentWeek[13]);
                for (Shift s : nextWeek){
                    shifts.add(s);
                    shiftDAO.addT(s);
                }
            }

            BranchDAO branchDAO = employeeController.branchDAO;
            boolean changes_allowed = branchDAO.getChangesAllowed(BID);
            boolean is_published = branchDAO.getIsPublished(BID);
            Branch b = new Branch(BID, currentWeek, nextWeek);
            b.setPublished(is_published);
            b.setAvailabilityChangesAllowed(changes_allowed);

            branches.add(BID, b);
        }
    }


    private Shift[] getCurrentWeekByBID(int BID){
        Shift[] week = new Shift[14];
        for (int i=0;i<14;i++){
            week[i] = findCurrentWeekShift(BID, i);
            if (week[i] == null){
                return null;
            }
        }
        return week;
    }

    private Shift[] getNextWeekByBID(int BID){
        Shift[] week = new Shift[14];
        for (int i=0;i<14;i++){
            week[i] = findNextWeekShift(BID, i);
            if (week[i] == null){
                return null;
            }
        }
        return week;
    }

    private Shift[] firstInitCurrent(int BID) throws SQLException {
        Shift[] currentWeek = new Shift[14];
        int SID = BID * 10000 + 1;
        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        for (int i = 0; i<14; i++){
            Shift shift = new Shift(SID + i, BID, sunday.plusDays(i/2), i,i >= 11);
            shift.setCurrentWeek(true);
            currentWeek[i] = shift;
        }
        return currentWeek;
    }

    private Shift[] firstInitNext(Shift lastShift) throws SQLException {
        Shift[] nextWeek = new Shift[14];
        int SID = lastShift.getShiftID() + 1;
        int BID = lastShift.getBranchID();
        LocalDate sunday = lastShift.getDate().plusDays(1);
        for (int i = 0; i<14; i++){
            Shift shift = new Shift(SID + i, BID, sunday.plusDays(i/2), i,i >= 11);
            shift.setNextWeek(true);
            nextWeek[i] = shift;
        }
        return nextWeek;
    }





    public static ShiftController getInstance() throws SQLException {
        if (instance == null)
            instance = new ShiftController();
        return instance;
    }

    public void pickAvailability(String userName, String requiredShifts) throws SQLException {
        Employee e = employeeController.getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user not found");
        }
        if (!branches.get(e.getBranchID()).isAvailabilityChangesAllowed()){
            throw new RuntimeException("changes in availability are not allowed");
        }
        String[] picks = requiredShifts.trim().split("\\s+");
        int[] _picks = new int[picks.length];
        for (int i = 0; i<picks.length; i++){
            int shift = convertStringToInt(picks[i]);
            if (shift < 0 || shift > 13){
                throw new RuntimeException("invalid shift chosen! choose 0-13");
            }
            _picks[i] = shift;
        }
        Branch branch = branches.get(e.getBranchID());
        for (int i : _picks){
            branch.addAvailableEmployee(e, i);
            Shift s = branch.getNewShifts()[i];
            shiftDAO.updateT(s);
        }
    }

    public String showMyAvailability(String userName){
        Employee e = employeeController.getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user not found");
        }
        Branch branch = branches.get(e.getBranchID());
        return branch.getEmployeeAvailability(e);
    }

    public void changeAvailability(String userName, String dayAndType) throws SQLException {
        int shift = convertStringToInt(dayAndType);
        if (shift < 0 || shift > 13){
            throw new RuntimeException("invalid shift chosen! choose 0-13");
        }
        Employee e = employeeController.getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user not found");
        }
        if (!branches.get(e.getBranchID()).isAvailabilityChangesAllowed()){
            throw new RuntimeException("changes in availability are not allowed");
        }
        Branch branch = branches.get(e.getBranchID());
        if (branch.isAvailableEmployee(e, shift)){
            branch.removeAvailableEmployee(e, shift);
        }else{
            branch.addAvailableEmployee(e, shift);
        }
        Shift s = branch.getNewShifts()[shift];
        shiftDAO.updateT(s);
    }


    public String showCurrentWeek(String name){
        Employee e = employeeController.getEmployee(name);
        if (e==null){
            throw new RuntimeException("illegal user name");
        }
        int bid = e.getBranchID();
        Branch branch = branches.get(bid);
        return branch.showCurrentWeek();
    }

    public String showNextWeek(String name){
        Employee e = employeeController.getEmployee(name);
        if (e==null){
            throw new RuntimeException("illegal user name");
        }
        int bid = e.getBranchID();
        Branch branch = branches.get(bid);
        if (!branch.isPublished()){
            return "not published yet";
        }
        return branch.showNextWeek();
    }

    public String HR_showCurrentWeek(String BID){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        return branch.showCurrentWeek();
    }

    public String HR_showNextWeek(String BID){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        return branch.showNextWeek();
    }



    public String showMyHistory(String userName){
        String st = "";
        for(Shift shift : shifts){
            if (shift.isWorked(userName)){
                String[] myArr = shift.getEmployeeArray(userName);
                String start = "start: " + myArr[2];
                String end = "end: " + myArr[3];
                String date = "date: " + shift.getDate().format(DateTimeFormatter.ofPattern("DD/MM/YYYY"));
                String type = "shift type: " + shift.getShiftType();
                String space = " ... ";
                st = st + date + space + type + space + start + space + end + "\n";
            }
        }
        return st;
    }

    public void reportHours(String userName, String SID, String startTime, String endTime) throws SQLException {
        LocalTime start = convertStringToTime(startTime);   //check if legal time
        LocalTime end = convertStringToTime(endTime);       //check if legal time
        Shift toReport = findShift(SID);
        if (toReport == null){
            throw new RuntimeException("there isn't shift with that SID : " + SID);
        }
        String[] employeeArray = toReport.getEmployeeArray(userName);
        if (employeeArray == null){
            throw new RuntimeException("you didn't work on this shift, can't report hours");
        }
        employeeArray[2] = startTime;
        employeeArray[3] = endTime;
        shiftDAO.updateT(toReport);
    }

    // HR
    public String showHistory(){
        String st = "";
        for(Shift shift : shifts){
             st += shift.toString();
        }
        return st;
    }

    public String showHistoryByBranch(String BID){
        String st = "";
        for(Shift shift : shifts){
            int bid = convertStringToInt(BID);
            if (shift.getBranchID() == bid){
                st += shift.toString();
            }
        }
        return st;
    }

    public String allowAvailabilityChanges(String BID) throws SQLException {
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        branch.setAvailabilityChangesAllowed(true);
        employeeController.branchDAO.updateChangesAllow(bid, true);
        return "Availability changes are now allowed";
    }

    public String disableAvailabilityChanges(String BID) throws SQLException {
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        branch.setAvailabilityChangesAllowed(false);
        employeeController.branchDAO.updateChangesAllow(bid, false);
        return "Availability changes are no longer allowed";
    }

    public String setHoliday(String BID, String dayAndType) throws SQLException {
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        branch.setAvailabilityChangesAllowed(true);
        int shiftNum = convertStringToInt(dayAndType);
        if (shiftNum < 0 || shiftNum > 13){
            throw new RuntimeException("illegal shift num. pick 0-13");
        }
        branch.setHoliday(shiftNum);
        Shift s = branch.getNewShifts()[shiftNum];
        shiftDAO.updateT(s);
        return "Set as holiday";
    }

    public String changeRequirement(String BID, String dayAndType, String role, String amountOfEmployees) throws SQLException {
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        branch.setAvailabilityChangesAllowed(true);
        int shiftNum = convertStringToInt(dayAndType);
        if (shiftNum < 0 || shiftNum > 13){
            throw new RuntimeException("illegal shift num. pick 0-13");
        }
        int newAmount = convertStringToInt(amountOfEmployees);
        if (newAmount < 0){
            throw new RuntimeException("must be positive number");
        }
        if (!Role.getInstance().containsRole(role)){
            throw new RuntimeException("illegal role");
        }
        branch.changeRequirement(shiftNum, role, newAmount);
        Shift s = branch.getNewShifts()[shiftNum];
        shiftDAO.updateT(s);
        return "the new requirement for the role " + role + ": " + s.getRequirements().get(role);
    }

    public String changeTime(String BID, String dayAndType, String start, String end) throws SQLException {
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        branch.setAvailabilityChangesAllowed(true);
        int shiftNum = convertStringToInt(dayAndType);
        if (shiftNum < 0 || shiftNum > 13){
            throw new RuntimeException("illegal shift num. pick 0-13");
        }
        LocalTime _start = convertStringToTime(start);
        LocalTime _end = convertStringToTime(end);
        branch.getNewShifts()[shiftNum].setStart(_start);
        branch.getNewShifts()[shiftNum].setEnd(_end);
        Shift s = branch.getNewShifts()[shiftNum];
        shiftDAO.updateT(s);
        return s.toString();
    }

    public String showAvailableEmployees(String BID, String dayAndType, String role) throws SQLException {
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        branch.setAvailabilityChangesAllowed(true);
        int shiftNum = convertStringToInt(dayAndType);
        if (shiftNum < 0 || shiftNum > 13){
            throw new RuntimeException("illegal shift num. pick 0-13");
        }
        if (!Role.getInstance().containsRole(role)){
            throw new RuntimeException("illegal role");
        }
        ArrayList<Employee> employees = branch.getNewShifts()[shiftNum].getAvailableByRole(role);
        String names = "";
        if (employees == null){
            return "no one is available";
        }
        for (Employee e : employees){
            names = names + e.getUserName() + " ";
        }
        return names;
    }

    public String getMissingRequirementsForShift(String BID, String shiftSign){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        int _shiftSign = convertStringToInt(shiftSign);
        if (_shiftSign < 0 || _shiftSign > 13){
            throw new RuntimeException("illegal shift num. pick 0-13");
        }
        Shift shift = branch.getNewShifts()[_shiftSign];
        StringBuilder st = new StringBuilder();
        shift.getRequirements().forEach((role, num) -> {
            try {
                st.append("*role: " + role).append(" missing: ").append(num).append("     available:     " + showAvailableEmployees(BID, shiftSign, role) + "\n");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return st.toString();
    }

    public String pickEmployee(String BID, String dayAndType, String role, String employeeName, String TID) throws SQLException {
        Employee e = employeeController.getEmployee(employeeName);
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        Branch branch = branches.get(bid);
        branch.setAvailabilityChangesAllowed(false);
        int shiftNum = convertStringToInt(dayAndType);
        if (shiftNum < 0 || shiftNum > 13){
            throw new RuntimeException("illegal shift num. pick 0-13");
        }
        if (!Role.getInstance().containsRole(role)){
            throw new RuntimeException("illegal role");
        }
        if (!e.getRoles().contains(role)){
            throw new RuntimeException("employee can't work at this role");
        }
        Shift shift = findNextWeekShift(bid, shiftNum);
        if (!shift.isAvailable(e)){
            throw new RuntimeException("employee can't work at this shift !!");
        }
        int tid = !TID.isEmpty() ? convertStringToInt(TID) : -1;
        branch.pickEmployee(shiftNum, e, role, tid);
        if (!role.contains("driver")) {
            branch.removeAvailableEmployee(e, shiftNum);
        }
        Shift s = branch.getNewShifts()[shiftNum];
        shiftDAO.updateT(s);
        return s.toString();
    }

    public String publishNextWeek(String BID) throws SQLException {
        Branch byBID = findNextWeekByBID(convertStringToInt(BID));
        if (byBID == null){
            throw new RuntimeException("wrong BID");
        }
        byBID.publishNextWeek();
        employeeController.branchDAO.updateIsPublished(byBID.getBID(), true);
        return "Next week for branch " + BID + " was published!";
    }

    public String setAsCurrentWeek(String BID) throws SQLException {
        Branch byBID = findNextWeekByBID(convertStringToInt(BID));
        if (byBID == null){
            throw new RuntimeException("wrong BID");
        }
        for (Shift s : byBID.getCurrentWeek()){
            s.setCurrentWeek(false);
            shiftDAO.updateT(s);
        }
        byBID.SetAsCurrentWeek();
        for (Shift shift : byBID.getCurrentWeek()){
            shiftDAO.updateT(shift);
        }
        for (Shift shift : byBID.getNewShifts()){
            shifts.add(shift);
            shiftDAO.addT(shift);
        }
        return "Set as current week";
    }



    private Shift findNextWeekShift(int bid, int shiftNum){
        for (Shift shift : shifts){
            if(shift.isNextWeek() && shift.getSign() == shiftNum && shift.getBranchID() == bid){
                return shift;
            }
        }
        return null;
    }



    private Shift findCurrentWeekShift(int bid, int shiftNum){
        for (Shift shift : shifts){
            if(shift.isCurrentWeek() && shift.getSign() == shiftNum && shift.getBranchID() == bid){
                return shift;
            }
        }
        return null;
    }




    private int convertStringToInt(String st){
        int s;
        try{
            s = Integer.parseInt(st);
        }catch (RuntimeException e){
            throw new RuntimeException("invalid value, you must provide a number");
        }
        return s;
    }

    private LocalTime convertStringToTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime t = LocalTime.parse(time, formatter);
            return t;
        } catch (Exception e) {
            throw new RuntimeException("invalid time format");
        }
    }

    private Shift findShift(String SID){
        int sid = convertStringToInt(SID);
        for (Shift shift : shifts){
            if (shift.getShiftID() == sid){
                return shift;
            }
        }
        return null;
    }


    private Branch findNextWeekByBID(int BID){
        for (Branch branch : branches){
            if (branch.getBID() == BID){
                return branch;
            }
        }
        return null;
    }


    private Branch getNextWeekByBranch(int BID){
        for (Branch w : branches){
            if (w.getBID() == BID){
                return w;
            }
        }
        return null;
    }





    //----TRANSPORTS----//

    private void loadTransportsToShifts(){
        for (Shift s : shifts){
            int BID = s.getBranchID();
            LocalDate date = s.getDate();
            int shiftSign = s.getSign();

            ArrayList<Transport> _transports = transportController.getTransportsByShift(BID, date, shiftSign);

            for (Transport t : _transports){
                s.addTransport(t);
            }
        }
    }

    //edit later if causes problems

    public Transport addTransport(String _BID, String _shiftSign, String _weight) throws SQLException {
        int BID = convertStringToInt(_BID);
        int shiftSign = convertStringToInt(_shiftSign);
        int weight = convertStringToInt(_weight);

        Branch b = getNextWeekByBranch(BID);
        if (b == null) throw new RuntimeException("ERROR ERROR");
        if (shiftSign > 13 || shiftSign < 0) throw new RuntimeException("illegal shiftSign");
        Shift s = b.getNewShifts()[shiftSign];
        int TID = transportController.getTransports().size();
        Transport t = new Transport(TID, BID, s.getDate(), weight, shiftSign);
        transportController.addTransport(t);
        s.addTransport(t);
        shiftDAO.updateT(s);
        return t;
    }

    public String showUnassignedTransports(String BID,  String shiftSign){
        int bid = convertStringToInt(BID);
        if (!employeeController.getBranches().contains(bid)){
            throw new RuntimeException("Invalid Branch ID");
        }
        int sign = convertStringToInt(shiftSign);
        if (sign > 13 || sign < 0)
            throw new RuntimeException("illegal shiftSign");

        return getNextWeekByBranch(bid).showUnassignedTransports(sign);
    }

    public String showTransportHistory(){
        String st = "";
        for (Transport t : transportController.getTransports()){
            st = st + t.toString();
        }
        return st;
    }

    public String showTransportHistoryByBranch(String BID){
        int bid = convertStringToInt(BID);
        if (!employeeController.getBranches().contains(bid)){
            throw new RuntimeException("Invalid Branch ID");
        }
        String st = "";
        for (Transport t : transportController.getTransportsByBID(bid)){
            st = st + t.toString();
        }
        return st;
    }

    public String createNewRole(String role) throws SQLException {
        employeeController.createNewRole(role);
        for (Shift s : shifts){
            s.getRequirements().put(role, 1);
            shiftDAO.updateT(s);
        }
        return "The current existing roles are: " + Role.getInstance().toString();
    }



    public String addBranch() throws SQLException {
        int BID = employeeController.getBranches().size();
        employeeController.addBranch();
        Shift[] currentWeek = firstInitCurrent(BID);
        Shift[] nextWeek = firstInitNext(currentWeek[13]);
        for (Shift s : currentWeek){
            shifts.add(s);
            shiftDAO.addT(s);
        }
        for (Shift s : nextWeek){
            shifts.add(s);
            shiftDAO.addT(s);
        }
        Branch b = new Branch(BID, currentWeek, nextWeek);
        branches.add(BID, b);
        return "The new branch ID is: " + BID;
    }

}
