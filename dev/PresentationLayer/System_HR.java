package PresentationLayer;
import ServiceLayer.*;
import java.util.Scanner;


public class System_HR {
    public String in;
    public String userName;
    public Service service;


    public void printMenu(){
        System.out.println("choose an option:");
        System.out.println("1. show all shifts history");
        System.out.println("2. show shifts history by branch");
        System.out.println("3. show branches IDs");
        System.out.println("4. add branch");
        System.out.println("5. show roles");
        System.out.println("6. create new role");
        System.out.println("7. publish next week");
        System.out.println("8. set as current week");
        System.out.println("9. change shift's requirement");
        System.out.println("10. show available employees for shift by role");
        System.out.println("11. pick employee");
        System.out.println("12. set shift as holiday");
        System.out.println("13. allow availability changes");
        System.out.println("14. disable availability changes");
        System.out.println("15. add role to employee");
        System.out.println("16. hire employee");
        System.out.println("17. fire employee");
        System.out.println("18. change employee's global salary");
        System.out.println("19. change employee's hourly salary");
        System.out.println("20. extend employee's contract");
        System.out.println("21. show employees performance");
        System.out.println("22. change employee's performance");
        System.out.println("23. show current week");
        System.out.println("24. show next week");
        System.out.println("25. change time");
        System.out.println("26. logout");

        getAnswer();
    }

    public void getAnswer(){
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        scanner.nextLine();
        switch (input) {
            case 1 -> {
                Response r = service.showAllHistory(userName);
                System.out.println(r.toString());
                printMenu();
            }
            case 2 -> {
                System.out.println("choose branch id");
                in = scanner.nextLine();
                Response r = service.showAllHistoryByBranch(userName, in);
                System.out.println(r.toString());
                printMenu();
            }

            case 3 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 4 -> {
                Response r = service.addBranch(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 5 -> {
                Response r = service.showRoles(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 6 -> {
                System.out.println("insert new role with no white spaces");
                in = scanner.nextLine();
                Response r = service.createNewRole(userName, in);
                System.out.println(r.toString());
                printMenu();
            }

            case 7 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                System.out.println("insert branch id");
                in = scanner.nextLine();
                r = service.publishNextWeek(userName, in);
                System.out.println(r.toString());
                printMenu();
            }

            case 8 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                System.out.println("insert branch id");
                in = scanner.nextLine();
                r = service.setAsCurrentWeek(userName, in);
                System.out.println(r.toString());
                printMenu();
            }

            case 9 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                System.out.println("---insert branch id");
                String BID = scanner.nextLine();
                System.out.println("---insert required shift (0-13)");
                String dayAndType = scanner.nextLine();
                r = service.showRoles(userName);
                System.out.println(r.toString());
                System.out.println("---insert role to change");
                String role = scanner.nextLine();
                System.out.println("---insert new amount of employees");
                String num = scanner.nextLine();
                r = service.changeRequirement(userName, BID, dayAndType, role, num);
                System.out.println(r.toString());
                printMenu();
            }

            case 10 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                System.out.println("---insert branch id");
                String BID = scanner.nextLine();
                System.out.println("---insert required shift (0-13)");
                String dayAndType = scanner.nextLine();
                r = service.showRoles(userName);
                System.out.println(r.toString());
                System.out.println("---insert role to change");
                String role = scanner.nextLine();
                r = service.showAvailableEmployees(userName, BID, dayAndType, role);
                System.out.println(r.toString());
                printMenu();
            }

            case 11 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                System.out.println("---insert branch id");
                String BID = scanner.nextLine();
                System.out.println("---insert required shift (0-13)");
                String dayAndType = scanner.nextLine();
                r = service.showRoles(userName);
                System.out.println(r.toString());
                System.out.println("---insert role to change");
                String role = scanner.nextLine();
                r = service.showAvailableEmployees(userName, BID, dayAndType, role);
                System.out.println("---insert employee's name");
                String name = scanner.nextLine();
                r = service.pickEmployee(userName, BID, dayAndType, role, name);
                System.out.println(r.toString());
                printMenu();
            }

            case 12 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                System.out.println("---insert branch id");
                String BID = scanner.nextLine();
                System.out.println("---insert required shift (0-13)");
                String dayAndType = scanner.nextLine();
                r = service.setHoliday(userName, BID, dayAndType);
                System.out.println(r.toString());
                printMenu();
            }

