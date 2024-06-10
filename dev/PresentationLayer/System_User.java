package PresentationLayer;
import ServiceLayer.*;
import java.util.Scanner;

public class System_User {
    public String in;
    public String userName;
    public Service service;

    public void printMenu(){
        System.out.println();
        System.out.println("choose an option:");
        System.out.println("1. change password");
        System.out.println("2. pick availability");
        System.out.println("3. show my availabilities");
        System.out.println("4. change availability");
        System.out.println("5. show current week");
        System.out.println("6. show my shift history");
        System.out.println("7. report hours");
        System.out.println("8. show how many days left for contract");
        System.out.println("9. logout");

        getAnswer();
    }

    public void getAnswer(){
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        scanner.nextLine();
        switch (input) {
            case 1 -> {
                System.out.println("please enter new password");
                in = scanner.nextLine();
                Response r = service.changePassword(userName, in);
                System.out.println(r.toString());
                printMenu();
            }
            case 2 -> {
                System.out.println("please enter your availability by choosing numbers between 0-13, make sure" +
                        " to have a space between each number");
                System.out.println("Note that each number represents a shift, for example 0 is sunday morning shift" +
                        " ,1 is sunday evening shift, 2 is monday morning and so on");
                in = scanner.nextLine();
                Response r = service.pickAvailability(userName, in);
                System.out.println(r.toString());
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
                System.out.println(r.toString());
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
                System.out.println("please choose SID of the wanted shift: ");
                Response r = service.showMyHistory(userName);
                System.out.println(r.toString());
                String s = scanner.nextLine();
                System.out.println("please enter start time in format HH:MM");
                String s1 = scanner.nextLine();
                System.out.println("please enter end time in format HH:MM");
                String s2 = scanner.nextLine();
                r = service.reportHours(userName, s, s1, s2);
                System.out.println(r.toString());
                printMenu();
            }
            case 8 -> {
                Response r = service.daysLeftForContract(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 9 -> {
                Response r = service.logout(userName);
                System.out.println(r.toString());
                return;
            }

            default -> {
                System.out.print("invalid input, please try again\n");
                printMenu();
            }
        }
    }
}
