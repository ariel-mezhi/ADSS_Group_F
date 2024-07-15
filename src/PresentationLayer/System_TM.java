package PresentationLayer;
import ServiceLayer.*;
import java.util.Scanner;

public class System_TM {
    public String in;
    public String userName;
    public String out;

    public Service service;

    public void printMenu(){
        System.out.println("************************************************************");
        System.out.println("            Hello TM, please choose an option:");
        System.out.println("************************************************************");

        System.out.println("    1. Add transport");
        System.out.println("    2. Show transport history");
        System.out.println("    3. Show transport by Branch ID");
        System.out.println("    4. exit");
        System.out.println("************************************************************");
        getAnswer();
    }

    public void getAnswer(){
        int input;
        Scanner scanner = new Scanner(System.in);
        try {
            input = scanner.nextInt();
        }catch (Exception e){
            input = 0;
        }
        scanner.nextLine();
        switch (input) {
            case 1 -> {
                //String userName, String BID, String shiftSign, String Weight
                System.out.println("Please enter Branch ID");
                String BID = scanner.nextLine();
                System.out.println("Please enter required shift");
                String shiftSign = scanner.nextLine();
                System.out.println("Please enter transport's weight");
                String weight = scanner.nextLine();
                Response r = service.addTransport(userName, BID, shiftSign, weight);
                out = r.toString();
                System.out.println(out);
                printMenu();
            }
            case 2 -> {
                Response r = service.showTransportHistory(userName);
                out = r.toString();
                System.out.println(out);
                printMenu();
            }

            case 3 -> {
                Response rs = service.showBranchesID(userName);
                System.out.println("Please enter Branch ID");
                String BID = scanner.nextLine();
                Response r = service.showTransportHistoryByBranch(userName, BID);
                out = r.toString();
                System.out.println(out);
                printMenu();
            }

            case 4 -> {
                Response r = service.logout(userName);
                out = r.isError ? r.msg: "BYE BYE " + userName +" , have a lovely day :D";
                System.out.println(out);

            }


            default -> {
                System.out.println("invalid input, please try again");
                printMenu();
            }
        }
    }
}
