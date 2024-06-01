package supply_package;

import java.util.List;

public class Item_type {
    private final int type_id;
    private final String producer;
    private final String category;
    private final String sub_category;
    private final String size;

    private int amount_on_shelves;
    private int amount_in_storage;

    private float selling_price;
    private final float cost_price;

    private int minimal_amount;

    // discount??

    public Item_type(int type_id, String producer, String category, String sub_category
            , String size,float cost_price) {
        this.type_id = type_id;
        this.producer = producer;
        this.category = category;
        this.sub_category = sub_category;
        this.size = size;
        this.amount_on_shelves = 0;
        this.amount_in_storage = 0;
        this.minimal_amount = 0;
        this.cost_price = cost_price;
        this.selling_price = cost_price; // there isnt any provided selling price thus it will be default as cost price
    }

    public int getMinimal_amount() {
        return minimal_amount;
    }

    public void setMinimal_amount(int minimal_amount) {
        this.minimal_amount = minimal_amount;
    }

    public int getType_id() {
        return type_id;
    }


    public String getProducer() {
        return producer;
    }

    public String getCategory() {
        return category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public String getSize() {
        return size;
    }

    public int getAmount_on_shelves() {
        return amount_on_shelves;
    }

    public void setAmount_on_shelves(int amount_on_shelves) {
        this.amount_on_shelves = amount_on_shelves;
    }

    public int getAmount_in_storage() {
        return amount_in_storage;
    }

    public void setAmount_in_storage(int amount_in_storage) {
        this.amount_in_storage = amount_in_storage;
    }

    public float getSelling_pricce() {
        return selling_price;
    }

    public void setSelling_pricce(float selling_pricce) {
        this.selling_price = selling_pricce;
    }

    public float getCost_price() {
        return cost_price;
    }

    public int get_total_amount(){
        return amount_in_storage + amount_on_shelves;
    }

}
