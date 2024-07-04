package DomainLayer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShiftControllerTest {
    private static ShiftController shiftController;
    private static EmployeeController employeeController;
    private static TransportController transportController;
    @BeforeAll
    static void init () throws SQLException {
        employeeController = EmployeeController.getInstance();
        transportController = TransportController.getInstance();
        shiftController = ShiftController.getInstance();

    }
    @Test
    void pickAvailability() throws SQLException {
        shiftController.allowAvailabilityChanges("1");
        Random random = new Random();
        int randomNumber = random.nextInt(1000001);
        String name = "e" + randomNumber;
        employeeController.hireEmployee("1", name, name, "cashier");
        shiftController.pickAvailability(name, "0");
        String actual = shiftController.showMyAvailability(name);
        String expected = "day: SUNDAY  type: morning shift sign: 0\n";
        assertEquals(expected, actual);

    }


    @Test
    void allowAvailabilityChanges() throws SQLException {
        String result = shiftController.allowAvailabilityChanges("0");
        assertEquals("Availability changes are now allowed", result);
    }

    @Test
    void disableAvailabilityChanges() throws SQLException {
        String result = shiftController.disableAvailabilityChanges("0");
        assertEquals("Availability changes are no longer allowed", result);
    }



    @Test
    void addTransport() throws SQLException {
        int expectedTransportID = transportController.getTransports().size();
        Transport t = shiftController.addTransport("0", "0", "3400");
        int actuallID = t.getTID();
        assertEquals(expectedTransportID, actuallID);


    }

    @Test
    void createNewRole() throws SQLException {
        Random random = new Random();
        String randomRole = "" + random.nextInt(1000001);

        shiftController.createNewRole(randomRole);

        int randomNumber = random.nextInt(1000001);
        String name = "e" + randomNumber;
        employeeController.hireEmployee("0", name, name, "cashier");
        employeeController.addRoleToEmployee(name, randomRole);
        assertTrue(employeeController.getEmployee(name).getRoles().contains(randomRole));
    }

    @Test
    void addBranch() throws SQLException {
        String ExpectedBID = "The new branch ID is: " +employeeController.getBranches().size();
        String newBID = shiftController.addBranch();
        assertEquals(ExpectedBID, newBID);
    }
}