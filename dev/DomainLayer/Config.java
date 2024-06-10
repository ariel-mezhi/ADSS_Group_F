package DomainLayer;
import ServiceLayer.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;

public class Config {
    public ArrayList<Employee> employees = new ArrayList<>();
    public ArrayList<Shift> shifts = new ArrayList<>();


    public void use(){

        employees = new ArrayList<>();
        for (String role : Role.getInstance().getRoles()){
            add_20_Employees(role);
        }

        shifts = new ArrayList<>();
        buildWeek();
    }

    public void add_20_Employees(String role){
        String name = "e_";
        for (int i = 0; i<20; i++){
            Employee e = new Employee(0, name+String.valueOf(i)+"_"+role, "1234");
            employees.add(e);
        }
    }

    public void buildWeek(){
        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        for (int i = 0; i<14; i++){
            Shift.shiftType _shiftType = (i % 2 == 0) ? Shift.shiftType.morning : Shift.shiftType.evening;
            Shift shift = new Shift(i+1, 0, sunday.plusDays(i/2), _shiftType,i >= 11);
            shift.setCurrentWeek(true);
            if (!shift.isHoliday()){
                for (String role : Role.getInstance().getRoles()){
                    String startTime = shift.getStart().toString();
                    String endTime = shift.getEnd().toString();
                    String[] employee = {"e_"+String.valueOf(i)+"_"+role, role, startTime, endTime};
                    shift.getEmployees().add(employee);
                    shift.setCurrentWeek(true);
                }
            }
            shifts.add(shift);
        }
    }

}
