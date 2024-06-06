package supply_package;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Area> shop_area;
    private int amount_of_areas;

    public Shop() {
        shop_area = new ArrayList<Area>();
        amount_of_areas = 0;
    }

    public boolean add_area(String description){
        shop_area.add(new Area(description));
        amount_of_areas++;
        return true;
    }

    public boolean remove_area(Area area){
        if(amount_of_areas==0)
            return false;
        shop_area.remove(area);
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
        boolean was_added = false;
        for (int i = 0; i < amount_of_areas; i++) {
            if(new_item.getType().getCategory().equals(shop_area.get(i).getArea_description())){
                was_added = shop_area.get(i).add_to_area(new_item,location_path);
            }
        }
        return was_added;
    }

    public Area find_area(String area_description){
        for (Area area : shop_area) {
            if (area.getArea_description().equals(area_description))
                return area;
        }
        return null;
    }
}
