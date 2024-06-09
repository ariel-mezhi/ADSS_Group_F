package supply_domain;
import java.util.Date;

public class FaultyReport extends Report {
    FaultyReport(Date creation_date) {
        super(creation_date);
        this.report_info += "Faulty report:\n";
    }

    public void add_to_report(Item item,String faulty_reason ) {
        String str1 = String.valueOf(line_counter);
        String category = item.getType().getCategory() + " ,";
        String sub_category = item.getType().getSub_category() + " ,";
        String size = item.getType().getSize();
        String serial_num = String.valueOf(item.getSerialNum()) + " faulty reason: " + faulty_reason + "\n";
        this.report_info += str1 + ". item: " + category + sub_category + size + ", Serial number: " + serial_num;
        line_counter++;
    }
}
