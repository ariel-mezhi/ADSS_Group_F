package DomainLayer;

import PresentationLayer.*;
import ServiceLayer.Jsoncontroller;
import ServiceLayer.Service;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.sql.SQLException;

import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;


public class IntegrationTest {
    private Service service;
    private Calendar calendar;
    private Supply supply;
    private Jsoncontroller jc;
    private System_SM systemSm;

    private static EmployeeController employeeController;
    public IntegrationTest() throws SQLException {
        Service service = new Service();
        Calendar calendar = Calendar.getInstance();
        Supply supply = new Supply(calendar.getTime(),calendar);
        Jsoncontroller jc = new Jsoncontroller(supply);
        System_SM systemSm = new System_SM(jc);
        System_storeKeeper systemStoreKeeper = new System_storeKeeper(jc);
        System_User system_user = new System_User(systemStoreKeeper);
        System_HR system_hr = new System_HR();
        System_TM system_tm = new System_TM();
        system_user.service = service;
        system_hr.service = service;
        system_tm.service = service;
    }


    @Test
    void IntegrationTestOne() throws SQLException {

    }

    @Test
    void IntegrationTestTwo() throws SQLException {
    }
}
