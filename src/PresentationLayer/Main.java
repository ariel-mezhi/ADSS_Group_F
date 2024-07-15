package PresentationLayer;
import ServiceLayer.*;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Calendar;
import DomainLayer.*;

public class Main {
    public static Service service;


    public static void main(String[] args) throws SQLException {
        service = new Service();
        boolean system_on = true;
        Scanner scanner = new Scanner(System.in);
        int user_input;
        String user_name;
        String password;
        Calendar calendar = Calendar.getInstance();
        Supply supply = new Supply(calendar.getTime(),calendar);
        Jsoncontroller jc = new Jsoncontroller(supply);
        System_SM systemSm = new System_SM(jc);
        System_storeKeeper systemStoreKeeper = new System_storeKeeper(jc);
        System_User system_user = new System_User(systemStoreKeeper);
        System_HR system_hr = new System_HR(scanner);
        System_TM system_tm = new System_TM();
        system_user.service = service;
        system_user.scanner = scanner;
        systemStoreKeeper.scanner = scanner;
        system_hr.service = service;
        system_tm.service = service;



        while(system_on) {
            System.out.print("Hello, please identify yourself:\n");
            System.out.print("1.Employee\n");
            System.out.print("2.HR\n");
            System.out.print("3.TM\n");
            System.out.print("4.SM\n");
            System.out.print("5.Exit system\n");
            try{
                user_input = scanner.nextInt();
            }
            catch (Exception e) {
                user_input = 4;
            }
            switch (user_input) {
                case 1 -> {
                    System.out.print("Enter username:");
                    user_name = scanner.next();
                    scanner.nextLine();
                    System.out.print("Enter password:");
                    password = scanner.next();
                    scanner.nextLine();
                    Response response = service.login(user_name, password);
                    if(!response.isError){
                        system_user.userName = user_name;
                        response = service.isStoreKeeper(user_name);
                        system_user.isStoreKeeper = (response.isError && response.msg.equals(""));
                        system_user.printMenu();
                    }
                    else
                        System.out.println(response.msg);
                }

                case 2 -> {
                    System.out.print("Enter username:");
                    user_name = scanner.next();
                    scanner.nextLine();
                    System.out.print("Enter password:");
                    password = scanner.next();
                    scanner.nextLine();
                    Response response = service.login(user_name, password);
                    if(!response.isError){
                        system_hr.userName = user_name;
                        system_hr.printMenu();
                    }
                    else{
                        System.out.println(response.msg);
                    }
                }
                case 3 -> {
                    System.out.print("Enter username:");
                    user_name = scanner.next();
                    scanner.nextLine();
                    System.out.print("Enter password:");
                    password = scanner.next();
                    scanner.nextLine();
                    Response response = service.login(user_name, password);
                    if(!response.isError){
                        system_tm.userName = user_name;
                        system_tm.printMenu();
                    }
                    else{
                        System.out.println(response.msg);
                    }
                }
                case 4 ->{
                    System.out.print("Enter username:");
                    user_name = scanner.next();
                    scanner.nextLine();
                    System.out.print("Enter password:");
                    password = scanner.next();
                    scanner.nextLine();
                    if (!user_name.equals("SM")){
                        System.out.println("You are not SM");
                        break;
                    }
                    Response response = service.login(user_name, password);
                    if(!response.isError){
                        systemSm.userName = user_name;
                        systemSm.print_menu_manager();
                    }
                    else{
                        System.out.println(response.msg);
                    }
                }

                case 5 ->{
                    System.out.println("see you soon :P");
                    system_on = false;
                }

                default -> {
                    System.out.print("enter correct value from given options\n");
                }
            }
        }
    }
}