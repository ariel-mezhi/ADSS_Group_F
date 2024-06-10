package ServiceLayer;

import DomainLayer.Config;
import DomainLayer.Employee;

public class Service {
    private ShiftService shiftService;
    private UserService userService;

    public Service(){
        shiftService = new ShiftService();
        userService = new UserService();
    }


    // regular user
    public Response login(String userName, String password){
        return userService.login(userName,password);
    }

    public Response logout(String userName){
        return userService.logout(userName);
    }

    public Response changePassword(String userName, String newPassword){
        return userService.changePassword(userName,newPassword);
    }

    public Response daysLeftForContract(String userName){
        return userService.daysLeftForContract(userName);
    }



    public Response pickAvailability(String userName, String requiredShifts){
        Response r = userService.getInOrKickOut(userName);
        if (!r.isError){
            r = shiftService.pickAvailability(userName,requiredShifts);
        }
        return r;
    }

    public Response showMyAvailability(String userName){
        Response r = userService.getInOrKickOut(userName);
        if (!r.isError){
            r = shiftService.showMyAvailability(userName);
        }
        return r;
    }

    public Response changeAvailability(String userName, String dayAndType){
        Response r = userService.getInOrKickOut(userName);
        if (!r.isError){
            r = shiftService.changeAvailability(userName,dayAndType);
        }
        return r;
    }


    public Response showCurrentWeek(String userName){
        Response r = userService.getInOrKickOut(userName);
        if (!r.isError){
            r = shiftService.showCurrentWeek(userName);
        }
        return r;
    }

    public Response showNextWeek(String userName){
        Response r = userService.getInOrKickOut(userName);
        if (!r.isError){
            r = shiftService.showNextWeek(userName);
        }
        return r;
    }

    public Response showMyHistory(String userName){
        Response r = userService.getInOrKickOut(userName);
        if (!r.isError){
            r = shiftService.showMyHistory(userName);
        }
        return r;
    }

    public Response reportHours(String userName, String SID, String startTime, String endTime){
        Response r = userService.getInOrKickOut(userName);
        if (!r.isError){
            r = shiftService.reportHours(userName,SID, startTime, endTime);
        }
        return r;
    }




    // HR

    public Response HR_showCurrentWeek(String userName, String BID){
        Response r = userService.getInOrKickOut(userName);
        if (!r.isError){
            r = shiftService.HR_showCurrentWeek(BID);
        }
        return r;
    }

    public Response HR_showNextWeek(String userName, String BID){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError){
            r = shiftService.HR_showNextWeek(BID);
        }
        return r;
    }

    public Response showAllHistory(String userName){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError){
            r = shiftService.showAllHistory();
        }
        return r;
    }

    public Response showAllHistoryByBranch(String userName, String BID){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError){
            r = userService.isValidBID(BID);
            if(!r.isError){
                r = shiftService.showAllHistoryByBranch(BID);
            }
        }
        return r;
    }

    public Response showBranchesID(String userName){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.showBranchesID();
        }
        return r;
    }

    public Response addBranch(String userName){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.addBranch();
        }
        return r;
    }

    public Response showRoles(String userName){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.showRoles();
        }
        return r;
    }

    public Response createNewRole(String userName, String role){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.createNewRole(role);
        }
        return r;
    }

    public Response publishNextWeek(String userName, String BID){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = shiftService.publishNextWeek(BID);
        }
        return r;
    }

    public Response setAsCurrentWeek(String userName, String BID){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = shiftService.setAsCurrentWeek(BID);
        }
        return r;
    }

    public Response changeRequirement(String userName, String BID, String dayAndType, String role, String amountOfEmployees){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = shiftService.changeRequirement(BID, dayAndType, role, amountOfEmployees);
        }
        return r;
    }

    public Response changeTime(String userName, String BID, String dayAndType, String start, String end){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = shiftService.changeTime(BID, dayAndType, start, end);
        }
        return r;
    }

    public Response showAvailableEmployees(String userName, String BID, String dayAndType, String role){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = shiftService.showAvailableEmployees(BID, dayAndType, role);
        }
        return r;
    }

    public Response pickEmployee(String userName, String BID, String dayAndType, String role, String employeeName){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = shiftService.pickEmployee(BID, dayAndType, role, employeeName);
        }
        return r;
    }

    public Response setHoliday(String userName, String BID, String dayAndType){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = shiftService.setHoliday(BID, dayAndType);
        }
        return r;
    }

    public Response allowAvailabilityChanges(String userName, String BID){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = shiftService.allowAvailabilityChanges(BID);
        }
        return r;
    }

    public Response disableAvailabilityChanges(String userName, String BID){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = shiftService.disableAvailabilityChanges(BID);
        }
        return r;
    }

    public Response addRoleToEmployee(String userName, String employeeName, String role){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.addRoleToEmployee(employeeName, role);
        }
        return r;
    }

    public Response hireEmployee(String userName, String BID, String employeeName, String bankAccount, String role){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.hireEmployee(BID,employeeName,bankAccount,role);
        }
        return r;
    }

    public Response fireEmployee(String userName, String employeeName){
        Response r = userService.HR_Or_KickOut(userName);
        if(!r.isError){
            r = userService.fireEmployee(employeeName);
        }
        return r;
    }

    public Response changeEmployeeGlobal(String userName, String employeeName, String newGlobalSalary){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.changeEmployeeGlobal(employeeName, newGlobalSalary);
        }
        return r;
    }

    public Response changeEmployeeHourly(String userName, String employeeName, String newHourlySalary){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.changeEmployeeHourly(employeeName, newHourlySalary);
        }
        return r;
    }

    public Response extendContract(String userName, String employeeName, String endContractDate){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.extendContract(employeeName, endContractDate);
        }
        return r;
    }

    public Response showEmployeesPerformance(String userName){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.showEmployeesPerformance();
        }
        return r;
    }

    public Response changePerformance(String userName, String employeeName, String performance){
        Response r = userService.HR_Or_KickOut(userName);
        if (!r.isError) {
            r = userService.changePerformance(employeeName, performance);
        }
        return r;
    }

    public void useConfig(Config c){
        userService.getEmployeeController().Config_setEmployees(c.employees);
        shiftService.getShiftController().Config_setShifts(c.shifts);
    }
}
