package supply_package;

import java.util.Date;

public abstract class Report {
    private final Date creation_date;
    protected String report_info;
    protected int line_counter;


    Report(Date creation_date){
        this.line_counter = 1;
        this.creation_date = creation_date;
        report_info = creation_date.toString() + "\n";
    }

    public void show(){
        System.out.print(report_info);
    }

}
