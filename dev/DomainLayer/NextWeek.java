package DomainLayer;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


public class NextWeek {
    private int BID;
    private boolean availabilityChangesAllowed;
    private Shift[] newShifts;
    private Shift[] currentWeek;
    private boolean isPublished;



    public NextWeek(int _BID, Shift[] _currentWeek, Shift[] _nextWeek)
    {
        this.BID = _BID;
        initWeeks(_currentWeek, _nextWeek);
        this.availabilityChangesAllowed = true;
        this.newShifts = new Shift[14];
    }

    private void initWeeks(Shift[] current, Shift[] next){
        this.currentWeek = new Shift[14];
        this.newShifts = new Shift[14];
        for (int i = 0; i<14; i++){
            this.currentWeek[i] = current[i];
            if(next != null){
                this.newShifts[i] = next[i];
            }
        }
        if(next == null){
            initNewWeekByCurrent(current[13]);
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
        if (shift.isHoliday()){
            throw new RuntimeException("it's holiday");
        }
        shift.addAvailableEmployee(e);
    }
    public void pickEmployee(int shiftNum, Employee e, String role){
        if (availabilityChangesAllowed){
            throw new RuntimeException("you need to disable changes in requirements first");
        }
        Shift shift = newShifts[shiftNum];
        if (shift.chosenForRole(role) == shift.getRequirements().get(role)){
            throw new RuntimeException("there is enough employees to this role");
        }
        String[] employee = {e.getUserName(), role, shift.getStart().toString(), shift.getEnd().toString()};
        shift.getEmployees().add(employee);
    }

    public String getEmployeeAvailability(Employee e){
        String st = "";
        for (Shift shift : newShifts){
            if (shift.isAvailable(e)){
                st = st + "day: " + shift.getDate().getDayOfWeek() + "  type: " + shift.getShiftType() +
                        " shift sign: " + shift.getSign();
            }
        }
        return st;
    }

    public void setHoliday(int shiftNum){
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
        if (!isPublished){
            return "not published yet";
        }
        String shiftToView = "";
        for (Shift shift : newShifts){
            shiftToView = shiftToView + shift.toString() + "\n";
        }
        return shiftToView;
    }

    public void publishNextWeek(){
        for (Shift shift : newShifts){
            if (!shift.isValid()){
                throw new RuntimeException("not match to HR requirements");
            }
        }
        isPublished = true;
    }

    public void SetAsCurrentWeek(){
        if (!isPublished){
            throw new RuntimeException("you need to publish first");
        }

        for (int i = 0; i<14; i++){
            currentWeek[i].setCurrentWeek(false);
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

    private LocalDate getNearestSunday(LocalDate date) {
        DayOfWeek currentDay = date.getDayOfWeek();
        int daysUntilSunday = DayOfWeek.SUNDAY.getValue() - currentDay.getValue();

        if (daysUntilSunday < 0) {
            daysUntilSunday += 7; // Adjust to next Sunday if the value is negative
        }
        return date.plusDays(daysUntilSunday);
    }

    private void initNewWeekByCurrent(Shift lastShift){
        int SID = lastShift.getBranchID() + 1;
        LocalDate sunday = lastShift.getDate().plusDays(1);
        for (int i = 0; i<14; i++) {
            Shift.shiftType _shiftType = (i % 2 == 0) ? Shift.shiftType.morning : Shift.shiftType.evening;
            Shift shift = new Shift(i + SID, 0, sunday.plusDays(i), _shiftType, i >= 11);
            shift.setCurrentWeek(true);
        }
    }
}
