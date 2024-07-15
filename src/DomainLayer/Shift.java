package DomainLayer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Shift {
    private int branchID;
    private LocalDate date;
    private ArrayList<String[]> employees;
    public String shiftType;
    private int shiftID;
    private int sign; // (0-13)
    private boolean isCurrentWeek;
    private boolean isNextWeek;
    private boolean isHoliday;
    private LocalTime start;
    private LocalTime end;
    private ArrayList<Employee> availableEmployees;
    private HashMap<String, Integer> requirements;
    private ArrayList<Transport> transports;

    // Constructor
    public Shift(int SID, int BID, LocalDate date, int sign, boolean isHoliday) throws SQLException {
        this.shiftID = SID;
        this.branchID = BID;
        this.date = date;
        this.isHoliday = isHoliday;
        this.sign = sign;
        this.shiftType = sign % 2 == 0?  "morning" : "evening";
        this.start = convertStringToTime(this.shiftType.equals("morning") ? ("08:00") : ("15:00"));
        this.end = convertStringToTime(this.shiftType.equals("morning") ? ("15:00") : ("22:00"));
        this.employees = new ArrayList<>();
        this.availableEmployees = new ArrayList<>();
        this.requirements = new HashMap<>();
        if (isHoliday){
            for (String role : Role.getInstance().getRoles()){
                requirements.put(role, 0);
            }
        }
        else{
            for (String role : Role.getInstance().getRoles()){
                if (role.equals("driver_B") || role.equals("driver_C1") || role.equals("driver_C") || role.equals("storekeeper")){
                    requirements.put(role, 0);
                }
                else {
                    requirements.put(role, 1);
                }
            }
            this.transports = new ArrayList<>();
        }
    }

    public boolean isStorekeeperWorking(){
        for (String[] st : employees){
            if (st.length > 1){
                if (st[1].equals("storekeeper")){
                    return true;
                }
            }
        }
        return false;
    }

    public void changeReq(String role, int num){
        if (isHoliday){
            throw new RuntimeException("can't change requirements in holiday !!");
        }
        if (role.equals("storekeeper") && num == 0 && !transports.isEmpty() && !isStorekeeperWorking()){
            for (Transport t : transports){
                if (!t.isConfirmed()){
                    throw new RuntimeException("u cant");
                }
            }
        }
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
        if (isHoliday){
            return true;
        }
        for (String role : requirements.keySet()){
            if (chosenForRole(role) < requirements.get(role)){
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


    public LocalDate getDate() {
        return date;
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

    public void setHoliday(boolean holiday) throws SQLException {
        if (isHoliday){
            for (String role : Role.getInstance().getRoles()){
                requirements.put(role, 0);
            }
        }
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

    public void setRequirements(HashMap<String, Integer> requirements) {
        this.requirements = requirements;
    }
    public HashMap<String, Integer> getRequirements(){
        return requirements;
    }

    public void setAvailableEmployees(ArrayList<Employee> availableEmployees) {
        this.availableEmployees = availableEmployees;
    }

    public void setEmployees(ArrayList<String[]> employees) {
        this.employees = employees;
    }

    // --- TRANSPORTS ---//
    public ArrayList<Transport> getTransports() {
        return transports;
    }

    public void addTransport(Transport transport){
        transports.add(transport);
        int max = maxWeight();
        requirements.put("driver_B", 0);
        requirements.put("driver_C1", 0);
        requirements.put("driver_C", 0);

        if (!isStorekeeperWorking() && requirements.get("storekeeper") == 0) {
            requirements.put("storekeeper", 1);
        }
        if (max < 3500){
            requirements.put("driver_B", 1);
        } else if ( max < 12000) {
            requirements.put("driver_C1", 1);
        }
        else{
            requirements.put("driver_C", 1);
        }
    }

    public String showUnassignedTransports(){
        String st = "";
        for (Transport t : transports){
            if (!t.isConfirmed()){
                st = st + "Transport ID: " + t.getTID() +", Weight: " +t.getWeight() + ", required license: " + t.getRequiredLicense() +"\n";
            }
        }
        return st;
    }

    public int maxWeight(){
        int maxWeight = 0;
        for (Transport t : transports){
            if (t.getWeight() > maxWeight) {
                maxWeight = t.getWeight();
            }
        }
        return maxWeight;
    }


    public String toString(){
        String sid = "SID: " + shiftID + "\n";
        String day = "day: " + date.getDayOfWeek().toString() + " " + shiftType + "\n";
        String workers = "";
        for (String[] worker : employees) {
            if (worker.length > 1) {
                workers = workers + "name: " + worker[0] + "  role: " + worker[1] +
                        "  startTime: " + worker[2] + "  endTime: " + worker[3] + "\n";
            }
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

    public String getShiftType() {
        return shiftType;
    }


}
