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
        get_answer_worker();
    }

    public void get_answer_worker(){
        Scanner scanner = new Scanner(System.in);
        int user_input = scanner.nextInt();
        switch (user_input) {
            case 1 -> {
                System.out.print("enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                scanner.nextLine();
                // add exception if entered string and then printed the same menu again
                if (user_input1 < 0) {
                    System.out.print("invalid serial number\n");
                    get_answer_worker();
                } else {
                    System.out.print("enter the faulty item description \n");
                    String user_string = scanner.nextLine();
                    JsonObject json = new JsonObject();
                    json.addProperty("serialnumber", user_input1);
                    json.addProperty("faulty_description", user_string);
                    jcontroller.report_faulty_item(json);
                }
                print_menu_worker();
            }
            case 2 -> {
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                JsonObject json2 = new JsonObject();
                json2.addProperty("type_id", type_id);
                float selling_price = jcontroller.get_item_selling_price(json2);
                if(selling_price == -1)
                    System.out.print("type not exist\n");
                System.out.print(selling_price);
                System.out.print("\n");
                print_menu_worker();
            }
            case 3 -> {
                System.out.print("enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                JsonObject json3 = new JsonObject();
                json3.addProperty("serialnumber", user_input1);
                System.out.print(jcontroller.get_item_location(json3));
                print_menu_worker();
            }
            case 4 -> {
                return;
            }
            default -> {
                System.out.print("invalid input, please try again\n");
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
        System.out.print("8.Get current supply report \n");
        System.out.print("9.Get current faulty item report \n");
        System.out.print("10.Pass days in system(provide amount of days)\n");
        System.out.print("11.Enter sale for items\n");
        System.out.print("12.Exit \n");
        get_answer_manager();
    }

    public void get_answer_manager(){
        Scanner scanner = new Scanner(System.in);
        int user_input = scanner.nextInt();
        switch (user_input) {
            case 1 -> {
                System.out.print("enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                JsonObject json1 = new JsonObject();
                json1.addProperty("serialnumber", user_input1);
                System.out.print(jcontroller.get_item_location(json1));
                print_menu_manager();
            }
            case 2 -> {
                System.out.print("enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                JsonObject json2 = new JsonObject();
                json2.addProperty("serialnumber", user_input1);
                System.out.print(jcontroller.get_item_manufacturer(json2));
                print_menu_manager();
            }
            case 3 -> {
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                JsonObject json3 = new JsonObject();
                json3.addProperty("type_id", type_id);
                int cur_amount = jcontroller.get_cur_amount_type(json3);
                if(cur_amount == -1){
                    System.out.print("type not exist\n");
                }
                else{
                    System.out.print(cur_amount);
                    System.out.print("\n");
                }
                print_menu_manager();
            }
            case 4 -> {
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                JsonObject json4 = new JsonObject();
                json4.addProperty("type_id", type_id);
                int cur_amount = jcontroller.get_cur_amount_type_shelves(json4);
                if(cur_amount == -1){
                    System.out.print("type not exist\n");
                }
                else{
                    System.out.print(cur_amount);
                    System.out.print("\n");
                }
                print_menu_manager();
            }
            case 5 -> {
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                JsonObject json5 = new JsonObject();
                json5.addProperty("type_id", type_id);
                int cur_amount = jcontroller.get_cur_amount_type_storage(json5);
                if(cur_amount == -1){
                    System.out.print("type not exist\n");
                }
                else{
                    System.out.print(cur_amount);
                    System.out.print("\n");
                }
                print_menu_manager();
            }
            case 6 -> {
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                JsonObject json6 = new JsonObject();
                json6.addProperty("type_id", type_id);
                float selling_price = jcontroller.get_item_selling_price(json6);
                if(selling_price == -1){
                    System.out.print("type not exist\n");
                }
                else{
                    System.out.print(selling_price);
                    System.out.print("\n");
                }
                print_menu_manager();
            }
            case 7 -> {
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                JsonObject json7 = new JsonObject();
                json7.addProperty("type_id", type_id);
                float cost_price = jcontroller.get_item_cost_price(json7);
                if(cost_price == -1){
                    System.out.print("type not exist\n");
                }
                else{
                    System.out.print(cost_price);
                    System.out.print("\n");
                }
                print_menu_manager();
            }
            case 8 -> {
                scanner.nextLine();
                System.out.print("enter category/categories on report,press enter to include all categories\n");
                JsonObject json8 = new JsonObject();
                String user_input_string = scanner.nextLine();
                json8.addProperty("categories", user_input_string);
                jcontroller.create_supply_report(json8);
                print_menu_manager();
            }
            case 9 -> {jcontroller.create_faulty_report();print_menu_manager();}
            case 10 -> {
                System.out.print("Enter amount of days to pass?\n");
                int amount_of_days = scanner.nextInt();
                jcontroller.pass_days(amount_of_days);
                print_menu_manager();
            }
            case 11 -> {
                System.out.print("Enter amount of days to the sale\n ");
                int amount_of_days = scanner.nextInt();
                System.out.print("Enter precentage of sale\n");
                user_input = scanner.nextInt();
                System.out.print("Enter type id\n");
                int type_id = scanner.nextInt();
                JsonObject json11 = new JsonObject();
                json11.addProperty("days", amount_of_days);
                json11.addProperty("precentage", user_input);
                json11.addProperty("type_id", type_id);
                jcontroller.set_sale(json11);
                print_menu_manager();
            }
            case 12 -> {
                return;
            }
            default -> {
                System.out.print("invalid input, please try again\n");
                print_menu_manager();
            }
        }
    }

    public void print_menu_cashier(){
        System.out.print("Hello, please choose one option from the following:\n");
        System.out.print("1.Remove bought items (provide serial numbers) \n");
        System.out.print("2.Report faulty item (provide serial number) \n");
        System.out.print("3.Exit \n");
        get_answer_cashier();
    }


    public void get_answer_cashier(){
        Scanner scanner = new Scanner(System.in);
        int user_input = scanner.nextInt();
        switch (user_input) {
            case 1 -> {
                System.out.print("enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                JsonObject json1 = new JsonObject();
                json1.addProperty("serialnumber", user_input1);
                jcontroller.remove_item(json1);
                print_menu_cashier();
            }
            case 2 -> {
                System.out.print("enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                scanner.nextLine();
                // add exception if entered string and then printed the same menu again
                if (user_input1 < 0) {
                    System.out.print("invalid serial number\n");
                    get_answer_worker();
                } else {
                    System.out.print("enter the faulty item description \n");
                    String user_string = scanner.nextLine();
                    JsonObject json = new JsonObject();
                    json.addProperty("serialnumber", user_input1);
                    json.addProperty("faulty_description", user_string);
                    jcontroller.report_faulty_item(json);
                }
                print_menu_cashier();
            }
            case 3 -> {
                return;
            }
            default -> {
                System.out.print("invalid input, please try again\n");
                print_menu_cashier();
            }
        }
    }
}
