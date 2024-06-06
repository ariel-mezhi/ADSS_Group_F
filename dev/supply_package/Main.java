package supply_package;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        boolean system_on = true;
        Date date = new Date(2024, Calendar.JUNE,6);
        Date date1 = new Date(2024, Calendar.JUNE,16);
        Supply supply = new Supply(date);
        System_user system_user = new System_user(supply);
        supply.add_newItem(10,"tara","diary","milk","100ml",5,date,date1);
        supply.add_newItem(10,"tara","diary","milk","100ml",5,date,date1);
        supply.add_newItem(10,"tara","diary","milk","100ml",5,date,date1);
        supply.add_newItem(11,"tnova","diary","milk","150ml",5,date,date1);
        supply.add_newItem(13,"strauss","diary","milk","200ml",5,date,date1);
        supply.add_newItem(13,"strauss","diary","milk","200ml",5,date,date1);
        supply.add_newItem(13,"strauss","diary","milk","200ml",5,date,date1);
        supply.add_newItem(50,"neto","meat","beef","300g",5,date,date1);
        while(system_on) {
            System.out.print("Hello, welcome to supply system, please identify by entering number of the option:\n");
            System.out.print("1.Manager\n");
            System.out.print("2.Worker\n");
            System.out.print("3.Storekeeper\n");
            System.out.print("4.Exit system\n");
            Scanner scanner = new Scanner(System.in);
            int user_input = scanner.nextInt();
            switch (user_input) {
                case 1 -> {
                    system_user.print_menu_manager();
                }
                case 2 -> {
                    system_user.print_menu_worker();
                }
                case 3 -> {
                    system_user.print_menu_storekeeper();
                }
                case 4 ->{
                    system_on = false;
                }
                default -> {
                    System.out.print("enter correct value from given options\n");
                }
            }
        }
    }
}
