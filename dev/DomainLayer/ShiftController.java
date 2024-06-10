package DomainLayer;
import ServiceLayer.Response;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class ShiftController {

    private static ShiftController instance;
    private ArrayList<NextWeek> nextWeeks;
    private ArrayList<Shift> shifts;
    private EmployeeController employeeController;


    private ShiftController(){
        this.shifts = new ArrayList<>();
        this.nextWeeks = new ArrayList<>();
        this.employeeController = EmployeeController.getInstance();

        //loadShifts();
        initNextWeeksArray();
    }


    private void initNextWeeksArray(){
        for (int i = 0; i<employeeController.getBranches().size(); i++){
            Shift[] current = getCurrentWeek(i);
            if (current == null){
                current = firstInit(i);
            }
            Shift[] next = getNextWeek(i);
            nextWeeks.add(i, new NextWeek(i, current, next));
        }
    }

    private Shift[] firstInit(int BID){
        Shift[] currentWeek = new Shift[14];
        int SID = BID * 10000 + 1;
        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        for (int i = 0; i<14; i++){
            Shift.shiftType _shiftType = (i % 2 == 0) ? Shift.shiftType.morning : Shift.shiftType.evening;
            Shift shift = new Shift(SID + i, BID, sunday.plusDays(i/2), _shiftType,i >= 11);
            shift.setCurrentWeek(true);
            shift.setHoliday(true);
            currentWeek[i] = shift;
        }
        return currentWeek;
    }

    public static ShiftController getInstance(){
        if (instance == null)
            instance = new ShiftController();
        return instance;
    }

    public void pickAvailability(String userName, String requiredShifts){
        Employee e = employeeController.getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user not found");
        }
        if (!nextWeeks.get(e.getBranchID()).isAvailabilityChangesAllowed()){
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
        NextWeek nextWeek = nextWeeks.get(e.getBranchID());
        for (int i : _picks){
            nextWeek.addAvailableEmployee(e, i);
        }
    }

    public String showMyAvailability(String userName){
        Employee e = employeeController.getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user not found");
        }
        NextWeek nextWeek = nextWeeks.get(e.getBranchID());
        return nextWeek.getEmployeeAvailability(e);
    }

    public void changeAvailability(String userName, String dayAndType){
        int shift = convertStringToInt(dayAndType);
        if (shift < 0 || shift > 13){
            throw new RuntimeException("invalid shift chosen! choose 0-13");
        }
        Employee e = employeeController.getEmployee(userName);
        if (e == null){
            throw new RuntimeException("user not found");
        }
        if (!nextWeeks.get(e.getBranchID()).isAvailabilityChangesAllowed()){
            throw new RuntimeException("changes in availability are not allowed");
        }
        NextWeek nextWeek = nextWeeks.get(e.getBranchID());
        if (nextWeek.isAvailableEmployee(e, shift)){
            nextWeek.removeAvailableEmployee(e, shift);
        }else{
            nextWeek.addAvailableEmployee(e, shift);
        }
    }


    public String showCurrentWeek(String name){
        Employee e = employeeController.getEmployee(name);
        if (e==null){
            throw new RuntimeException("illegal user name");
        }
        int bid = e.getBranchID();
        NextWeek nextWeek = nextWeeks.get(bid);
        return nextWeek.showCurrentWeek();
    }

    public String showNextWeek(String name){
        Employee e = employeeController.getEmployee(name);
        if (e==null){
            throw new RuntimeException("illegal user name");
        }
        int bid = e.getBranchID();
        NextWeek nextWeek = nextWeeks.get(bid);
        return nextWeek.showNextWeek();
    }

    public String HR_showCurrentWeek(String BID){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        NextWeek nextWeek = nextWeeks.get(bid);
        return nextWeek.showCurrentWeek();
    }

    public String HR_showNextWeek(String BID){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        NextWeek nextWeek = nextWeeks.get(bid);
        return nextWeek.showNextWeek();
    }



    public String showMyHistory(String userName){
        String st = "";
        for(Shift shift : shifts){
            if (shift.isWorked(userName)){
                st = st + shift.getDate().toString() + " " + shift.getShiftType() + "\n";
            }
        }
        return st;
    }

    public void reportHours(String userName, String SID, String startTime, String endTime){
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

    public void allowAvailabilityChanges(String BID){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        NextWeek nextWeek = nextWeeks.get(bid);
        nextWeek.setAvailabilityChangesAllowed(true);
    }

    public void disableAvailabilityChanges(String BID){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        NextWeek nextWeek = nextWeeks.get(bid);
        nextWeek.setAvailabilityChangesAllowed(false);
    }

    public void setHoliday(String BID, String dayAndType){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        NextWeek nextWeek = nextWeeks.get(bid);
        nextWeek.setAvailabilityChangesAllowed(true);
        int shiftNum = convertStringToInt(dayAndType);
        if (shiftNum < 0 || shiftNum > 13){
            throw new RuntimeException("illegal shift num. pick 0-13");
        }
        nextWeek.setHoliday(shiftNum);
    }

    public void changeRequirement(String BID, String dayAndType, String role, String amountOfEmployees){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        NextWeek nextWeek = nextWeeks.get(bid);
        nextWeek.setAvailabilityChangesAllowed(true);
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
        nextWeek.changeRequirement(shiftNum, role, newAmount);
    }

    public void changeTime(String BID, String dayAndType, String start, String end){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        NextWeek nextWeek = nextWeeks.get(bid);
        nextWeek.setAvailabilityChangesAllowed(true);
        int shiftNum = convertStringToInt(dayAndType);
        if (shiftNum < 0 || shiftNum > 13){
            throw new RuntimeException("illegal shift num. pick 0-13");
        }
        LocalTime _start = convertStringToTime(start);
        LocalTime _end = convertStringToTime(end);
        nextWeek.getNewShifts()[shiftNum].setStart(_start);
        nextWeek.getNewShifts()[shiftNum].setEnd(_end);
    }

    public String showAvailableEmployees(String BID, String dayAndType, String role){
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        NextWeek nextWeek = nextWeeks.get(bid);
        nextWeek.setAvailabilityChangesAllowed(true);
        int shiftNum = convertStringToInt(dayAndType);
        if (shiftNum < 0 || shiftNum > 13){
            throw new RuntimeException("illegal shift num. pick 0-13");
        }
        if (!Role.getInstance().containsRole(role)){
            throw new RuntimeException("illegal role");
        }
        ArrayList<Employee> employees = nextWeek.getNewShifts()[shiftNum].getAvailableByRole(role);
        String names = "";
        if (employees == null){
            return "no one is available";
        }
        for (Employee e : employees){
            names = names + e.getUserName() + " ";
        }
        return names;
    }

    public void pickEmployee(String BID, String dayAndType, String role, String employeeName){
        Employee e = employeeController.getEmployee(employeeName);
        int bid = convertStringToInt(BID);
        if (bid >= employeeController.getBranches().size()){
            throw new RuntimeException("illegal branch id");
        }
        NextWeek nextWeek = nextWeeks.get(bid);
        nextWeek.setAvailabilityChangesAllowed(true);
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
        nextWeek.pickEmployee(shiftNum, e, role);
    }

    public void publishNextWeek(String BID){
        NextWeek byBID = findNextWeekByBID(convertStringToInt(BID));
        if (byBID == null){
            throw new RuntimeException("wrong BID");
        }
        byBID.publishNextWeek();
    }

    public void setAsCurrentWeek(String BID){
        NextWeek byBID = findNextWeekByBID(convertStringToInt(BID));
        if (byBID == null){
            throw new RuntimeException("wrong BID");
        }
        byBID.SetAsCurrentWeek();
    }








    private Shift[] getNextWeek(int BID){
        Shift[] week = new Shift[14];
        for (int i=0;i<14;i++){
            week[i] = findNextWeekShift(BID, i);
            if(week[i] == null){
                return null;
            }
        }
        return week;
    }

    private Shift findNextWeekShift(int bid, int shiftNum){
        for (Shift shift : shifts){
            if(shift.isNextWeek() && shift.getSign() == shiftNum && shift.getBranchID() == bid){
                return shift;
            }
        }
        return null;
    }

    private Shift[] getCurrentWeek(int BID){
        Shift[] week = new Shift[14];
        for (int i=0;i<14;i++){
            week[i] = findCurrentWeekShift(BID, i);
            if (week[i] == null){
                return null;
            }
        }
        return week;
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


    private NextWeek findNextWeekByBID(int BID){
        for (NextWeek nextWeek : nextWeeks){
            if (nextWeek.getBID() == BID){
                return nextWeek;
            }
        }
        return null;
    }

    public void addBranch(){
        ArrayList<Integer> branches = employeeController.getBranches();
        branches.add(branches.size(), branches.size());
        int BID = branches.size() - 1;
        nextWeeks.add(BID, new NextWeek(BID, firstInit(BID), null));
    }

    public void Config_setShifts(ArrayList<Shift> shifts){
        for (Shift s : shifts){
            this.shifts.add(s);
        }
        initNextWeeksArray();
    }
}
