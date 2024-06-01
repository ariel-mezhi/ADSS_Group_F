package supply_package;
import java.util.Date;

public class Item {
    private final int serialNum; // like "makat"
    private Item_type type; // maybe change implementaion
    private String location;
    private static int serialNumgenerator;

    private final Date exp_date;
    private final Date creation_date;
    // private boolean faulty;

    public Item(Item_type type, Date exp, Date creation) {
        this.serialNum = serialNumgenerator;
        this.location = "";
        this.type = type;
        this.creation_date = creation;
        this.exp_date = exp;
        serialNumgenerator++;
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
