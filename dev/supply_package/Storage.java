package supply_package;
import java.util.ArrayList;
import java.util.List;
public class Storage {
    private List<Item> storageitems;

    public Storage(){
        storageitems = new ArrayList<Item>();
    }

    public boolean removeItem(Item item){
        if(item == null){
            return false;
        }
        else{
            storageitems.remove(item);
            return true;
        }
    }

    public Item getItem(int serialnum){
        for (int i = 0; i < storageitems.size(); i++) {
            Item item = storageitems.get(i);
            if(item.getSerialNum() == serialnum)
                return item;
        }
        return null;
    }

    public void addItem(Item item){
        storageitems.add(item);
    }
}
