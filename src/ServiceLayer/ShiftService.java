package ServiceLayer;

import DomainLayer.ShiftController;

import java.sql.SQLException;

public class ShiftService {
    private ShiftController shiftController;
    public ShiftService() throws SQLException {
        shiftController = ShiftController.getInstance();
    }


    public Response pickAvailability(String userName, String requiredShifts){
        boolean error = false;
        String msg="";
        try {
            shiftController.pickAvailability(userName, requiredShifts);
        }
        catch (Exception e){
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
        catch (Exception e){
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
        catch (Exception e){
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
        catch (Exception e){
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
        catch (Exception e){
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
        catch (Exception e){
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
        catch (Exception e){
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
        catch (Exception e){
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
        catch (Exception e){
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
        catch (Exception e){
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
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }




    public Response allowAvailabilityChanges(String BID){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.allowAvailabilityChanges(BID);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response disableAvailabilityChanges(String BID){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.disableAvailabilityChanges(BID);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response setHoliday(String BID, String dayAndType){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.setHoliday(BID, dayAndType);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response changeRequirement(String BID, String dayAndType, String role, String amountOfEmployees){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.changeRequirement(BID, dayAndType, role, amountOfEmployees);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response changeTime(String BID, String dayAndType, String start, String end){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.changeTime(BID, dayAndType, start, end);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response createNewRole(String role){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.createNewRole(role);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        Response r = new Response(error, msg);
        return r;
    }

    public Response showAvailableEmployees(String BID, String dayAndType, String role){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showAvailableEmployees(BID, dayAndType, role);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response getMissingRequirementsForShift(String BID, String shiftSign){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.getMissingRequirementsForShift(BID, shiftSign);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response pickEmployee(String BID, String dayAndType, String role, String employeeName, String TID){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.pickEmployee(BID, dayAndType, role, employeeName, TID);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response publishNextWeek(String BID){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.publishNextWeek(BID);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response setAsCurrentWeek(String BID){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.setAsCurrentWeek(BID);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response addBranch(){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.addBranch();
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }


    // --- TRANSPORTS --- //
    public Response showUnassignedTransports(String BID, String shiftSign){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showUnassignedTransports(BID, shiftSign);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response showTransportHistory(){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showTransportHistory();
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }
    public Response showTransportHistoryByBranch(String BID){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.showTransportHistoryByBranch(BID);
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);
    }

    public Response addTransport(String BID, String shiftSign, String Weight){
        boolean error = false;
        String msg;
        try {
            msg = shiftController.addTransport(BID, shiftSign, Weight).toString();
        }
        catch (Exception e){
            error = true;
            msg = e.getMessage();
        }
        return new Response(error, msg);    }

}
