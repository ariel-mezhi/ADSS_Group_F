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

    public Area() {
        this.max_shelves = 10;
        amount_of_shelves = 0;
        this.shelves_in_Area = new ArrayList<Shelf>();
        this.area_id = area_id_generator;
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

    public Item get_items_in_area(){}

    public int getAmount_of_shelves(){
        return amount_of_shelves;
    }

    public String getArea_description(){
        return Area_description;
    }
}
