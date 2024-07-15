package PresentationLayer;

import ServiceLayer.*;
import java.sql.SQLException;
import java.util.Scanner;

public class System_User {
    public String in;
    public String userName;
    public String out;
    public boolean isStoreKeeper;
    public System_storeKeeper systemStoreKeeper;
    public Service service;
    public Scanner scanner;

    public System_User(System_storeKeeper systemStoreKeeper){
        this.systemStoreKeeper = systemStoreKeeper;
    }

    public void printMenu() throws SQLException {
        System.out.println("************************************************************");
        System.out.println("            Hello " + userName + ", please choose an option:");
        System.out.println();
        System.out.println("************************************************************");
        if(isStoreKeeper)
        System.out.println("    0. to supply menu");
        System.out.println("    1. change password");
        System.out.println("    2. pick availability");
        System.out.println("    3. show my availabilities");
        System.out.println("    4. change specific availability");
        System.out.println("    5. show current week");
        System.out.println("    6. show my shift history");
        System.out.println("    7. report hours");
        System.out.println("    8. show how many days left for contract");
        System.out.println("    9. show next week");
        System.out.println("    10. logout");
        System.out.println("************************************************************");

        getAnswer();
    }

    public void getAnswer() throws SQLException {
        int input;
        try {
            input = scanner.nextInt();
        }catch (Exception e){
            input = -1;
        }
        String a = scanner.nextLine();
        switch (input) {
            case 0 -> {
                if (!isStoreKeeper){
                    printMenu();
                }
                else{
                    systemStoreKeeper.print_menu_storekeeper();
                }
            }
            case 1 -> {
                System.out.println("please enter a new password");
                in = scanner.nextLine();
                Response r = service.changePassword(userName, in);
                out = (r.isError? r.toString() : "password was successfully changed");
                System.out.println(out);
                printMenu();
            }
            case 2 -> {
                System.out.println("please enter your availability by choosing numbers between 0-13, make sure" +
                        " to have a space between each number");
                System.out.println("Note that each number represents a shift, for example 0 is sunday morning shift" +
                        " ,1 is sunday evening shift, 2 is monday morning and so on\n");
                System.out.println("You can write 'all' instead of choosing every shift");
                in = scanner.nextLine();
                if (in.equals("all")){
                    in = "0 1 2 3 4 5 6 7 8 9 10";
                }
                Response r = service.pickAvailability(userName, in);
                if (!r.isError){
                    r = service.showMyAvailability(userName);
                }
                out = r.toString();
                System.out.println(out);
                printMenu();
            }

            case 3 -> {
                Response r = service.showMyAvailability(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 4 -> {
                System.out.println("please enter the shift you want to change (0-13)");
                in = scanner.nextLine();
                Response r = service.changeAvailability(userName, in);
                if (!r.isError){
                    r = service.showMyAvailability(userName);
                }
                out = r.toString();
                System.out.println(out);
                printMenu();
            }

            case 5 -> {
                Response r = service.showCurrentWeek(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 6 -> {
                Response r = service.showMyHistory(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 7 -> {
                System.out.println("please choose the shift ID of the desired shift from the list below: \n");
                Response r = service.showMyHistory(userName);
                System.out.println(r.toString());
                String s = scanner.nextLine();
                System.out.println("please enter start time in format HH:MM");
                String s1 = scanner.nextLine();
                System.out.println("please enter end time in format HH:MM");
                String s2 = scanner.nextLine();
                r = service.reportHours(userName, s, s1, s2);
                out = (r.isError? r.toString() : "hours reported");
                System.out.println(out);
                printMenu();
            }
            case 8 -> {
                Response r = service.daysLeftForContract(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 9 ->{
                Response r = service.showNextWeek(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 10 -> {
                System.out.println("BYE BYE " + userName +" , have a lovely day :D");
                Response r = service.logout(userName);
                System.out.println(r.toString());
            }

            default -> {
                System.out.println("invalid input, please try again");
                printMenu();
            }
        }
    }
}
