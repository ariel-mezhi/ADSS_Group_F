package DomainLayer;
import org.sqlite.core.DB;
import DataAccessLayer.ItemRepositoryImpl;
import DataAccessLayer.TypeRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Supply {
    private static int serialNumgenerator;
    private final Storage storage;
    private final Shop shop;
    private List<Item_type> itemTypes;
    private FaultyReport faultyReport;
    private Date cur_date;
    private int days_counter_from_report;
    private final ItemRepositoryImpl item_repo;
    private final TypeRepositoryImpl type_repo;
    private Calendar calendar;

    public Supply(Date cur_date,Calendar calendar) throws SQLException {
        this.cur_date = cur_date;
        this.faultyReport = new FaultyReport(cur_date);
        this.storage = new Storage();
        this.shop = new Shop();
        itemTypes = new ArrayList<Item_type>();
        days_counter_from_report = 7;
        this.calendar = calendar;
        this.item_repo = new ItemRepositoryImpl();
        this.type_repo = new TypeRepositoryImpl();
    }

    public Item getItem(int serialNum) throws SQLException {
        Item item1 = shop.getItem(serialNum);
        Item item2 = storage.getItem(serialNum);
        if (item1 == null && item2 == null){
            return this.item_repo.get(serialNum);
        }
        else if (item1 == null)
            return item2;
        else return item1;
    }

    public boolean find_item(Item item) throws SQLException {
        String location = item.getLocation();
        if(location.equals("storage")){
            return this.storage.getItem(item.getSerialNum()) != null;
        }
        else{
            return this.shop.getItem(item.getSerialNum()) != null;
        }
    }


    public void removeItem(int serialNum) throws SQLException {
        Item item = getItem(serialNum);
        if(item == null)
            return;
        boolean found_in_runtime = find_item(item);
        this.item_repo.remove(item);
        Item_type this_item_type = item.getType();
        if(found_in_runtime){
            if(!item.getLocation().equals("storage")){ // item in shop
                item.getShelf_of_item().remove_from_shelf(item);
                this_item_type.setAmount_on_shelves(this_item_type.getAmount_on_shelves()-1);
            }
            else{ // item in storage
                storage.removeItem(item);
                this_item_type.setAmount_in_storage(this_item_type.getAmount_in_storage()-1);
            }

        }
        else{ // wasnt found in runtime
            if(item.getLocation().equals("storage"))
                this_item_type.setAmount_in_storage(this_item_type.getAmount_in_storage()-1);
            else
                this_item_type.setAmount_on_shelves(this_item_type.getAmount_on_shelves()-1);
        }
        if(this_item_type.get_total_amount() <= this_item_type.getMinimal_amount()) {
            alert_low_quantity_item_type(this_item_type);
            System.out.print("\n");
        }
        this.type_repo.update(this_item_type);
    }

    public void set_minimal_amount(int item_id,int minimal_amount) throws SQLException {
        Item_type item_type = getType(item_id);
        if(item_type == null){
            System.out.print("item type not exist\n");
            return;
        }
        if(minimal_amount < 0)
            minimal_amount = 0;
        item_type.setMinimal_amount(minimal_amount);
        this.type_repo.update(item_type);
    }

    public void alert_low_quantity_item_type(Item_type item_type){
        String item_id_str = Integer.toString(item_type.getType_id());
        System.out.print("item type id: " + item_id_str + " has low quantity and needs to be restocked\n");
        // at the moment we cant connect this module to supplier module so a print will be simulating an order request
        int order_amount = 2 * item_type.getMinimal_amount() - item_type.get_total_amount();
        if(order_amount == 0)
            order_amount = 1;
        send_to_order_shortage(item_type,order_amount);
    }


    public void send_to_order_shortage(Item_type item_type,int num){
        System.out.print("~Sending ORDER REQUEST(shortage) to supplier module~\n" + item_type.get_type_information());
        // can either be sent as object with all the raw data or as message to supplier module.
    }

    public void send_to_order_periodically() throws SQLException { // we assumed periodically is each week,
        // passing few days in 1 go can destroy the counter so pass day by day
        System.out.print("~Sending ORDER REQUEST(periodically) to supplier module~ \n");
        System.out.print("all item's information: \n");
        for(Item_type type : this.type_repo.getAll()){
            if(type.get_total_amount() <= type.getMinimal_amount())
                System.out.print(type.get_type_information()+ "\n");
        }
    }

    public void set_faulty_item(int serialNum, String faulty_description) throws SQLException {
        Item item = getItem(serialNum);
        if(item == null){
            System.out.print("item not exist\n");
            return;
        }
        removeItem(serialNum); // removing item cause its faulty
        faultyReport.add_to_report(item,faulty_description);
    }

    public void send_faulty_report(){
        this.faultyReport.show();
        this.faultyReport = new FaultyReport(cur_date);
    }

    public void pass_days(int amount_of_days) throws SQLException { // this function will simulate time in this module, by our perception
        if(amount_of_days <= 0)
            return;
        //System.out.print(cur_date.toString());
        calendar.add(Calendar.DAY_OF_MONTH,amount_of_days);
        cur_date = calendar.getTime();
        if(days_counter_from_report - amount_of_days <= 0){ // assume to order each week an order
            days_counter_from_report = 7;
            supplyReport("");
        }
        else if (days_counter_from_report - amount_of_days == 1) { // remaining 1 day before order
            send_to_order_periodically();
            days_counter_from_report -= amount_of_days;
        }
        else{
            days_counter_from_report -= amount_of_days;
        }

        for (Item_type item_type : this.type_repo.getAll()) {
            if (item_type.getAmount_of_days_left_sale() > amount_of_days) {
                item_type.setAmount_of_days_left_sale(item_type.getAmount_of_days_left_sale() - amount_of_days);
            } else {
                item_type.setAmount_of_days_left_sale(0);
                item_type.setPercentage_sale(0);
            }
            this.type_repo.update(item_type);
            for (Item_type item_type_runtime : itemTypes){ // if type from DB is also in runtime
                if(item_type_runtime.getType_id() == item_type.getType_id()){
                    if (item_type_runtime.getAmount_of_days_left_sale() > amount_of_days) {
                        item_type_runtime.setAmount_of_days_left_sale(item_type_runtime.getAmount_of_days_left_sale() - amount_of_days);
                    } else {
                        item_type_runtime.setAmount_of_days_left_sale(0);
                        item_type_runtime.setPercentage_sale(0);
                    }
                }
            }
        }
    }
    public void supplyReportCategory(String category,String sub_category, String size) throws SQLException {
        if(category.compareTo("") == 0) {
            supplyReport("");
            return;
        }
        boolean size_exist = true;
        if(sub_category.compareTo("") == 0) {
            supplyReport(category);
            return;
        }
        if(size.compareTo("") == 0 )
            size_exist = false;
        SupplyReport supplyReport = new SupplyReport(cur_date);
        for (Item_type item_type : this.type_repo.getAll()) {
            if (size_exist) {
                if(item_type.getCategory().equals(category)
                        && item_type.getSub_category().equals(sub_category)
                        && item_type.getSize().equals(size))
                {
                    supplyReport.add_item_type(item_type);
                }
            }
            else{
                if(item_type.getCategory().equals(category)
                        && item_type.getSub_category().equals(sub_category))
                {
                    supplyReport.add_item_type(item_type);
                }
            }
        }
        supplyReport.show();
    }

    public void supplyReport(String category) throws SQLException {
        SupplyReport supplyReport = new SupplyReport(cur_date);
        if(category.compareTo("") == 0){
            for (Item_type item_type : this.type_repo.getAll()) {
                supplyReport.add_item_type(item_type); // adding any item_types
            }
        }
        else {
            for (Item_type item_type : this.type_repo.getAll()) {
                String cur_item_category = item_type.getCategory();
                boolean found = category.contains(cur_item_category);
                    if (found) // adding categories that has the same category given in the request
                        supplyReport.add_item_type(item_type);
                }
            }
        supplyReport.show();
    }

    public Item_type getType(int type_id) throws SQLException {
        for (Item_type itemType : itemTypes) {
            if (type_id == itemType.getType_id())
                return itemType;
        }
        return this.type_repo.get(type_id); // assuming data of type on DB is correct
    }

    public void set_sale(int days,int type_id,int percentage) throws SQLException {
        if(percentage <= 0)
            return;
        if (days <= 0)
            return;
        Item_type item_type = getType(type_id);
        if(item_type == null) {
            System.out.print("item type not exist\n");
            return;
        }
        item_type.setPercentage_sale(percentage);
        item_type.setAmount_of_days_left_sale(days);
        this.type_repo.update(item_type);
    }


    public void set_sale_categories(int days,int percentage,String categories) throws SQLException {
        int type_id;
        for (Item_type item_type : this.type_repo.getAll()) {
            String cur_item_type_category = item_type.getCategory();
            if (categories.contains(cur_item_type_category)){ // if current category is in given categories
                type_id = item_type.getType_id();
                set_sale(days,type_id,percentage);
            }
        }
    }

    public void add_newItem(int type_id, String producer, String category, String sub_category
            , String size, float cost_price, Date exprdate, Date creation_date, int supplier_sale, int amount) throws SQLException { // item type won't have different values, so adding new item will have same fields as any other item in its item type
        Item_type type=null;
        for (Item_type itemType : itemTypes) { // getting type of the item if exists
            if (itemType.getType_id() == type_id) {
                type = itemType;
            }
        }
        if(type == null){ // type not exist, therefore creates for the type a new item_type
            type = this.type_repo.get(type_id);
            if(type == null) { // type wasn't found in DB
                type = new Item_type(type_id, producer, category, sub_category, size, cost_price,supplier_sale);
                this.type_repo.add(type);
            }
            this.itemTypes.add(type);
        }
        boolean added_to_shop;
        for (int i = 0; i < amount; i++) {
            Item new_item;

            while(true){ // generating serial number that doesn't exist
                if(this.item_repo.get(serialNumgenerator) == null)
                    break;
                else
                    serialNumgenerator++;
            }
            new_item = new Item(type,exprdate, creation_date,serialNumgenerator);
            added_to_shop = shop.add_to_shop(new_item);
            if(added_to_shop){
                type.setAmount_on_shelves(type.getAmount_on_shelves()+1);
            }
            else{
                storage.addItem(new_item);
                type.setAmount_in_storage(type.getAmount_in_storage()+1);
                new_item.setLocation("storage");
            }
            this.item_repo.add(new_item);
            this.type_repo.update(type);

        }

    }
    public void add_area_to_shop(String area_category){
        this.shop.add_area(area_category);
    }

    public void add_shelf_to_area(String shelf_description,String area_description){
        Area area = this.shop.find_area(area_description);
        if(area == null){
            System.out.print("area not exist\n");
            return;
        }
        area.add_shelf(shelf_description);
    }

    public void remove_area_from_shop(String area_category){
        this.shop.remove_area(this.shop.find_area(area_category));
    }

    public int get_supplier_sale(Item_type item_type){
        return item_type.get_supplier_sale();
    }

    public void set_supplier_sale(Item_type item_type,int new_supplier_sale) throws SQLException {
        if(new_supplier_sale <= 0) {
            item_type.set_supplier_sale(0);
            return;
        }
        item_type.set_supplier_sale(new_supplier_sale);
        this.type_repo.update(item_type);
    }

    public void set_selling_price(Item_type item_type,float new_selling_price) throws SQLException {
        if(new_selling_price <= 0){
            return;
        }
        item_type.setSelling_price(new_selling_price);
        this.type_repo.update(item_type);
    }

    public void set_cost_price(Item_type item_type,float new_cost_price) throws SQLException {
        if(new_cost_price <= 0){
            return;
        }
        item_type.setCost_price(new_cost_price);
        this.type_repo.update(item_type);
    }
}
