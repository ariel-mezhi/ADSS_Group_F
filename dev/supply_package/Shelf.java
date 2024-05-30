package supply_package;

import java.util.ArrayList;
import java.util.List;
public class Shelf {
    private static int shelf_id_generator;
    private List<Item> items_on_shelf;
    private final int shelf_id;
    private int amount_on_shelf;
    private int max_items;
    public Shelf() {
        this.max_items = 20;
        amount_on_shelf = 0;
        this.shelf_id = shelf_id_generator;
        items_on_shelf = new ArrayList<Item>();
        shelf_id_generator++;
    }

    public int getShelf_id() {
        return shelf_id;
    }

    public boolean add_to_shelf(Item cur_item){
        if(amount_on_shelf == max_items)
            return false;
        items_on_shelf.add(cur_item);
        amount_on_shelf++;
        return true;

    }
    public boolean remove_from_shelf(Item cur_item){
        if(amount_on_shelf == 0)
            return false;
        items_on_shelf.remove(cur_item);
        amount_on_shelf--;
        return true;
    }
    public Item get_item_index(int i){
        if(i<=items_on_shelf.size())
            return items_on_shelf.get(i);
        else return null;
    }

    public int getAmount_on_shelf(){
        return amount_on_shelf;
    }

    public void set_max_items(int new_max_items){
        max_items = new_max_items;
    }

}