            case 13 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                System.out.println("---insert branch id");
                String BID = scanner.nextLine();
                r = service.allowAvailabilityChanges(userName, BID);
                System.out.println(r.toString());
                printMenu();
            }

            case 14 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                System.out.println("---insert branch id");
                String BID = scanner.nextLine();
                r = service.disableAvailabilityChanges(userName, BID);
                System.out.println(r.toString());
                printMenu();
            }

            case 15 -> {
                Response r = service.showRoles(userName);
                System.out.println(r.toString());
                System.out.println("---insert role to change");
                String role = scanner.nextLine();
                r = service.showEmployeesPerformance(userName);
                System.out.println("---insert employee's name");
                String name = scanner.nextLine();
                r = service.addRoleToEmployee(userName, name, role);
                System.out.println(r.toString());
                printMenu();
            }

            case 16 -> {
                Response r = service.showBranchesID(userName);
                System.out.println(r.toString());
                System.out.println("---insert branch id");
                String BID = scanner.nextLine();
                r = service.showRoles(userName);
                System.out.println(r.toString());
                System.out.println("---insert role to change");
                String role = scanner.nextLine();
                System.out.println("---insert bank account with no white spaces");
                String bank = scanner.nextLine();
                System.out.println("---insert name with no white spaces");
                String name = scanner.nextLine();
                r = service.hireEmployee(userName, BID, name, bank ,role);
                System.out.println(r.toString());
                printMenu();
            }

            case 17 -> {
                Response r = service.showEmployeesPerformance(userName);
                System.out.println(r.toString());
                System.out.println("---insert employee's name");
                String name = scanner.nextLine();
                r = service.fireEmployee(userName, name);
                System.out.println(r.toString());
                printMenu();
            }

            case 18 -> {
                Response r = service.showEmployeesPerformance(userName);
                System.out.println(r.toString());
                System.out.println("---insert employee's name");
                String name = scanner.nextLine();
                System.out.println("---insert employee's new salary");
                String salary = scanner.nextLine();
                r = service.changeEmployeeGlobal(userName, name, salary);
                System.out.println(r.toString());
                printMenu();
            }

            case 19 -> {
                Response r = service.showEmployeesPerformance(userName);
                System.out.println(r.toString());
                System.out.println("---insert employee's name");
                String name = scanner.nextLine();
                System.out.println("---insert employee's new salary");
                String salary = scanner.nextLine();
                r = service.changeEmployeeHourly(userName, name, salary);
                System.out.println(r.toString());
                printMenu();
            }

            case 20 -> {
                Response r = service.showEmployeesPerformance(userName);
                System.out.println(r.toString());
                System.out.println("---insert employee's name");
                String name = scanner.nextLine();
                System.out.println("---insert employee's date of end contract in format yyyy-MM-dd");
                String date = scanner.nextLine();
                r = service.extendContract(userName, name, date);
                System.out.println(r.toString());
                printMenu();
            }

            case 21 -> {
                Response r = service.showEmployeesPerformance(userName);
                System.out.println(r.toString());
                printMenu();
            }

            case 22 -> {
                Response r = service.showEmployeesPerformance(userName);
                System.out.println(r.toString());
                System.out.println("---insert employee's name");
                String name = scanner.nextLine();
                System.out.println("---insert employee's performance");
                String rate = scanner.nextLine();
                r = service.changePerformance(userName, name, rate);
                System.out.println(r.toString());
                printMenu();
            }

            case 23 -> {
                System.out.println("insert branchID");
                String BID = scanner.nextLine();
                Response r = service.HR_showCurrentWeek(userName, BID);
                System.out.println(r.toString());
                printMenu();
            }

            case 24 -> {
                System.out.println("insert branchID");
                String BID = scanner.nextLine();
                Response r = service.HR_showNextWeek(userName, BID);
                System.out.println(r.toString());
                printMenu();
            }
            case 25 -> {
                System.out.println("insert branchID");
                String BID = scanner.nextLine();
                System.out.println("insert shift number (0-13)");
                String num = scanner.nextLine();
                System.out.println("insert start time in format HH:MM");
                String start = scanner.nextLine();
                System.out.println("insert start time in format HH:MM");
                String end = scanner.nextLine();
                Response r = service.changeTime(userName, BID, num, start, end);
                System.out.println(r.toString());
                printMenu();

            }
            case 26 -> {
                Response r = service.logout(userName);
                System.out.println(r.toString());
            }

            default -> {
                System.out.print("invalid input, please try again\n");
                printMenu();
            }
        }

    }


}
