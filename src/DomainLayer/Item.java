package DomainLayer;
import java.util.Date;

public class Item {
    private final int serialNum;
    private final Item_type type;
    private String location;
    private Shelf shelf_of_item;
    private final Date exp_date;
    private final Date creation_date;



    public Item(Item_type type, Date exp, Date creation,int Serial_num) {
        this.shelf_of_item = null;
        this.location = "";
        this.exp_date = exp;
        this.creation_date = creation;
        this.serialNum = Serial_num;
        this.type = type;
    }

    public Item(Item_type type, Date exp, Date creation,int Serial_num,String location) {
        this.shelf_of_item = null;
        this.location = location;
        this.exp_date = exp;
        this.creation_date = creation;
        this.serialNum = Serial_num;
        this.type = type;
    }



    public Shelf getShelf_of_item() {
        return shelf_of_item;
    }

    public void setShelf_of_item(Shelf shelf_of_item) {
        this.shelf_of_item = shelf_of_item;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public Item_type getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getExp_date() {
        return exp_date;
    }

    public Date getCreation_date() {
        return creation_date;
    }
}
