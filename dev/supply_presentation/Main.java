package supply_presentation;
import supply_domain.Supply;

import java.util.Calendar;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        boolean configured = false;
        boolean system_on = true;
        Scanner scanner = new Scanner(System.in);
        int user_input;
        String user_name;
        String password;
        Calendar calendar = Calendar.getInstance();
        Supply supply = new Supply(calendar.getTime(),calendar);
        System_user system_user = new System_user(supply);
        while(system_on) {
            System.out.print("Hello, welcome to supply system, please identify by entering number of the option:\n");
            System.out.print("1.Manager\n");
            System.out.print("2.Worker\n");
            System.out.print("3.Storekeeper\n");
            System.out.print("4.load data of system\n"); //not for ordinary users,required option from assignment
            System.out.print("5.Exit system\n");
            user_input = scanner.nextInt();
            switch (user_input) {
                case 1 -> {
                    System.out.print("Enter username of manager:");
                    user_name = scanner.next();
                    scanner.nextLine();
                    System.out.print("Enter password of manager:");
                    password = scanner.next();
                    scanner.nextLine();
                    if(password.equals("123456") && user_name.equals("admin1"))
                        system_user.print_menu_manager();
                    else
                        System.out.print("Credentials are incorrect, please try again\n");
                }
                case 2 -> {
                    System.out.print("Enter username of worker:");
                    user_name = scanner.next();
                    scanner.nextLine();
                    System.out.print("Enter password of worker:");
                    password = scanner.next();
                    scanner.nextLine();
                    if(password.equals("111111") && user_name.equals("supplyworker"))
                        system_user.print_menu_worker();
                    else
                        System.out.print("Credentials are incorrect, please try again\n");
                }
                case 3 -> {
                    System.out.print("Enter username of storekeeper:");
                    user_name = scanner.next();
                    scanner.nextLine();
                    System.out.print("Enter password of storekeeper:");
                    password = scanner.next();
                    scanner.nextLine();
                    if(password.equals("222222") && user_name.equals("storekeeper"))
                        system_user.print_menu_storekeeper();
                    else
                        System.out.print("Credentials are incorrect, please try again\n");
                }
                case 4 ->{
                    if(!configured)
                        configuration.configure(supply);
                }
                case 5 ->{
                    system_on = false;
                }
                default -> {
                    System.out.print("enter correct value from given options\n");
                }
            }
        }
    }
}
