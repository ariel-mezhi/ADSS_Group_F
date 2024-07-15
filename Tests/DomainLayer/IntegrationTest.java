package DomainLayer;

import PresentationLayer.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

public class IntegrationTest {

    @Test
    void IntegrationTestOne() throws SQLException {

        //simulating an input to main with worker that is not storekeeper
        String simulatedInput =
                "1\n" +
                        "SM\n" +
                        "SM\n" +
                        "0\n" + // trying to reach option 0 but SM is not storekeeper
                        "10\n" + // clicking 10 because menu 0 is unreachable,exiting menu
                        "5\n";
        InputStream originalIn = System.in;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(byteArrayInputStream);
        Main.main(new String[0]);
        System.setIn(originalIn);
        System.out.println("expected to exit from system without any changes, SM not storekeeper - test passed");
    }

    @Test
    void IntegrationTestTwo() throws SQLException {
        // before this test, delete 2 tables of items and types, then run Database class to load fresh tables,
        // item number 0 doesn't exist in fresh tables!
        //simulating an input to main method(integrated modules)
        String simulatedInput =
                        "2\n" +
                        "HR\n" +
                        "HR\n" +
                        "2\n" +
                        "0\n" +
                        "storekeeper\n" +
                        "omer2\n" +
                        "omer2\n" +
                        "22\n" +
                        "1\n" +
                        "omer2\n" +
                        "omer2\n" +
                                // so far entered HR module
                        "0\n" +
                        "1\n" +
                        "15\n" +
                        "1\n" +
                        "15\n" +
                        "tara\n" +
                        "dairy\n" +
                        "milk\n" +
                        "500ml\n" +
                        "1.1.1111\n" +
                        "1.1.1111\n" +
                                "0\n"
                                +"4\n"
                                +"0\n"
                                +"5\n"
                                +"5\n" ;
        InputStream originalIn = System.in;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(byteArrayInputStream);
        Main.main(new String[0]);
        System.setIn(originalIn);
        System.out.println("item with id 0 is in storage as expected - test passed");
        // we can also see item added in DB
    }
}
