package PresentationLayer;
import DomainLayer.Config;
import DomainLayer.Employee;
import DomainLayer.Shift;
import ServiceLayer.*;
import java.util.Scanner;


public class Main {
    public static Service service = new Service();

    public static void main(String[] args){
        boolean system_on = true;
        Scanner scanner = new Scanner(System.in);
        int user_input;
        String user_name;
        String password;


        System_User system_user = new System_User();
        System_HR system_hr = new System_HR();
        system_user.service = service;
        system_hr.service = service;


        while(system_on) {
            System.out.print("Hello, please identify yourself:\n");
            System.out.print("1.Employee\n");
            System.out.print("2.HR\n");
            System.out.print("3.Exit system\n");
            System.out.print("4.use config\n");
            user_input = scanner.nextInt();
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
                case 3 ->{
                    system_on = false;
                }
                case 4->{
                    Config c = new Config();
                    c.use();
                    service.useConfig(c);
                }
                default -> {
                    System.out.print("enter correct value from given options\n");
                }
            }
        }
    }
}