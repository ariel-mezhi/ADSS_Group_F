package supply_Data;

import supply_domain.Item_type;
import supply_domain.Shelf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShelfDaoImpl implements ShelfDao{
    private Connection conn;
    private List<Shelf> existing_shelves;

    public ShelfDaoImpl() throws SQLException{
        conn = DataBase.connect();
        this.existing_shelves = new ArrayList<>();
    }

    @Override
    public Shelf read(String shelf_description) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM types WHERE type_id = ?");
        stmt.setString(1, shelf_description);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String shelf_sub_category = rs.getString("shelf_sub_category");
            int max_items = rs.getInt("max_items");
            for(Shelf shelf: this.existing_shelves){ // search in runtime types if current type already exists
                if(shelf.getShelf_sub_category().equals(shelf_sub_category))
                    return shelf; // return matched type, type already exists
            }
            Shelf new_shelf = new Shelf(shelf_sub_category);
            new_shelf.set_max_items(max_items);
            this.existing_shelves.add(new_shelf); // type wasn't exist therefor add to existing types
            return new_shelf;

        } else {
            return null;
        }
    }

    @Override
    public void create(Shelf shelf) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO shelves (shelf_sub_category,max_items) VALUES (?, ?)");
        stmt.setString(1, shelf.getShelf_sub_category());
        stmt.setInt(2, shelf.getMax_items());
        stmt.executeUpdate();
    }
}
