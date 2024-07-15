package PresentationLayer;
import java.sql.SQLException;
import java.util.Scanner;

import ServiceLayer.Jsoncontroller;
import com.google.gson.JsonObject;
import DomainLayer.*;

public class System_storeKeeper {
    public Jsoncontroller jcontroller;
    public Scanner scanner;

    public System_storeKeeper(Jsoncontroller jc){
        this.jcontroller = jc;
    }

    public void print_menu_storekeeper() throws SQLException {
        System.out.print("Hello, please choose one option from the following:\n");
        System.out.print("1.Add item to supply system\n");
        System.out.print("2.Remove item\n");
        System.out.print("3.Report faulty item \n");
        System.out.print("4.Get item location\n");
        System.out.print("5.Exit \n");
        get_answer_storekeeper();
    }

    public void get_answer_storekeeper() throws SQLException {
        int user_input = scanner.nextInt();
        switch (user_input) {
            case 1 -> {
                System.out.print("Enter type id \n");
                int type_id = scanner.nextInt();
                System.out.print("Enter amount of the item type\n");
                int amount = scanner.nextInt();
                System.out.print("Enter item's cost price\n");
                int cost_price = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter item's producer \n");
                String producer = scanner.next();
                scanner.nextLine();
                System.out.print("Enter item's category \n");
                String category = scanner.next();
                scanner.nextLine();
                System.out.print("Enter item's sub-category \n");
                String sub_category = scanner.next();
                scanner.nextLine();
                System.out.print("Enter item's size \n");
                String size = scanner.next();
                scanner.nextLine();
                System.out.print("Enter item's expiration date e.g: 12.12.2024 \n");
                String expiration_date = scanner.next();
                scanner.nextLine();
                System.out.print("Enter item's creation date e.g: 12.12.2024\n");
                String creation_date = scanner.next();
                scanner.nextLine();
                System.out.print("Enter item type supplier sale in percentage e.g 10% -> 10\n");
                int supplier_sale = scanner.nextInt();
                scanner.nextLine();
                JsonObject json = new JsonObject();
                json.addProperty("supplier_sale", supplier_sale);
                json.addProperty("type_id", type_id);
                json.addProperty("cost_price", cost_price);
                json.addProperty("producer", producer);
                json.addProperty("category", category);
                json.addProperty("sub_category", sub_category);
                json.addProperty("size", size);
                json.addProperty("exp_date", expiration_date);
                json.addProperty("creation_date", creation_date);
                json.addProperty("amount", amount);
                jcontroller.add_item_shop(json);
                print_menu_storekeeper();
            }
            case 2 -> {
                System.out.print("Enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                JsonObject json1 = new JsonObject();
                json1.addProperty("serialnumber", user_input1);
                jcontroller.remove_item(json1);
                print_menu_storekeeper();
            }
            case 3 -> {
                System.out.print("Enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                scanner.nextLine();
                if (user_input1 < 0) {
                    System.out.print("Invalid serial number\n");
                    get_answer_storekeeper();
                } else {
                    System.out.print("Enter the faulty item description \n");
                    String user_string = scanner.nextLine();
                    JsonObject json = new JsonObject();
                    json.addProperty("serialnumber", user_input1);
                    json.addProperty("faulty_description", user_string);
                    jcontroller.report_faulty_item(json);
                }
                print_menu_storekeeper();
            }
            case 4 -> {
                System.out.print("Enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                JsonObject json3 = new JsonObject();
                json3.addProperty("serialnumber", user_input1);
                System.out.print(jcontroller.get_item_location(json3));
                print_menu_storekeeper();
            }
            case 5 -> {
                return;
            }
            default -> {
                System.out.print("Invalid input, please try again\n");
                print_menu_storekeeper();
            }
        }
    }
}
