package supply_package;

import java.util.ArrayList;
import java.util.List;
public class Area {

    private static int area_id_generator;
    private List<Shelf> shelves_in_Area;
    private final int area_id;
    private final int max_shelves;
    private int amount_of_shelves;
    private String Area_description;

    public Area(String area_description) {
        this.max_shelves = 10;
        amount_of_shelves = 0;
        this.shelves_in_Area = new ArrayList<Shelf>();
        this.area_id = area_id_generator;
        this.Area_description = area_description;
        area_id_generator++;
    }

    public int getArea_id() {
        return area_id;
    }

    public boolean add_shelf(){
        if(amount_of_shelves == max_shelves)
            return false;
        shelves_in_Area.add(new Shelf());
        amount_of_shelves++;
        return true;
        // add check on amount of shelves
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
            Item cur_item = shelves_in_Area.get(i).getItem(serialNum);
            if (cur_item != null)
                return cur_item;
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
        boolean added_to_shelf = false;
        boolean found_related_shelf = false;
        for (int i = 0; i < amount_of_shelves; i++) {
            if(item.getType().getSub_category() == shelves_in_Area.get(i).getShelf_sub_category()){ // if shelf sub category has a designated shelf
                found_related_shelf = true;
                location_path += shelves_in_Area.get(i).getShelf_sub_category();
                added_to_shelf = shelves_in_Area.get(i).add_to_shelf(item,location_path);
                break;
            }
        }
        // checked all areas and didnt find any matched area or failed to add to area
        if(found_related_shelf && !added_to_shelf){
            return false;
        }
        else if (found_related_shelf && added_to_shelf){
            return true;
        }
        else{ // didnt find any matched shelves
            if(amount_of_shelves < max_shelves){ // check if an shelf can be created
                Shelf new_shelf = new Shelf(item.getType().getSub_category());
                amount_of_shelves++;
                shelves_in_Area.add(new_shelf);
                return new_shelf.add_to_shelf(item,location_path);
            }
            else{ // cant add anymore areas
                return false;

            }
        }
    }
}
