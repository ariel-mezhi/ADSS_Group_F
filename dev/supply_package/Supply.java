package supply_package;

import java.util.ArrayList;
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

    public void changeStatus(int serialNum){ //TODO: add to faulty report;
        Item item = getItem(serialNum);
        if (item == null){
            return;
        }
        item.setFaultyStatus();
        if (item.getLocation() == "storage" ){
            storage.removeItem(serialNum);
        }
        else {
            shop.removeItem(serialNum);
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


    public void insert_item(Item item){ //TODO add to requirement section that shop is getting filled first and then storage
        
    }




}
