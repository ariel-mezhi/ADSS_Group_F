package supply_domain;
import java.util.Date;

public class SupplyReport extends Report {
    SupplyReport(Date creation_date) {
        super(creation_date);
        this.report_info += "Supply report:\n";
    }

    public void add_item_type(Item_type item_type) {
        String str1 = String.valueOf(line_counter)  + ".";
        String num_of_type = Integer.toString(item_type.get_total_amount());
        this.report_info += str1 + item_type.getCategory() + "," + item_type.getSub_category() + "," + item_type.getSize()
                + " has: " + num_of_type + " units.\n";
        line_counter++;
    }
}
