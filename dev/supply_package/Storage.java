package supply_package;

import java.util.ArrayList;
import java.util.List;
public class Storage {
    private List<Item> storageitems;

    public Storage(){
        storageitems = new ArrayList<Item>();
    }

    public boolean removeItem(int serialnum){
        for (int i = 0; i < storageitems.size(); i++) {
            Item item = storageitems.get(i);
            if(item.makat == serialnum) {
                storageitems.remove(item);
                return true;
            }
        }
        return false;
    }

    public Item getItem(int serialnum){
        for (int i = 0; i < storageitems.size(); i++) {
            Item item = storageitems.get(i);
            if(item.makat == serialnum)
                return item;
        }
        return null;
    }

    public void insertItem(Item item){
        storageitems.add(item);
    }
}
