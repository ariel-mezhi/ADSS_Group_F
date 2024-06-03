package supply_package;

import java.util.List;

public class Category {
    private String category_name;
    private List<Item_type> itemTypes;

    public Category(List<Item_type> itemTypes) {
        this.itemTypes = itemTypes;
    }
}
