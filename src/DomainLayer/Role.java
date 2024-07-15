package DomainLayer;
import DataAccessLayer.RoleDAO;

import java.sql.SQLException;
import java.util.ArrayList;
public class Role {
    private ArrayList<String> roles;
    private static Role instance;
    private InitSystem initSystem;
    public RoleDAO roleDAO;

    private Role() throws SQLException {
        initSystem = InitSystem.getInstance();
        roles = initSystem.loadRoles();
        roleDAO = new RoleDAO();
    }


    public static Role getInstance() throws SQLException {
        if (instance == null){
            instance = new Role();
        }
        return instance;
    }

    public ArrayList<String> getRoles() { return this.roles; }

    public void setRoles(ArrayList<String> validRoles) {
        this.roles = validRoles;
    }

    public void addRole(String role){
        if (!this.roles.contains(role)){
            this.roles.add(role);
        }
    }

    public ArrayList<String> getRegularRoles() {
        ArrayList<String> arr = new ArrayList<>();
        for (String r : roles){
            if (!r.contains("driver")){
                arr.add(r);
            }
        }
        return arr;
    }

    public boolean containsRole(String role){
        return this.roles.contains(role);
    }

    public void deleteRole(String role){
        if (containsRole(role)){
            roles.remove(role);
        }
    }

    @Override
    public String toString() {
        String st = "";
        for (String role : roles){
            st = st + " " + role;
        }
        return st;
    }
}
