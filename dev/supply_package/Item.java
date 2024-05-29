package supply_package;
import java.util.Date;

public class Item {
    private static int cur_item_id;
    private int item_id;
    private String discount_description;
    private String category;
    private float cost_price;
    private float selling_price;   // how do we calculate the selling price???
    private Date expiration_date; // ???????????????
    private Date creation_date;
    private String faulty_status; // empty string if item is fine,else item is faulty

    public Item(Date creation_date, Date expiration_date, float selling_price, float cost_price, String category, String discount_description) {
        this.item_id = cur_item_id;
        this.creation_date = creation_date;
        this.expiration_date = expiration_date; // ???????????
        this.selling_price = selling_price; // ????????????
        this.cost_price = cost_price;
        this.category = category;
        this.discount_description = discount_description;
        cur_item_id++;
    }

    public String getFaulty_status() {
        return faulty_status;
    }

    public void setFaulty_status(String faulty_status) { // the workers module is calling this function and thus determining the string of faulty status
        this.faulty_status = faulty_status; // ???
    }

    public String getDiscount_description() {
        return discount_description;
    }

    public void setDiscount_description(String discount_description) {
        this.discount_description = discount_description;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public float getCost_price() {
        return cost_price;
    }

    public void setCost_price(float cost_price) {
        this.cost_price = cost_price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(float selling_price) {
        this.selling_price = selling_price;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
}