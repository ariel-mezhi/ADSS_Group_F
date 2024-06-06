package supply_package;
import com.google.gson.JsonObject;
import java.util.Date;
public class Jsoncontroller {
    private Supply supply;

    public Jsoncontroller(Supply supply){
        this.supply = supply;
    }

    public int get_serial_num (JsonObject json){
        int serialNum = json.get("serialnumber").getAsInt();
        if(this.supply.getItem(serialNum) != null )
            return serialNum;
        return -1;
    }

    public int get_item_type(JsonObject json){
        return json.get("type_id").getAsInt();
    }

    public void report_faulty_item(JsonObject json){
        int serialNum = get_serial_num(json);
        if(serialNum == -1) {
            System.out.print("item not exist\n");
            return;
        }
        String faulty_des = json.get("faulty_description").getAsString();
        this.supply.set_faulty_item(serialNum,faulty_des);
    }

    public float get_item_selling_price(JsonObject json){
        int type_id = get_item_type(json);
        Item_type type = supply.getType(type_id);
        if (type == null)
            return -1;
        return type.getSelling_price();
    }

    public String get_item_location(JsonObject json){
        int serialNum = get_serial_num(json);
        if(serialNum == -1)
            return "item not exist\n";
        return supply.getItem(serialNum).getLocation() + "\n";
    }

    public String get_item_manufacturer(JsonObject json){
        int serial_Num = get_serial_num(json);
        if(serial_Num == -1)
            return " item not exist\n";
        Item item = supply.getItem(serial_Num);
        return item.getType().getProducer();
    }

    public int get_cur_amount_type(JsonObject json){
        int type_id = get_item_type(json);
        Item_type type = supply.getType(type_id);
        if (type == null)
            return -1;
        return type.get_total_amount();
    }

    public int get_cur_amount_type_shelves(JsonObject json){
        int type_id = get_item_type(json);
        Item_type type = supply.getType(type_id);
        if (type == null)
            return -1;
        return type.getAmount_on_shelves();
    }

    public int get_cur_amount_type_storage(JsonObject json){
        int type_id = get_item_type(json);
        Item_type type = supply.getType(type_id);
        if (type == null)
            return -1;
        return type.getAmount_in_storage();
    }

    public float get_item_cost_price(JsonObject json){
        int type_id = get_item_type(json);
        Item_type type = supply.getType(type_id);
        if (type == null)
            return -1;
        return type.getCost_price();
    }

    public void create_supply_report(JsonObject json){
        String category = json.get("categories").getAsString();
        supply.supplyReport(category);
    }

    public void create_faulty_report(){
        supply.send_faulty_report();
    }

    public void remove_item(JsonObject json){
        int serial_Num = get_serial_num(json);
        if(serial_Num == -1) {
            System.out.print("item not exist");
            return;
        }
        supply.removeItem(serial_Num);
    }

    public void pass_days(int days){
        supply.pass_days(days);
    }

    public void set_sale(JsonObject json){
        int days = json.get("days").getAsInt();
        int percentage = json.get("percentage").getAsInt();
        int item_id = json.get("type_id").getAsInt();
        supply.set_sale(days,item_id,percentage);
    }

    public void add_item_shop(JsonObject json){
        int type_id = json.get("type_id").getAsInt();
        int cost_price = json.get("cost_price").getAsInt();
        String producer = json.get("producer").getAsString();
        String category = json.get("category").getAsString();
        String sub_category = json.get("sub_category").getAsString();
        String size = json.get("size").getAsString();
        String expiration_date = json.get("exp_date").getAsString();
        String creation_date = json.get("creation_date").getAsString();
        String[] dateparts = expiration_date.split("\\.");
        int year = Integer.parseInt(dateparts[2]);
        int month = Integer.parseInt(dateparts[1]);
        int day = Integer.parseInt(dateparts[0]);
        Date exp_date = new Date(year,month,day);
        dateparts = creation_date.split("\\.");
        year = Integer.parseInt(dateparts[2]);
        month = Integer.parseInt(dateparts[1]);
        day = Integer.parseInt(dateparts[0]);
        Date create_date = new Date(year,month,day);
        supply.add_newItem(type_id,producer,category,sub_category,size,cost_price,exp_date,create_date);
    }

    public void set_minimal_amount_type(JsonObject json){
        int type_id = get_item_type(json);
        int minimal_amount = json.get("minimal_amount").getAsInt();
        supply.set_minimal_amount(type_id,minimal_amount);
    }
    public void set_sale_categories(JsonObject json){
        int days = json.get("days").getAsInt();
        int percentage = json.get("percentage").getAsInt();
        String categories = json.get("categories").getAsString();
        supply.set_sale_categories(days,percentage,categories);
    }

    public void add_new_area(JsonObject json){
        String category = json.get("category").getAsString();
        supply.add_area_to_shop(category);
    }

    public void add_shelf_to_area(JsonObject json){
        String area_description = json.get("area_description").getAsString();
        String shelf_description = json.get("shelf_description").getAsString();
        supply.add_shelf_to_area(shelf_description,area_description);
    }
}
