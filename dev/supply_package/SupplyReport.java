package supply_package;

import java.util.Date;

public class SupplyReport extends Report {
    private int counter;
    SupplyReport(Date creation_date) {
        super(creation_date);
        this.report_info += creation_date + "\n";
    }

    public void add_item_type(Item_type item_type) {
        String num_of_type = Integer.toString(item_type.get_total_amount());
        this.report_info += item_type.getCategory() + item_type.getSub_category() + item_type.getSize()
                + " has: " + num_of_type + "units.\n";
    }


}
