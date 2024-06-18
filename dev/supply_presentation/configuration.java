package supply_presentation;

import supply_domain.Supply;

import java.util.Calendar;
import java.util.Date;

public class configuration { // we assumed that the configuration can be managed by class with static method
    public static void configure(Supply supply){
        supply.add_area_to_shop("dairy");
        supply.add_shelf_to_area("milk","dairy");
        Date date1 = new Date(2024, Calendar.JANUARY,1);
        Date date2 = new Date(2024, Calendar.JANUARY,10);
        supply.add_newItem(10,"tara","dairy","milk","100ml",5
                ,date1,date2,0,3);
        supply.add_newItem(11,"tara","dairy","milk","200ml",7
                ,date1,date2,0,1);
        supply.add_newItem(12,"tara","dairy","cottage","200ml",7
                ,date1,date2,0,1);
        supply.add_newItem(20,"neto","meat","beef","200g",50
                ,date1,date2,0,2);
        supply.add_newItem(21,"shufersal","meat","beef","200g",55
                ,date1,date2,0,1);
        supply.add_newItem(30,"teva","shampoo","men shampoo","100ml",10
                ,date1,date2,0,1);
        supply.add_newItem(31,"teva","shampoo","women shampoo","100ml",12
                ,date1,date2,0,1);
        // configuration is set to have only area of dairy and shelf of milk, all other items will be set to the storage as default(assumption of us)
    }
}
