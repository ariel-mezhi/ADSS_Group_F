package ServiceLayer;
import DomainLayer.*;

public class ShiftService {
    private ShiftController shiftController;
    public ShiftService(){
        shiftController = ShiftController.getInstance();
    }


    public Response pickAvailability(String userName, String requiredShifts){
        boolean error = false;
        String msg="";
        try {
            shiftController.pickAvailability(userName, requiredShifts);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response showMyAvailability(String userName){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showMyAvailability(userName);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response changeAvailability(String userName, String dayAndType){
        boolean error = false;
        String msg="";
        try {
            shiftController.changeAvailability(userName, dayAndType);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response showCurrentWeek(String employeeName){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showCurrentWeek(employeeName);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response showNextWeek(String employeeName){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showNextWeek(employeeName);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response showMyHistory(String userName){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showMyHistory(userName);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response reportHours(String userName, String SID, String startTime, String endTime){
        boolean error = false;
        String msg="";
        try {
            shiftController.reportHours(userName, SID, startTime, endTime);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    // **HR

    public Response HR_showCurrentWeek(String BID){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.HR_showCurrentWeek(BID);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response HR_showNextWeek(String BID){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.HR_showNextWeek(BID);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response showAllHistory(){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showHistory();
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response showAllHistoryByBranch(String BID){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showHistoryByBranch(BID);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }




    public Response allowAvailabilityChanges(String BID){
        boolean error = false;
        String msg;
        try {
            shiftController.allowAvailabilityChanges(BID);
            msg = "done";
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response disableAvailabilityChanges(String BID){
        boolean error = false;
        String msg;
        try {
            shiftController.disableAvailabilityChanges(BID);
            msg = "done";
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response setHoliday(String BID, String dayAndType){
        boolean error = false;
        String msg;
        try {
            shiftController.setHoliday(BID, dayAndType);
            msg = "done";
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response changeRequirement(String BID, String dayAndType, String role, String amountOfEmployees){
        boolean error = false;
        String msg;
        try {
            shiftController.changeRequirement(BID, dayAndType, role, amountOfEmployees);
            msg = "done";
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response changeTime(String BID, String dayAndType, String start, String end){
        boolean error = false;
        String msg;
        try {
            shiftController.changeTime(BID, dayAndType, start, end);
            msg = "done";
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response showAvailableEmployees(String BID, String dayAndType, String role){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showAvailableEmployees(BID, dayAndType, role);
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response pickEmployee(String BID, String dayAndType, String role, String employeeName){
        boolean error = false;
        String msg;
        try {
            shiftController.pickEmployee(BID, dayAndType, role, employeeName);
            msg = "done";
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response publishNextWeek(String BID){
        boolean error = false;
        String msg;
        try {
            shiftController.publishNextWeek(BID);
            msg = "done";
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response setAsCurrentWeek(String BID){
        boolean error = false;
        String msg;
        try {
            shiftController.setAsCurrentWeek(BID);
            msg = "done";
        }
        catch (RuntimeException e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }


    public ShiftController getShiftController() {
        return shiftController;
    }

    public void setShiftController(ShiftController shiftController) {
        this.shiftController = shiftController;
    }
}
