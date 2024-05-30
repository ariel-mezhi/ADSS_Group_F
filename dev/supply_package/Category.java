package supply_package;

import java.util.List;

public class Category {
    private String category_name;
    private List<Product_type> productTypes;

    public Category(List<Product_type> productTypes) {
        this.productTypes = productTypes;
    }
}
