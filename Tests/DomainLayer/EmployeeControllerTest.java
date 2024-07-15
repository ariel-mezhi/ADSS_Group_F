package DomainLayer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.sql.SQLException;

import java.util.Random;


class EmployeeControllerTest {
    private static EmployeeController employeeController;
    @BeforeAll
    static void init () throws SQLException {
        employeeController = EmployeeController.getInstance();
    }

    @Test
    void hireEmployee() throws SQLException {
        Random random = new Random();
        int randomNumber = random.nextInt(1000001);
        String name = "e" + randomNumber;
        Employee e3 = employeeController.hireEmployee("0", name, name, "shiftManager");
        assertTrue(employeeController.getEmployees().contains(e3), "Employee exists");
    }

    @Test
    void addRoleToEmployee() throws SQLException {
        Random random = new Random();
        int randomNumber = random.nextInt(1000001);
        String name = "e" + randomNumber;
        employeeController.hireEmployee("0", name, name, "cashier");
        employeeController.addRoleToEmployee(name, "driver_B");
        assertTrue(employeeController.getEmployee(name).getRoles().contains("driver_B"));
    }


    @Test
    void fireEmployee() throws SQLException {
        Employee e2 = employeeController.hireEmployee("0", "e2", "e2", "cashier");
        employeeController.fireEmployee("e2");
        assertTrue(employeeController.getFiredEmployees().contains(e2), "Employee exists");
    }

    @Test
    void changePerformance() throws SQLException {
        Random random = new Random();
        int randomNumber = random.nextInt(1000001);
        String name = "e" + randomNumber;
        employeeController.hireEmployee("0", name, name, "cashier");
        assertEquals(employeeController.getEmployee(name).getPerformance(), 5);
        employeeController.changePerformance(name, "10");
        assertEquals(employeeController.getEmployee(name).getPerformance(), 10);
    }
}