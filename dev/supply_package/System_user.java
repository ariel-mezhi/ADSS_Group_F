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
        System.out.print("1.Remove item\n");
        System.out.print("2.Report faulty item \n");
        System.out.print("3.Get item location\n");
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
                JsonObject json1 = new JsonObject();
                json1.addProperty("serialnumber", user_input1);
                jcontroller.remove_item(json1);
                print_menu_worker();
            }
            case 2 -> {
                System.out.print("enter the serial number of the item \n");
                int user_input1 = scanner.nextInt();
                scanner.nextLine();
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
        System.out.print("1.Get item location\n");
        System.out.print("2.Get item manufacturer\n");
        System.out.print("3.Get current amount of item type\n");
        System.out.print("4.Get current amount of item type on shelves\n");
        System.out.print("5.Get current amount of item type in storage\n");
        System.out.print("6.Get selling item price \n");
        System.out.print("7.Get cost price of item type \n");
        System.out.print("8.Get current supply report \n");
        System.out.print("9.Get current faulty item report \n");
        System.out.print("10.Pass days in system\n");
        System.out.print("11.Enter sale for items\n");
        System.out.print("12.Set minimal amount of item type\n");
        System.out.print("13.Enter sale for category or categories\n");
        System.out.print("14.Add new area to shop\n");
        System.out.print("15.Add shelf to specific area\n");
        System.out.print("16.Get sale from supplier on item type\n");
        System.out.print("17.Set new supplier sale of item type\n");
        System.out.print("18.Set new selling price of item type\n");
        System.out.print("19.set new cost price of item type\n");
        System.out.print("20.Exit \n");
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
                System.out.print("Enter percentage of sale\n");
                user_input = scanner.nextInt();
                System.out.print("Enter type id\n");
                int type_id = scanner.nextInt();
                JsonObject json11 = new JsonObject();
                json11.addProperty("days", amount_of_days);
                json11.addProperty("percentage", user_input);
                json11.addProperty("type_id", type_id);
                jcontroller.set_sale(json11);
                print_menu_manager();
            }
            case 12 -> {
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                System.out.print("enter the minimal amount \n");
                int minimal_amount = scanner.nextInt();
                JsonObject json12 = new JsonObject();
                json12.addProperty("type_id", type_id);
                json12.addProperty("minimal_amount", minimal_amount);
                jcontroller.set_minimal_amount_type(json12);
                print_menu_manager();
            }
            case 13 -> {
                scanner.nextLine();
                System.out.print("enter category/categories for sale\n");
                String categories = scanner.nextLine();
                System.out.print("Enter amount of days to the sale\n ");
                int amount_of_days = scanner.nextInt();
                System.out.print("Enter percentage of sale\n");
                int percentage = scanner.nextInt();
                JsonObject json13 = new JsonObject();
                json13.addProperty("days", amount_of_days);
                json13.addProperty("percentage", percentage);
                json13.addProperty("categories", categories);
                jcontroller.set_sale_categories(json13);
                print_menu_manager();
            }
            case 14 -> {
                JsonObject json14 = new JsonObject();
                scanner.nextLine();
                System.out.print("enter category of the area\n");
                String category = scanner.next();
                json14.addProperty("category",category);
                jcontroller.add_new_area(json14);
                print_menu_manager();
            }
            case 15 -> {
                JsonObject json15 = new JsonObject();
                scanner.nextLine();
                System.out.print("enter category of the area\n");
                String area_description = scanner.next();
                System.out.print("enter sub-category of shelf\n");
                String shelf_description = scanner.next();
                json15.addProperty("area_description",area_description);
                json15.addProperty("shelf_description",shelf_description);
                jcontroller.add_shelf_to_area(json15);
                print_menu_manager();
            }
            case 16 ->{
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                JsonObject json16 = new JsonObject();
                json16.addProperty("type_id", type_id);
                jcontroller.get_supplier_sale(json16);
                print_menu_manager();
            }
            case 17 ->{
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                System.out.print("enter new supplier sale amount \n");
                int new_supplier_sale = scanner.nextInt();
                JsonObject json17 = new JsonObject();
                json17.addProperty("type_id", type_id);
                json17.addProperty("new_supplier_sale", new_supplier_sale);
                jcontroller.set_supplier_sale(json17);
                print_menu_manager();
            }
            case 18 ->{
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                System.out.print("enter new selling price \n");
                float new_selling_price = scanner.nextFloat();
                JsonObject json18 = new JsonObject();
                json18.addProperty("type_id", type_id);
                json18.addProperty("new_selling_price", new_selling_price);
                jcontroller.set_new_selling_price(json18);
                print_menu_manager();
            }
            case 19 ->{
                System.out.print("enter item type \n");
                int type_id = scanner.nextInt();
                System.out.print("enter new cost price \n");
                float new_cost_price = scanner.nextFloat();
                JsonObject json19 = new JsonObject();
                json19.addProperty("type_id", type_id);
                json19.addProperty("new_cost_price", new_cost_price);
                jcontroller.set_new_cost_price(json19);
                print_menu_manager();
            }
            case 20 ->{
                return;
            }
            default -> {
                System.out.print("invalid input, please try again\n");
                print_menu_manager();
            }
        }
    }

    public void print_menu_storekeeper(){
        System.out.print("Hello, please choose one option from the following:\n");
        System.out.print("1.Add item to supply system\n");
        System.out.print("2.Exit \n");
        get_answer_storekeeper();
    }

    public void get_answer_storekeeper(){
        Scanner scanner = new Scanner(System.in);
        int user_input = scanner.nextInt();
        switch (user_input) {
            case 1 -> {
                System.out.print("enter type id \n");
                int type_id = scanner.nextInt();
                System.out.print("enter item's cost_price\n");
                int cost_price = scanner.nextInt();
                scanner.nextLine();
                System.out.print("enter item's producer \n");
                String producer = scanner.next();
                scanner.nextLine();
                System.out.print("enter item's category \n");
                String category = scanner.next();
                scanner.nextLine();
                System.out.print("enter item's sub-category \n");
                String sub_category = scanner.next();
                scanner.nextLine();
                System.out.print("enter item's size \n");
                String size = scanner.next();
                scanner.nextLine();
                System.out.print("enter item's expiration date e.g: 12.12.2024 \n");
                String expiration_date = scanner.next();
                scanner.nextLine();
                System.out.print("enter item's creation date e.g: 12.12.2024\n");
                String creation_date = scanner.next();
                scanner.nextLine();
                System.out.print("enter item type supplier sale\n");
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
                jcontroller.add_item_shop(json);
                print_menu_storekeeper();
            }
            case 2 -> {
                return;
            }
            default -> {
                System.out.print("invalid input, please try again\n");
                print_menu_storekeeper();
            }
        }
    }
}
