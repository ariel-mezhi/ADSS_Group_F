package DomainLayer;
import DataAccessLayer.TransportDAO;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


public class Branch {
    private int BID;
    private boolean availabilityChangesAllowed;
    private Shift[] newShifts;
    private Shift[] currentWeek;
    private boolean isPublished;


    public Branch(int _BID, Shift[] _currentWeek, Shift[] _nextWeek)
    {
        this.BID = _BID;
        this.availabilityChangesAllowed = true;
        this.isPublished = false;
        this.currentWeek = new Shift[14];
        this.newShifts = new Shift[14];
        for (int i = 0; i<14; i++){
            this.currentWeek[i] = _currentWeek[i];
            this.newShifts[i] = _nextWeek[i];
        }
    }


    public boolean isAvailableEmployee(Employee e, int shiftNum){
        Shift shift = newShifts[shiftNum];
        return shift.isAvailable(e);
    }
    public void removeAvailableEmployee(Employee e, int shiftNum){
        Shift shift = newShifts[shiftNum];
        if (shift.isHoliday()){
            throw new RuntimeException("it's holiday");
        }
        shift.removeAvailableEmployee(e);
    }
    public void addAvailableEmployee(Employee e, int shiftNum){
        Shift shift = newShifts[shiftNum];
        if (!shift.isHoliday()){
            shift.addAvailableEmployee(e);
        }

    }
    public void pickEmployee(int shiftNum, Employee e, String role, int tid) throws SQLException {
        if (availabilityChangesAllowed){
            throw new RuntimeException("you need to disable changes in requirements first");
        }
        Shift shift = newShifts[shiftNum];
        if (tid != -1){
            Transport transportToAssign = null;
            for (Transport t : shift.getTransports()){
                if (t.getTID() == tid){
                    transportToAssign = t;
                }
            }
            if (transportToAssign == null){
                throw new RuntimeException("Transport ID doesnt exist");
            }
            if (!transportToAssign.getDriver().equals("")){
                throw new RuntimeException("Driver already assigned to this transport");
            }
            transportToAssign.assignDriver(e);
            boolean isAllConfirmed = true;
            for (Transport t : shift.getTransports()){
                if (!t.isConfirmed()){
                    isAllConfirmed = false;
                }
            }
            if (isAllConfirmed){
                for (String r : Role.getInstance().getRoles()){
                    if (r.contains("driver") || r.contains("storekeeper")){
                        shift.changeReq(r, 0);
                    }
                }
            }
            TransportDAO transportDAO = new TransportDAO();
            transportDAO.updateT(transportToAssign);
        }
        else{
            shift.changeReq(role, Math.max(0, shift.getRequirements().get(role) - 1));
        }
        if (!shift.isWorked(e.getUserName())){
            String[] employee = {e.getUserName(), role, shift.getStart().toString(), shift.getEnd().toString()};
            shift.getEmployees().add(employee);
        }
    }


    public String getEmployeeAvailability(Employee e){
        String st = "";
        for (Shift shift : newShifts){
            if (shift.isAvailable(e)){
                st = st + "day: " + shift.getDate().getDayOfWeek() + "  type: " + shift.getShiftType() +
                        " shift sign: " + String.valueOf(shift.getSign()) + "\n";
            }
        }
        return st;
    }

    public void setHoliday(int shiftNum) throws SQLException {
        newShifts[shiftNum].setHoliday(true);
    }

    public void changeRequirement(int shiftNum, String role, int amountOfEmployees){
        newShifts[shiftNum].changeReq(role, amountOfEmployees);
    }

    public String showCurrentWeek(){
        String shiftToView = "";
        for (Shift shift : currentWeek){
            shiftToView = shiftToView + shift.toString() + "\n";
        }
        return shiftToView;
    }

    public String showNextWeek(){
        String shiftToView = "";
        for (Shift shift : newShifts){
            shiftToView = shiftToView + shift.toString() + "\n";
        }
        return shiftToView;
    }

    public void publishNextWeek(){
        for (Shift shift : newShifts){
            if (!shift.isValid()){
                throw new RuntimeException(shift.getSign() + "not match to HR requirements");
            }
        }
        isPublished = true;
    }

    public void SetAsCurrentWeek() throws SQLException {
        if (!isPublished){
            throw new RuntimeException("you need to publish first");
        }

        for (int i = 0; i<14; i++){
            currentWeek[i] = newShifts[i];
            currentWeek[i].setCurrentWeek(true);
        }

        initNewWeekByCurrent(currentWeek[13]);

        isPublished = false;
        availabilityChangesAllowed = true;
    }



    public int getBID() {
        return BID;
    }


    public boolean isAvailabilityChangesAllowed() {
        return availabilityChangesAllowed;
    }

    public Shift[] getNewShifts() {
        return newShifts;
    }

    public Shift[] getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(Shift[] currentWeek) {
        this.currentWeek = currentWeek;
    }

    public void setAvailabilityChangesAllowed(boolean availabilityChangesAllowed) {
        this.availabilityChangesAllowed = availabilityChangesAllowed;
    }


    public void setNewShifts(Shift[] newShifts) {
        this.newShifts = newShifts;
    }


    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }


    private void initNewWeekByCurrent(Shift lastShift) throws SQLException {
        int SID = lastShift.getBranchID() + 1;
        LocalDate sunday = lastShift.getDate().plusDays(1);
        for (int i = 0; i<14; i++) {
            Shift shift = new Shift(i + SID, 0, sunday.plusDays(i), i, i >= 11);
            shift.setNextWeek(true);
        }
    }



    // --- TRANSPORTS --- //
    public String showUnassignedTransports(int shiftSign){
        return newShifts[shiftSign].showUnassignedTransports();
    }
}