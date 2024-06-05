package supply_package;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Area> shop_area;
    private int max_areas;
    private int amount_of_areas;

    public Shop() {
        this.max_areas = 5;
        shop_area = new ArrayList<Area>();

    }

    public int getMax_areas() {
        return max_areas;
    }

    public void setMax_areas(int max_areas) {
        this.max_areas = max_areas;
    }

    public boolean add_area(String description){
        if(amount_of_areas==max_areas)
            return false;
        shop_area.add(new Area(description));
        amount_of_areas++;
        return true;
    }

    public boolean remove_area(int i){
        if(amount_of_areas==0)
            return false;
        shop_area.remove(i);
        amount_of_areas--;
        return true;
    }

    public Item getItem(int serialNum){
        for (int i = 0; i < amount_of_areas; i++) {
            Item cur_item = shop_area.get(i).get_items_in_area(serialNum);
            if (cur_item != null)
                return cur_item;
        }
        return null;
    }

    public boolean add_to_shop(Item new_item){
        String location_path = "";
        boolean added_to_area = false;
        boolean found_related_area = false;
        for (int i = 0; i < amount_of_areas; i++) {
            if(new_item.getType().getCategory().equals(shop_area.get(i).getArea_description())){ // if item category has a designated area
                found_related_area = true;
                added_to_area = shop_area.get(i).add_to_area(new_item,location_path);
                break;
            }
        }
        // checked all areas and didnt find any matched area or failed to add to area
        if(found_related_area && !added_to_area){
            return false;
        }
        else if (found_related_area && added_to_area){
            return true;
        }
        else{ // didnt find any matched areas
            if(amount_of_areas < max_areas){ // check if an area can be created
                Area new_area = new Area(new_item.getType().getCategory());
                amount_of_areas++;
                shop_area.add(new_area);
                return new_area.add_to_area(new_item,location_path);
            }
            else{ // cant add anymore areas
                return false;

        }
        }
    }
}
