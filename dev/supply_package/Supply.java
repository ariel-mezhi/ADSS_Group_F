package supply_package;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Supply {
    private Storage storage;
    private Shop shop;
    private List<Item_type> itemTypes;
    private FaultyReport faultyReport;
    private Date cur_date;

    public Supply(){
        this.cur_date = new Date(2000, Calendar.JANUARY,25);
        this.faultyReport = new FaultyReport(cur_date);
        this.storage = new Storage();
        this.shop = new Shop();
        itemTypes = new ArrayList<Item_type>();

    }

    public Item getItem(int serialNum){ //TODO: return exception
        Item item1 = shop.getItem(serialNum);
        Item item2 = storage.getItem(serialNum);
        if (item1 == null && item2 == null){
            return null;
        }
        else if (item1 == null){
            return item2;
        }
        else return item1;
    }

    public boolean removeItem(int serialNum){ //TODO: add to faulty report;
        Item item = getItem(serialNum);
        if(item == null){
            return false;
        }
        Item_type this_item_type = item.getType();
        if(item.getLocation() != "storage"){ // item in shop
            item.getShelf_of_item().remove_from_shelf(item);
            this_item_type.setAmount_on_shelves(this_item_type.getAmount_on_shelves()-1);
        }
        else{ // item in storage
            storage.removeItem(item);
            this_item_type.setAmount_in_storage(this_item_type.getAmount_in_storage()-1);
        }
        if(this_item_type.get_total_amount() <= this_item_type.getMinimal_amount())
            alert_low_quantity_item_type(this_item_type);
        // maybe send to def report if def report is required
        return true;
    }

    public void addnewItem(int type_id, String producer, String category, String sub_category
            , String size, float cost_price, Date exprdate, Date creation_date){//TODO add to requirement section that shop is getting filled first and then storage
        Item_type type=null;
        for (int i = 0; i < itemTypes.size(); i++) {
            if(type_id == itemTypes.get(i).getType_id()){
                type = itemTypes.get(i);
            }
        }
        if(type == null){
            type = new Item_type(type_id, producer, category, sub_category, size, cost_price);
        }
        Item new_item = new Item(type,exprdate, creation_date);
        boolean added_to_shop = shop.add_to_shop(new_item);
        if(added_to_shop){ // TODO equal on strings
            type.setAmount_on_shelves(type.getAmount_on_shelves()+1);
        }
        else{
            storage.addItem(new_item);
            type.setAmount_in_storage(type.getAmount_in_storage()+1);
            new_item.setLocation("storage");
        }
    }

    public void set_minimal_amount(Item_type item_type,int minimal_amount){
        item_type.setMinimal_amount(minimal_amount);
    }


    public void alert_low_quantity_item_type(Item_type item_type){
        // connect jason to relevant information and send it to user interface
    }

    public void set_faulty_item(int serialNum, String faulty_description){
        Item item = getItem(serialNum);
        removeItem(serialNum);
        add_to_faulty_report(item,faulty_description);
    }

    public void add_to_faulty_report(Item item,String faulty_description){
        faultyReport.add_to_report(item,faulty_description);
    }

    public void send_faulty_report(){
        // sending to jason the report object, add cur_date to jason
        this.faultyReport = new FaultyReport(cur_date);
    }


    public void pass_days(int amount_of_days){
        if(amount_of_days <1)
            return;
        int miliseconds = 1000*60*60*24*amount_of_days;
        //??
    }
    public void  supply_report_initiator(){

    }
    public void supplyReport(String category){
        for (int i = 0; i < itemTypes.size(); i++) {
            Item_type item_type = itemTypes.get(i);
            String cur_item_category = item_type.getCategory();
            boolean found = category.contains(cur_item_category);
            if(found){
                //TODO add to report
        }

        }
    }


    //public void add_to_def_report(Item item){
    //    Item_type item_type = item.getType();
    //    if(item_type.get_total_amount() <= item_type.getMinimal_amount()){
    // add to def report
    // tase atraa al hamozar
    //    }
    //  }
}
