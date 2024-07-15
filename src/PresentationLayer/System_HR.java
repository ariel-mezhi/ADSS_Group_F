package PresentationLayer;

import ServiceLayer.*;
import java.util.Scanner;

public class System_HR {
    private String in;
    public String userName;
    private String out;
    public Service service;
    private Scanner scanner;

    public System_HR(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printMenu() {
        System.out.println("************************************************************");
        System.out.println("            Hello HR, please choose an option:");
        System.out.println();
        System.out.println("|---------------------- Employees -------------------------|");
        System.out.println("    1. add role to employee");
        System.out.println("    2. hire employee");
        System.out.println("    3. fire employee");
        System.out.println("    4. change employee's global salary");
        System.out.println("    5. change employee's hourly salary");
        System.out.println("    6. extend employee's contract");
        System.out.println("    7. change employee's performance");
        System.out.println("|----------------------------------------------------------|");
        System.out.println();
        System.out.println("|------------------------- Shift --------------------------|");
        System.out.println("    8. assign employee to a shift");
        System.out.println("    9. show all shifts history");
        System.out.println("    10. show shifts history by branch");
        System.out.println("    11. change shift's requirement");
        System.out.println("    12. change shift working hours");
        System.out.println("|----------------------------------------------------------|");
        System.out.println();
        System.out.println("|------------------------- Schedule -----------------------|");
        System.out.println("    13. allow availability changes for branch");
        System.out.println("    14. disable availability changes for branch");
        System.out.println("    15. publish next week");
        System.out.println("    16. set as current week");
        System.out.println("    17. set shift as holiday");
        System.out.println("    18. show current week for branch");
        System.out.println("    19. show next week for branch");
        System.out.println("|----------------------------------------------------------|");
        System.out.println();
        System.out.println("|-------------------------- Other -------------------------|");
        System.out.println("    20. create new role");
        System.out.println("    21. add branch");
        System.out.println("    22. Logout");
        System.out.println("************************************************************");
        getAnswer();
    }

    private void getAnswer() {
        int input;
        try {
            input = scanner.nextInt();
        } catch (Exception e) {
            input = 0;
        }
        scanner.nextLine();
        handleOption(input);
    }

    private void handleOption(int input) {
        switch (input) {
            case 1 -> addRoleToEmployee();
            case 2 -> hireEmployee();
            case 3 -> fireEmployee();
            case 4 -> changeEmployeeGlobalSalary();
            case 5 -> changeEmployeeHourlySalary();
            case 6 -> extendEmployeeContract();
            case 7 -> changeEmployeePerformance();
            case 8 -> assignEmployeeToShift();
            case 9 -> showAllShiftsHistory();
            case 10 -> showShiftsHistoryByBranch();
            case 11 -> changeShiftRequirement();
            case 12 -> changeShiftWorkingHours();
            case 13 -> allowAvailabilityChangesForBranch();
            case 14 -> disableAvailabilityChangesForBranch();
            case 15 -> publishNextWeek();
            case 16 -> setAsCurrentWeek();
            case 17 -> setShiftAsHoliday();
            case 18 -> showCurrentWeekForBranch();
            case 19 -> showNextWeekForBranch();
            case 20 -> createNewRole();
            case 21 -> addBranch();
            case 22 -> logout();
            default -> {
                System.out.print("Invalid input, please try again\n");
                printMenu();
            }
        }
    }

    private void addRoleToEmployee() {
        Response r = service.showRoles(userName);
        System.out.println(r.toString());
        System.out.println("---insert role to add");
        String role = scanner.nextLine();
        r = service.showEmployeesPerformance(userName);
        System.out.println("---insert employee's name");
        String name = scanner.nextLine();
        r = service.addRoleToEmployee(userName, name, role);
        System.out.println(r.toString());

        printMenu();
    }

    private void hireEmployee() {
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
        r = service.hireEmployee(userName, BID, name, bank, role);
        System.out.println(r.toString());
        printMenu();
    }

    private void fireEmployee() {
        Response r = service.showEmployeesPerformance(userName);
        System.out.println(r.toString());
        System.out.println("---insert employee's name");
        String name = scanner.nextLine();
        r = service.fireEmployee(userName, name);
        System.out.println(r.toString());
        printMenu();
    }

    private void changeEmployeeGlobalSalary() {
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

    private void changeEmployeeHourlySalary() {
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

    private void extendEmployeeContract() {
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

    private void changeEmployeePerformance() {
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

    private void assignEmployeeToShift() {
        Response r = service.showBranchesID(userName);
        System.out.println("the branches are: \n" + r.toString());
        System.out.println("---insert branch id");
        String BID = scanner.nextLine();
        System.out.println("---insert required shift (0-13)");
        String dayAndType = scanner.nextLine();
        r = service.getMissingRequirementsForShift(BID, dayAndType);
        System.out.println("according to the requirements: \n" + r.toString());
        System.out.println("---insert role");
        String role = scanner.nextLine();
        String TID = "";
        if (role.equals("driver_B") || role.equals("driver_C1") || role.equals("driver_C")) {
            r = service.showUnassignedTransports(userName, BID, dayAndType);
            System.out.println(r.toString());
            System.out.println("---insert transport ID");
            TID = scanner.nextLine();
        }
        System.out.println("---insert employee's name");
        String name = scanner.nextLine();
        r = service.pickEmployee(userName, BID, dayAndType, role, name, TID);
        out = r.toString();
        System.out.println(out);
        printMenu();
    }

    private void showAllShiftsHistory() {
        Response r = service.showAllHistory(userName);
        System.out.println(r.toString());
        printMenu();
    }

    private void showShiftsHistoryByBranch() {
        System.out.println("choose branch id");
        in = scanner.nextLine();
        Response r = service.showAllHistoryByBranch(userName, in);
        System.out.println(r.toString());
        printMenu();
    }

    private void changeShiftRequirement() {
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

    private void changeShiftWorkingHours() {
        System.out.println("insert branchID");
        String BID = scanner.nextLine();
        System.out.println("insert shift number (0-13)");
        String num = scanner.nextLine();
        System.out.println("insert start time in format HH:MM");
        String start = scanner.nextLine();
        System.out.println("insert end time in format HH:MM");
        String end = scanner.nextLine();
        Response r = service.changeTime(userName, BID, num, start, end);
        System.out.println(r.toString());
        printMenu();
    }

    private void allowAvailabilityChangesForBranch() {
        Response r = service.showBranchesID(userName);
        System.out.println(r.toString());
        System.out.println("---insert branch id");
        String BID = scanner.nextLine();
        r = service.allowAvailabilityChanges(userName, BID);
        System.out.println(r.toString());
        printMenu();
    }

    private void disableAvailabilityChangesForBranch() {
        Response r = service.showBranchesID(userName);
        System.out.println(r.toString());
        System.out.println("---insert branch id");
        String BID = scanner.nextLine();
        r = service.disableAvailabilityChanges(userName, BID);
        System.out.println(r.toString());
        printMenu();
    }

    private void publishNextWeek() {
        Response r = service.showBranchesID(userName);
        System.out.println(r.toString());
        System.out.println("insert branch id");
        in = scanner.nextLine();
        r = service.publishNextWeek(userName, in);
        System.out.println(r.toString());
        printMenu();
    }

    private void setAsCurrentWeek() {
        Response r = service.showBranchesID(userName);
        System.out.println(r.toString());
        System.out.println("insert branch id");
        in = scanner.nextLine();
        r = service.setAsCurrentWeek(userName, in);
        System.out.println(r.toString());
        printMenu();
    }

    private void setShiftAsHoliday() {
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

    private void showCurrentWeekForBranch() {
        System.out.println("insert branchID");
        String BID = scanner.nextLine();
        Response r = service.HR_showCurrentWeek(userName, BID);
        System.out.println(r.toString());
        printMenu();
    }

    private void showNextWeekForBranch() {
        System.out.println("insert branchID");
        String BID = scanner.nextLine();
        Response r = service.HR_showNextWeek(userName, BID);
        System.out.println(r.toString());
        printMenu();
    }

    private void createNewRole() {
        System.out.println("insert new role with no white spaces");
        in = scanner.nextLine();
        Response r = service.createNewRole(userName, in);
        System.out.println(r.toString());
        printMenu();
    }

    private void addBranch() {
        Response r = service.addBranch(userName);
        System.out.println(r.toString());
        printMenu();
    }

    private void logout() {
        System.out.println("BYE BYE " + userName +" , have a lovely day :D");
        Response r = service.logout(userName);
        System.out.println(r.toString());
    }
}
