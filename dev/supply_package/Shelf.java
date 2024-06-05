package supply_package;

import java.util.ArrayList;
import java.util.List;
public class Shelf {
    private List<Item> items_on_shelf;
    private final String shelf_sub_category;
    private int amount_on_shelf;
    private int max_items;
    public Shelf(String shelf_sub_category) {
        this.max_items = 3;
        amount_on_shelf = 0;
        this.shelf_sub_category = shelf_sub_category;
        items_on_shelf = new ArrayList<Item>();
    }

    public String getShelf_sub_category() {
        return shelf_sub_category;
    }

    public boolean add_to_shelf(Item cur_item,String location_path){
        if(amount_on_shelf == max_items)
            return false;
        location_path += this.shelf_sub_category + "\n";
        items_on_shelf.add(cur_item);
        amount_on_shelf++;
        cur_item.setLocation(location_path);
        cur_item.setShelf_of_item(this);
        return true;

    }
    public void remove_from_shelf(Item cur_item){
        if(amount_on_shelf == 0)
            return;
        items_on_shelf.remove(cur_item);
        amount_on_shelf--;
    }
    public Item getItem(int serialNum){
        for (int i = 0; i < amount_on_shelf; i++) {
            if (serialNum == items_on_shelf.get(i).getSerialNum())
                return items_on_shelf.get(i);
        }
        return null;
    }

    public int getAmount_on_shelf(){
        return amount_on_shelf;
    }

    public void set_max_items(int new_max_items){
        max_items = new_max_items;
    }

}
