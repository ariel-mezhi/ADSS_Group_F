package supply_package;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Supply {
    private Storage storage;
    private Shop shop;
    private List<Item_type> itemTypes;
    private FaultyReport faultyReport;
    private Date cur_date;
    private int days_counter_from_report;

    public Supply(Date cur_date){
        this.cur_date = cur_date;
        this.faultyReport = new FaultyReport(cur_date);
        this.storage = new Storage();
        this.shop = new Shop();
        itemTypes = new ArrayList<Item_type>();
        days_counter_from_report = 7;
    }

    public Item getItem(int serialNum){
        Item item1 = shop.getItem(serialNum);
        Item item2 = storage.getItem(serialNum);
        if (item1 == null && item2 == null)
            return null;
        else if (item1 == null)
            return item2;
        else return item1;
    }

    public void removeItem(int serialNum){
        Item item = getItem(serialNum);
        if(item == null){
            return;
        }
        Item_type this_item_type = item.getType();
        if(!item.getLocation().equals("storage")){ // item in shop
            item.getShelf_of_item().remove_from_shelf(item);
            this_item_type.setAmount_on_shelves(this_item_type.getAmount_on_shelves()-1);
        }
        else{ // item in storage
            storage.removeItem(item);
            this_item_type.setAmount_in_storage(this_item_type.getAmount_in_storage()-1);
        }
        if(this_item_type.get_total_amount() <= this_item_type.getMinimal_amount()) {
            alert_low_quantity_item_type(this_item_type);
            System.out.print("\n");
        }
    }

    public void set_minimal_amount(int item_id,int minimal_amount){
        Item_type item_type = getType(item_id);
        if(item_type == null){
            System.out.print("item type not exist\n");
            return;
        }
        if(minimal_amount < 0)
            minimal_amount = 0;
        item_type.setMinimal_amount(minimal_amount);
    }

    public void alert_low_quantity_item_type(Item_type item_type){
        String item_id_str = Integer.toString(item_type.getType_id());
        System.out.print("item id: " + item_id_str + " has low quantity and needs to be restocked\n");
    }

    public void set_faulty_item(int serialNum, String faulty_description){
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

    public void pass_days(int amount_of_days){
        if(amount_of_days <= 0)
            return;
        cur_date.setTime( (long)amount_of_days *24*60*60*1000);
        if(days_counter_from_report - amount_of_days <= 0){
            days_counter_from_report = 7;
            supplyReport("");
        }
        else{
            days_counter_from_report -= amount_of_days;
        }

        for (Item_type item_type : itemTypes) {
            if (item_type.getAmount_of_days_left_sale() > amount_of_days) {
                item_type.setAmount_of_days_left_sale(item_type.getAmount_of_days_left_sale() - amount_of_days);
            } else {
                item_type.setAmount_of_days_left_sale(0);
                item_type.setPercentage_sale(0);
            }
        }

    }

    public void supplyReport(String category){
        SupplyReport supplyReport = new SupplyReport(cur_date);
        if(category.compareTo("") == 0){
            for (Item_type item_type : itemTypes) {
                supplyReport.add_item_type(item_type); // adding any item_types
            }
        }
        else {
            for (Item_type item_type : itemTypes) {
                String cur_item_category = item_type.getCategory();
                boolean found = category.contains(cur_item_category);
                if (found) // adding categories that has the same category given in the request
                    supplyReport.add_item_type(item_type);
            }
        }
        supplyReport.show();
    }

    public Item_type getType(int type_id){
        for (Item_type itemType : itemTypes) {
            if (type_id == itemType.getType_id())
                return itemType;
        }
        return null;
    }

    public void set_sale(int days,int type_id,int percentage){
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
    }


    public void set_sale_categories(int days,int percentage,String categories){
        int type_id;
        for (Item_type item_type : itemTypes) {
            String cur_item_type_category = item_type.getCategory();
            if (categories.contains(cur_item_type_category)){ // if current category is in given categories
                type_id = item_type.getType_id();
                set_sale(days,type_id,percentage);
            }
        }
    }

    public void add_newItem(int type_id, String producer, String category, String sub_category
            , String size, float cost_price, Date exprdate, Date creation_date){//TODO add to requirement section that shop is getting filled first and then storage
        Item_type type=null;
        for (Item_type itemType : itemTypes) { // getting type of the item if exists
            if (itemType.getType_id() == type_id) {
                type = itemType;
            }
        }
        if(type == null){ // type not exist, therefore creates for the type a new item_type
            type = new Item_type(type_id, producer, category, sub_category, size, cost_price);
            this.itemTypes.add(type);
        }
        Item new_item = new Item(type,exprdate, creation_date);
        boolean added_to_shop = shop.add_to_shop(new_item);
        if(added_to_shop){
            type.setAmount_on_shelves(type.getAmount_on_shelves()+1);
        }
        else{
            storage.addItem(new_item);
            type.setAmount_in_storage(type.getAmount_in_storage()+1);
            new_item.setLocation("storage");
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

}
