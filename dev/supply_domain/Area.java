package supply_domain;
import java.util.ArrayList;
import java.util.List;

public class Area {
    private List<Shelf> shelves_in_Area;
    private int amount_of_shelves;
    private final String Area_description;

    public Area(String area_description) {
        amount_of_shelves = 0;
        this.shelves_in_Area = new ArrayList<Shelf>();
        this.Area_description = area_description;
    }

    public void add_shelf(String shelf_description){
        shelves_in_Area.add(new Shelf(shelf_description));
        amount_of_shelves++;
    }

    public boolean remove_shelf(int i){
        if(amount_of_shelves == 0)
            return false;
        shelves_in_Area.remove(i);
        amount_of_shelves --;
        return true;
    }

    public Item get_items_in_area(int serialNum){
        for (int i = 0; i < amount_of_shelves; i++) {
            Item cur_item = shelves_in_Area.get(i).getItem(serialNum); // calling getItem of shelf to find item with
            // same serial number, if finds, returns cur_item
            if (cur_item != null)
                return cur_item; // found cur_item in the area
        }
        return null;
    }

    public int getAmount_of_shelves(){
        return amount_of_shelves;
    }

    public String getArea_description(){
        return Area_description;
    }

    public boolean add_to_area(Item item, String location_path){
        location_path += this.Area_description;
        location_path += " ";
        boolean was_added = false;
        for (int i = 0; i < amount_of_shelves; i++) {
            if(item.getType().getSub_category().equals(shelves_in_Area.get(i).getShelf_sub_category())){ // if shelf sub category has a designated shelf
                was_added = shelves_in_Area.get(i).add_to_shelf(item,location_path);
            }
        }
        return was_added; // true if only added to shelves in this area(can find area with no shelves or shelf with no space)
    }
}
