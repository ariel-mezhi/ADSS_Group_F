package supply_Data;

import supply_domain.Item_type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemTypeDaoImpl implements TypeDao{
    private Connection conn;
    private List<Item_type> existing_Types;

    public ItemTypeDaoImpl() throws SQLException{
        conn = DataBase.connect();
        this.existing_Types = new ArrayList<>();
    }
    @Override
    public Item_type read(int type_id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM types WHERE type_id = ?");
        stmt.setInt(1, type_id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String producer = rs.getString("producer");
            String category = rs.getString("category");
            String sub_category = rs.getString("sub_category");
            String size = rs.getString("size");
            int amount_on_shelves = rs.getInt("amount_on_shelves");
            int amount_in_storage = rs.getInt("amount_in_storage");
            float selling_price = rs.getFloat("selling_price");
            float cost_price = rs.getFloat("cost_price");
            int minimal_amount = rs.getInt("minimal_amount");
            int percentage_sale = rs.getInt("percentage_sale");
            int amount_of_days_left_sale = rs.getInt("amount_of_days_left_sale");
            int supplier_sale = rs.getInt("supplier_sale");
            for(Item_type type: this.existing_Types){ // search in runtime types if current type already exists
                if(type.getType_id() == type_id && type.getProducer().equals(producer) && type.getCategory().equals(category) && type.getSize().equals(size))
                    return type; // return matched type, type already exists
            }
            Item_type new_type =  new Item_type(type_id,producer,category,sub_category,size
                    ,amount_on_shelves,amount_in_storage,selling_price,cost_price,minimal_amount,percentage_sale,amount_of_days_left_sale,supplier_sale);
            this.existing_Types.add(new_type); // type wasn't exist therefor add to existing types
            return new_type;

        } else {
            return null;
        }
    }

    @Override
    public void create(Item_type item_type) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO types (type_id,producer,category,sub_category,size,amount_on_shelves,amount_in_storage,selling_price," +
                "cost_price,minimal_amount,percentage_sale,amount_of_days_left_sale,supplier_sale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, item_type.getType_id());
        stmt.setString(2, item_type.getProducer());
        stmt.setString(3, item_type.getCategory());
        stmt.setString(4, item_type.getSub_category());
        stmt.setString(5, item_type.getSize());
        stmt.setInt(6, item_type.getAmount_on_shelves());
        stmt.setInt(7, item_type.getAmount_in_storage());
        stmt.setFloat(8,item_type.getSelling_price());
        stmt.setFloat(9,item_type.getCost_price());
        stmt.setInt(10, item_type.getMinimal_amount());
        stmt.setInt(11, item_type.getPercentage_sale());
        stmt.setInt(12, item_type.getAmount_of_days_left_sale());
        stmt.setInt(13, item_type.get_supplier_sale());
        stmt.executeUpdate();
    }
}
