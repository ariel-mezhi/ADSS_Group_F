package ServiceLayer;
import DomainLayer.*;
public class UserService {
    private EmployeeController employeeController;

    public UserService(){
        employeeController = EmployeeController.getInstance();
    }

    public Response login(String userName, String password){
        boolean error = false;
        String msg = "";
        try {
            employeeController.login(userName, password);
        }
        catch (RuntimeException e){
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
        catch (RuntimeException e){
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
        catch (RuntimeException e){
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
        catch (RuntimeException e){
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
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response addBranch(){
        boolean error = false;
        String msg;
        try {
            int newBID = employeeController.getBranches().size();
            employeeController.getBranches().add(newBID);
            msg = "new branch added, BID is " + newBID;
        }
        catch (RuntimeException e){
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
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response createNewRole(String role){
        boolean error = false;
        String msg;
        try {
            Role.getInstance().getRoles().add(role);
            msg = "new role added: " + role;
        }
        catch (RuntimeException e){
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
            employeeController.addRoleToEmployee(employeeName, role);
            msg = "new role added: " + role + " to: " + employeeName;
        }
        catch (RuntimeException e){
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
            employeeController.hireEmployee(BID,employeeName,bankAccount,role);
            msg = "congrats! you hired: " + employeeName;
        }
        catch (RuntimeException e){
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
        catch (RuntimeException e){
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
            employeeController.changeEmployeeGlobal(employeeName, newGlobalSalary);
            msg = "too late, too little";
        }
        catch (RuntimeException e){
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
            employeeController.changeEmployeeHourly(employeeName, newHourlySalary);
            msg = "too late, too little";
        }
        catch (RuntimeException e){
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
            employeeController.extendContract(employeeName, endContractDate);
            msg = "congrats, he stays";
        }
        catch (RuntimeException e){
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
        catch (RuntimeException e){
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
            employeeController.changePerformance(employeeName, performance);
            msg = "done";
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }








    public Response getInOrKickOut(String userName){
        try {
            employeeController.getInOrKickOut(userName);
        }catch (RuntimeException e){
            return new Response(true, e.getMessage());
        }
        return new Response(false, "gotIn");
    }

    public Response HR_Or_KickOut(String userName){
        try {
            employeeController.HR_Or_KickOut(userName);
        }catch (RuntimeException e){
            return new Response(true, e.getMessage());
        }
        return new Response(false, "HR gotIn");
    }

    public Response isValidBID(String BID){
        try {
            employeeController.exceptionIfInvalidBID(BID);
        }catch (RuntimeException e){
            return new Response(true, e.getMessage());
        }
        return new Response(false, "valid BID");
    }


    public EmployeeController getEmployeeController() {
        return employeeController;
    }


    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

}
