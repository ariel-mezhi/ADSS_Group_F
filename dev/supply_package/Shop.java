package supply_package;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Area> shop_area;
    private final int max_areas;
    private int amount_of_areas;

    public Shop() {
        this.max_areas = 5;
        shop_area = new ArrayList<Area>();

    }

    public boolean add_area(){
        if(amount_of_areas==max_areas)
            return false;
        shop_area.add(new Area());
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

//    public list[] search(int searchNum, list[]){
    // for i in list of areas
    //



}
