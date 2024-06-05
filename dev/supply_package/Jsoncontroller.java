package supply_package;

import com.google.gson.JsonObject;

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
        if(serialNum == -1)
            return;
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
        if(serial_Num == -1)
            return;
        supply.removeItem(serial_Num);
    }

    public void pass_days(int days){
        supply.pass_days(days);
    }

    public void set_sale(JsonObject json){
        int days = json.get("days").getAsInt();
        int precentage = json.get("precentage").getAsInt();
        int item_id = json.get("type_id").getAsInt();
        supply.set_sale(days,item_id,precentage);
    }
}
