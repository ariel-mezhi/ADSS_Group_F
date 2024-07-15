package ServiceLayer;

import DomainLayer.EmployeeController;
import DomainLayer.Role;

import java.sql.SQLException;

public class UserService {
    private EmployeeController employeeController;

    public UserService() throws SQLException {
        employeeController = EmployeeController.getInstance();
    }

    public Response login(String userName, String password){
        boolean error = false;
        String msg = "";
        try {
            employeeController.login(userName, password);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response isStoreKeeper(String userName){
        boolean error = false;
        String msg = "";
        try {
            error = employeeController.isStoreKeeper(userName);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response logout(String userName){
        boolean error = false;
        String msg = "";
        try {
            employeeController.logout(userName);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response changePassword(String userName, String newPassword){
        boolean error = false;
        String msg = "";
        try {
            employeeController.changePassword(userName, newPassword);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response daysLeftForContract(String userName){
        boolean error = false;
        String msg;
        try {
            msg = String.valueOf(employeeController.daysLeftForContract(userName));
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }


    //HR
    public Response showBranchesID(){
        boolean error = false;
        String msg;
        try {
            msg = employeeController.getBranches().toString();
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }



    public Response showRoles(){
        boolean error = false;
        String msg;
        try {
            msg = Role.getInstance().toString();
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }


    public Response addRoleToEmployee(String employeeName, String role){
        boolean error = false;
        String msg;
        try {
            msg = employeeController.addRoleToEmployee(employeeName, role);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response hireEmployee(String BID, String employeeName, String bankAccount, String role){
        boolean error = false;
        String msg;
        try {
            msg = employeeController.hireEmployee(BID,employeeName,bankAccount,role).toString();
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response fireEmployee(String employeeName){
        boolean error = false;
        String msg;
        try {
            msg = employeeController.fireEmployee(employeeName);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response changeEmployeeGlobal(String employeeName, String newGlobalSalary){
        boolean error = false;
        String msg;
        try {
            msg = employeeController.changeEmployeeGlobal(employeeName, newGlobalSalary);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response changeEmployeeHourly(String employeeName, String newHourlySalary){
        boolean error = false;
        String msg;
        try {
            msg = employeeController.changeEmployeeHourly(employeeName, newHourlySalary);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response extendContract(String employeeName, String endContractDate){
        boolean error = false;
        String msg;
        try {
            msg = employeeController.extendContract(employeeName, endContractDate);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response showEmployeesPerformance(){
        boolean error = false;
        String msg;
        try {
            msg = employeeController.showEmployeesPerformance();
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response changePerformance(String employeeName, String performance){
        boolean error = false;
        String msg;
        try {
            msg = employeeController.changePerformance(employeeName, performance);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }








    public Response getInOrKickOut(String userName){
        try {
            employeeController.getInOrKickOut(userName);
        }catch (Exception e){
            return new Response(true, e.getMessage());
        }
        return new Response(false, "");
    }

    public Response HR_Or_KickOut(String userName){
        try {
            employeeController.HR_Or_KickOut(userName);
        }catch (Exception e){
            return new Response(true, e.getMessage());
        }
        return new Response(false, "");
    }
    public Response TM_Or_KickOut(String userName){
        try {
            employeeController.TM_Or_KickOut(userName);
        }catch (Exception e){
            return new Response(true, e.getMessage());
        }
        return new Response(false, "");
    }

    public Response isValidBID(String BID){
        try {
            employeeController.exceptionIfInvalidBID(BID);
        }catch (Exception e){
            return new Response(true, e.getMessage());
        }
        return new Response(false, "");
    }


    public EmployeeController getEmployeeController() {
        return employeeController;
    }


    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

}
