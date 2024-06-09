package supply_domain;
public class Item_type {
    private final int type_id;
    private final String producer;
    private final String category;
    private final String sub_category;
    private final String size;
    private int amount_on_shelves;
    private int amount_in_storage;
    private float selling_price;
    private float cost_price;
    private int minimal_amount;
    private int percentage_sale;
    private int amount_of_days_left_sale;
    private int supplier_sale;

    public Item_type(int type_id, String producer, String category, String sub_category
            , String size,float cost_price,int supplier_sale) {
        this.type_id = type_id;
        this.producer = producer;
        this.category = category;
        this.sub_category = sub_category;
        this.size = size;
        this.amount_on_shelves = 0;
        this.amount_in_storage = 0;
        this.minimal_amount = 0;
        this.cost_price = cost_price;
        this.selling_price = cost_price; // there isn't any provided selling price thus it will be set as default cost price
        percentage_sale = 0;
        amount_of_days_left_sale = 0;
        this.supplier_sale = supplier_sale;
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
        return producer + "\n";
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

    public float getSelling_price() {
        return selling_price - (selling_price* percentage_sale)/100;
    }

    public void setSelling_price(float selling_price) {
        this.selling_price = selling_price;
    }

    public float getCost_price() {
        return cost_price - (cost_price*supplier_sale)/100;
    }

    public void setCost_price(float cost_price) {
        this.cost_price = cost_price;
    }

    public int get_total_amount(){
        return amount_in_storage + amount_on_shelves;
    }

    public int getPercentage_sale() {
        return percentage_sale;
    }

    public void setPercentage_sale(int percentage_sale) {
        this.percentage_sale = percentage_sale;
    }

    public int getAmount_of_days_left_sale() {
        return amount_of_days_left_sale;
    }

    public void setAmount_of_days_left_sale(int amount_of_days_left_sale) {
        this.amount_of_days_left_sale = amount_of_days_left_sale;
    }

    public int get_supplier_sale(){
        return supplier_sale;
    }

    public void set_supplier_sale(int new_supplier_sale){
        this.supplier_sale = new_supplier_sale;
    }
}

