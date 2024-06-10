package DomainLayer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;


public class Shift {
    private int branchID;
    private LocalDate date;
    private ArrayList<String[]> employees;
    public enum shiftType {
        morning, evening;
    }
    private static shiftType shiftType;
    private int shiftID;
    private int sign; // (0-13)
    private boolean isCurrentWeek;
    private boolean isNextWeek;
    private boolean isHoliday;
    private LocalTime start;
    private LocalTime end;
    private ArrayList<Employee> availableEmployees;
    private Map<String, Integer> requirements;

    // Constructor
    public Shift(int SID, int BID, LocalDate date, shiftType _st, boolean isHoliday) {
        this.shiftID = SID;
        this.branchID = BID;
        this.date = date;
        this.shiftType = _st;
        this.isHoliday = isHoliday;
        this.start = convertStringToTime(_st.equals(shiftType.morning) ? ("08:00") : ("15:00"));
        this.end = convertStringToTime(_st.equals(shiftType.morning) ? ("15:00") : ("22:00"));
        this.employees = new ArrayList<>();
        setShiftNum();
        this.isNextWeek = SID < 0;
        this.availableEmployees = new ArrayList<>();
        this.requirements = new HashMap<>();
        for (String role : Role.getInstance().getRoles()){
            requirements.put(role, 1);
        }
    }

    public void changeReq(String role, int num){
        requirements.put(role, num);
    }

    public boolean isAvailable(Employee e){
        return availableEmployees.contains(e);
    }

    public void addAvailableEmployee(Employee e){
        if (!availableEmployees.contains(e)){
            availableEmployees.add(e);
        }
    }

    public void removeAvailableEmployee(Employee e){
        availableEmployees.remove(e);
    }

    public ArrayList<Employee> getAvailableByRole(String role){
        ArrayList<Employee> byRole = new ArrayList<>();
        for (Employee e : availableEmployees){
            if (e.getRoles().contains(role)){
                byRole.add(e);
            }
        }
        return byRole;
    }








    public int chosenForRole(String role){
        int count = 0;
        for (String[] e : employees){
            if(e[1].equals(role)){
                count++;
            }
        }
        return count;
    }

    public boolean isValid(){
        for (String role : requirements.keySet()){
            if (chosenForRole(role) != requirements.get(role) && !isHoliday){
                return false;
            }
        }
        return true;
    }

    public boolean isWorked(String userName){
        for(String[] employee : employees){
            if (employee[0].equals(userName)){
                return true;
            }
        }
        return false;
    }

    public String[] getEmployeeArray(String userName){
        for(String[] employee : employees){
            if (employee[0].equals(userName)){
                return employee;
            }
        }
        return null;
    }

    public ArrayList<Employee> getAvailableEmployees() {
        return availableEmployees;
    }

    public Map<String, Integer> getRequirements() {
        return requirements;
    }

    public LocalDate getDate() {
        return date;
    }

    public shiftType getShiftType() {
        return this.shiftType;
    }

    public int getShiftID() {
        return shiftID;
    }

    public int getBranchID() {
        return branchID;
    }

    public ArrayList<String[]> getEmployees(){
        return this.employees;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public boolean isCurrentWeek() {
        return isCurrentWeek;
    }

    public void setCurrentWeek(boolean currentWeek) {
        isCurrentWeek = currentWeek;
    }

    public void setNextWeek(boolean nextWeek) {
        isNextWeek = nextWeek;
    }

    public boolean isNextWeek() {
        return isNextWeek;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }


    public String toString(){
        if (this == null){
            return "closed";
        }
        String sid = "SID: " + shiftID + "\n";
        String day = "day: " + date.getDayOfWeek().toString() + " " + getShiftType().name() + "\n";
        String workers = "";
        for (String[] worker : employees){
            workers = workers + "name: " + worker[0] + "  role: " + worker[1] +
                    "  startTime: " + worker[2] + "  endTime: " + worker[3] + "\n";
        }
        return sid + day + workers;
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

    private void setShiftNum() {
        DayOfWeek dayOfWeek = this.date.getDayOfWeek();
        int shiftType = (this.shiftType.equals(Shift.shiftType.morning) ? 0 : 1 );
        int shiftNum = (dayOfWeek.getValue() - 1) * 2 + shiftType;
        this.sign = shiftNum;
    }
}
