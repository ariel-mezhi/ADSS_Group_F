
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import supply_domain.Item;
import supply_domain.Item_type;
import supply_domain.Supply;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTest {
    Calendar calendar = Calendar.getInstance();
    Supply supply;

    public UnitTest() throws SQLException {
        this.supply = new Supply(calendar.getTime(),calendar);
    }



    @Test
    @Order(1)
    public void ReadingFromDB() throws SQLException {
        Item item = this.supply.getItem(1); // item should be exist in DB
        assertNotNull(item, "Item serial number 1 should not be null");
        assertEquals(1, item.getSerialNum(), "Expected serial number to be 1");
        Item item2 = this.supply.getItem(4); // item doesn't exist
        assertNull(item2, "Item with serial number 4 should be null");
        System.out.println("test 1 passed");
    }
    @Test
    @Order(2)
    public void RemovingFromDb() throws SQLException {
        Item item = this.supply.getItem(1);
        Item_type item_type = item.getType();
        int pre_total_amount = item_type.get_total_amount();
        this.supply.removeItem(1);
        Item null_item = this.supply.getItem(1); // item shouldn't be exist now
        assertNull(null_item, "Item serial number 1 should be null");
        int post_total_amount = item_type.get_total_amount();
        //checking correctness of type :
        assertEquals(post_total_amount, pre_total_amount - 1, "The numbers should be equal");
        System.out.println("test 2 passed");
    }
    @Test
    @Order(3)
    public void AddingNewItemAndType() throws SQLException {
        String expiration_date = "1.1.1111";
        String creation_date = "2.2.2222";
        String[] dateparts = expiration_date.split("\\.");
        int year = Integer.parseInt(dateparts[2]);
        int month = Integer.parseInt(dateparts[1]);
        int day = Integer.parseInt(dateparts[0]);
        Date exp_date = new Date(year-1900,month-1,day);
        dateparts = creation_date.split("\\.");
        year = Integer.parseInt(dateparts[2]);
        month = Integer.parseInt(dateparts[1]);
        day = Integer.parseInt(dateparts[0]);
        Date create_date = new Date(year-1900,month-1,day);
        this.supply.add_newItem(15,"tara","dairy","milk","200ml"
                ,10,exp_date,create_date,0,1);
        Item item = this.supply.getItem(0); // item should be exist in DB
        Item_type item_type = item.getType();
        // item serial number is 0 because it's generating from 0 and up, if its open
        assertNotNull(item, "Item with ID 1 should not be null");
        assertEquals(0, item.getSerialNum(), "Expected serial number to be 0");
        assertNotNull(item_type, "Item_type should not be null");
        assertEquals(15, item_type.getType_id(), "Expected item_type is 1");
        System.out.println("test 3 passed");
    }
    @Test
    @Order(4)
    public void RemovingFromRunTimeAndDB() throws SQLException {
        Item item = this.supply.getItem(0); // item should be exist in DB
        Item_type item_type = item.getType();
        // item serial number is 0 because it's generating from 0 and up, if its open
        assertNotNull(item, "Item with ID 1 should not be null");
        assertEquals(0, item.getSerialNum(), "Expected serial number to be 1");
        assertNotNull(item_type, "Item_type should not be null");
        assertEquals(15, item_type.getType_id(), "Expected item_type ID is 15");
        assertEquals(1, item_type.get_total_amount(), "Expected item_type total amount to be 1 pre deletion");
        this.supply.removeItem(0);
        Item null_item = this.supply.getItem(0); // item should be exist in DB
        assertNull(null_item, "Item serial number 1 should be null-not found");
        assertEquals(0, item_type.get_total_amount(), "Expected item_type total amount to be 0 post deletion");
        System.out.println("test 4 passed");
    }
    @Test
    @Order(5)
    public void send_to_order_shortageTest() throws SQLException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Act
            Item_type item_type = this.supply.getItem(3).getType();
            this.supply.removeItem(3);

            // Assert
            String expectedOutput = "item type id: 20 has low quantity and needs to be restocked\n" + "~Sending ORDER REQUEST(shortage) to supplier module~\n" + item_type.get_type_information() + "\n";
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            // Cleanup
            System.setOut(originalOut);
        }
        System.out.println("test 5 passed");
    }


    @Test
    @Order(6)
    public void send_to_order_periodicallyTest() throws SQLException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Act
            this.supply.pass_days(6);
            // Assert
            String expectedOutput = "~Sending ORDER REQUEST(periodically) to supplier module~ \n" + "all item's information: \n"
                    +"Item's information:\n" +
                    "1.Type id: 10\n" +
                    "2.Producer: tara\n" +
                    "3.Category: dairy\n" +
                    "4.Sub category: milk\n" +
                    "5.Size: 100ml\n" +
                    "5.Amount to order: 3 \n" +
                    "Sending order.....\n" +
                    "\n" +
                    "Item's information:\n" +
                    "1.Type id: 15\n" +
                    "2.Producer: tara\n" +
                    "3.Category: dairy\n" +
                    "4.Sub category: milk\n" +
                    "5.Size: 200ml\n" +
                    "5.Amount to order: 1 \n" +
                    "Sending order.....\n" +
                    "\n" +
                    "Item's information:\n" +
                    "1.Type id: 20\n" +
                    "2.Producer: tara\n" +
                    "3.Category: meat\n" +
                    "4.Sub category: stake\n" +
                    "5.Size: 100g\n" +
                    "5.Amount to order: 2 \n" +
                    "Sending order.....\n" +
                    "\n";
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            // Cleanup
            System.setOut(originalOut);
        }
        System.out.println("test 6 passed");
    }


    @Test
    @Order(7)
    public void UpdatingRunTimeType() throws SQLException {
        Item_type item_type = this.supply.getType(10);
        this.supply.set_selling_price(item_type, 10);
        Item_type updated_item_type = this.supply.getType(10);
        assertEquals(10, updated_item_type.getSelling_price(), "Expected selling price is 10");
        System.out.println("test 7 passed");
    }
    @Test
    @Order(8)
    public void FaultyReport() throws SQLException {
        this.supply.set_faulty_item(2,"faulty_item");
        this.supply.send_faulty_report();
        // this test should print that item with serial number 2 is faulty with the reason: faulty item
        System.out.println("test 8 passed");
    }
    @Test
    @Order(9)
    public void SupplyReport() throws SQLException {
        this.supply.supplyReport(""); // should show all items
        // expects 0 items with 3 types, each type with 0 units because of previous test
                 String expiration_date = "1.1.1111";
                String creation_date = "2.2.2222";
                String[] dateparts = expiration_date.split("\\.");
                int year = Integer.parseInt(dateparts[2]);
                int month = Integer.parseInt(dateparts[1]);
                int day = Integer.parseInt(dateparts[0]);
                Date exp_date = new Date(year-1900,month-1,day);
                dateparts = creation_date.split("\\.");
                year = Integer.parseInt(dateparts[2]);
                month = Integer.parseInt(dateparts[1]);
                day = Integer.parseInt(dateparts[0]);
                Date create_date = new Date(year-1900,month-1,day);
                this.supply.add_newItem(15,"tara","dairy","milk","200ml"
                        ,10,exp_date,create_date,0,1);
        this.supply.supplyReport(""); // should show all items
        // expects 1 item with 3 types, type 15(dairy,milk,200ml) has 1 unit
        System.out.println("test 9 passed");
    }
    @Test
    @Order(10)
    public void AddingSameItem() throws SQLException {
        String expiration_date = "1.1.1111";
        String creation_date = "2.2.2222";
        String[] dateparts = expiration_date.split("\\.");
        int year = Integer.parseInt(dateparts[2]);
        int month = Integer.parseInt(dateparts[1]);
        int day = Integer.parseInt(dateparts[0]);
        Date exp_date = new Date(year-1900,month-1,day);
        dateparts = creation_date.split("\\.");
        year = Integer.parseInt(dateparts[2]);
        month = Integer.parseInt(dateparts[1]);
        day = Integer.parseInt(dateparts[0]);
        Date create_date = new Date(year-1900,month-1,day);
        this.supply.add_newItem(15,"tara","dairy","milk","200ml"
                ,10,exp_date,create_date,0,1);
        Item item = this.supply.getItem(1); // item should be exist in DB
        Item_type item_type = item.getType();
        assertNotNull(item, "Item with ID 1 should not be null");
        assertEquals(1, item.getSerialNum(), "Expected serial number to be 0");
        assertNotNull(item_type, "Item_type should not be null");
        assertEquals(2, item_type.get_total_amount(), "Expected total of item_type is 2");
        System.out.println("test 10 passed");
    }
}
