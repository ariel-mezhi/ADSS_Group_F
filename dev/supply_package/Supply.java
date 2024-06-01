package supply_package;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Supply {
    private Storage storage;
    private Shop shop;
    private List<Item_type> itemTypes;

    public Supply(){
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
        if(item.getLocation() != "storage"){ // item in shop
            shop.
        }
        else{ // item in storage
            storage.removeItem(serialNum);
        }
    }

    public void faulty_item_found(int serialNum){
        Item item = getItem(serialNum);
        if (item == null){
            return;
        }
        boolean was_deleted;
        if (item.getLocation() == "storage" ){
            was_deleted= storage.removeItem(serialNum);
            if(was_deleted)
                item.kamot--; //TODO add attribute
        }
        else {
            was_deleted = shop.removeItem(serialNum);
        }
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




}
