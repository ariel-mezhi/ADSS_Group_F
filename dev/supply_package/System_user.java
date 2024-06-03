package supply_package;

import java.util.Scanner;

import com.google.gson.JsonObject;

public class System_user {

    private Jsoncontroller jcontroller;

    public System_user(Supply supply){
        this.jcontroller = new Jsoncontroller(supply);
    }

    public void print_menu_worker(){
        System.out.print("Hello, please choose one option from the following:\n");
        System.out.print("1.Report faulty item \n"); // send to prints that ask for serial number and faulty description
        System.out.print("2.Get selling item price (provide item type id) \n");
        System.out.print("3.Get item location (provide serial number) \n");
        System.out.print("4.Exit \n");
    }

    public void get_answer_worker(){
        Scanner scanner = new Scanner(System.in);
        int user_input = scanner.nextInt();
        switch (user_input) {
            case 1 -> {
                System.out.print("enter the serial number of the item \n");
                user_input = scanner.nextInt();
                // add exception if entered string and then printed the same menu again
                if (user_input < 0) {
                    System.out.print("invalid serial number\n");
                    get_answer_worker();
                } else {
                    System.out.print("enter the faulty item description \n");
                    String user_string = scanner.nextLine();
                    JsonObject json = new JsonObject();
                    json.addProperty("serialnumber", user_input);
                    json.addProperty("faulty_description", user_string);
                    jcontroller.report_faulty_item(json);
                }
            }
            case 2 -> {
                JsonObject json2 = new JsonObject();
                json2.addProperty("type_id", user_input);
                jcontroller.get_item_selling_price(json2);
            }
            case 3 -> {
                JsonObject json3 = new JsonObject();
                json3.addProperty("serialnumber", user_input);
                jcontroller.get_item_location(json3);
            }
            case 4 -> {
                return;
            }
            default -> {
                System.out.print("invalid input, please try again");
                print_menu_worker();
            }
        }
    }

    public void print_menu_manager(){
        System.out.print("Hello, please choose one option from the following:\n");
        System.out.print("1.Get item location (provide serial number) \n");
        System.out.print("2.Get item manufacturer (provide serial number) \n");
        System.out.print("3.Get current amount of item type (provide item type id) \n");
        System.out.print("4.Get current amount of item type on shelves(provide item type id) \n");
        System.out.print("5.Get current amount of item type in storage(provide item type id) \n");
        System.out.print("6.Get selling item price (provide item type id) \n");
        System.out.print("7.Get cost price of item type(provide item type id) \n");
        System.out.print("8.Get current supply report \n"); // continue interaction with more prints
        System.out.print("9.Get current faulty item report \n");
        System.out.print("10.Pass days in system(provide amount of days)");
        System.out.print("11.Enter sale for items");
        System.out.print("12.Exit \n");
    }

    public void get_answer_manager(){
        Scanner scanner = new Scanner(System.in);
        int user_input = scanner.nextInt();
        switch (user_input) {
            case 1 -> {
                JsonObject json1 = new JsonObject();
                json1.addProperty("serialnumber", user_input);
                jcontroller.get_item_location(json1);
            }
            case 2 -> {
                JsonObject json2 = new JsonObject();
                json2.addProperty("serialnumber", user_input);
                jcontroller.get_item_manufacturer(json2);
            }
            case 3 -> {
                JsonObject json3 = new JsonObject();
                json3.addProperty("type_id", user_input);
                jcontroller.get_cur_amount_type(json3);
            }
            case 4 -> {
                JsonObject json4 = new JsonObject();
                json4.addProperty("type_id", user_input);
                jcontroller.get_cur_amount_type_shelves(json4);
            }
            case 5 -> {
                JsonObject json5 = new JsonObject();
                json5.addProperty("type_id", user_input);
                jcontroller.get_cur_amount_type_storage(json5);
            }
            case 6 -> {
                JsonObject json6 = new JsonObject();
                json6.addProperty("type_id", user_input);
                jcontroller.get_item_selling_price(json6);
            }
            case 7 -> {
                JsonObject json7 = new JsonObject();
                json7.addProperty("type_id", user_input);
                jcontroller.get_item_cost_price(json7);
            }
            case 8 -> {
                System.out.print("enter category/categories on report,press enter to include all categories\n");
                JsonObject json8 = new JsonObject();
                String user_input_string = scanner.nextLine();
                json8.addProperty("categories", user_input_string);
                jcontroller.create_supply_report(json8);
            }
            case 9 -> jcontroller.create_faulty_report();
            case 10 -> {
                System.out.print("Enter amount of days to pass?\n");
                user_input = scanner.nextInt();
                jcontroller.pass_days(user_input);
            }
            case 11 -> {
                System.out.print("Enter amount of days to the sale ");
                int amount_of_days = scanner.nextInt();
                System.out.print("Enter precentage of sale");
                user_input = scanner.nextInt();
                System.out.print("Enter item id");
                int item_id = scanner.nextInt();
                JsonObject json11 = new JsonObject();
                json11.addProperty("days", amount_of_days);
                json11.addProperty("precentage", user_input);
                json11.addProperty("item_id", item_id);
                jcontroller.set_sale(json11);
            }
            case 12 -> {
                return;
            }
            default -> {
                System.out.print("invalid input, please try again");
                print_menu_worker();
            }
        }
    }

    public void print_menu_cashier(){
        System.out.print("Hello, please choose one option from the following:\n");
        System.out.print("1.Remove bought items (provide serial numbers) \n");
        System.out.print("2.Report faulty item (provide serial number) \n");
        System.out.print("3.Exit \n");
    }


    public void get_answer_cashier(){
        Scanner scanner = new Scanner(System.in);
        int user_input = scanner.nextInt();
        switch (user_input) {
            case 1 -> {
                JsonObject json1 = new JsonObject();
                json1.addProperty("serialnumber", user_input);
                jcontroller.remove_item(json1);
            }
            case 2 -> {
                System.out.print("enter the serial number of the item \n");
                user_input = scanner.nextInt();
                // add exception if entered string and then printed the same menu again
                if (user_input < 0) {
                    System.out.print("invalid serial number\n");
                    get_answer_worker();
                } else {
                    System.out.print("enter the faulty item description \n");
                    String user_string = scanner.nextLine();
                    JsonObject json = new JsonObject();
                    json.addProperty("serialnumber", user_input);
                    json.addProperty("faulty_description", user_string);
                    jcontroller.report_faulty_item(json);
                }
            }
            case 3 -> {
                return;
            }
            default -> {
                System.out.print("invalid input, please try again");
                print_menu_worker();
            }
        }
    }

    //TODO add serial number checker
    //TODO add type_id checker
    //TODO check early input validity(entered only 1 digit)


}
