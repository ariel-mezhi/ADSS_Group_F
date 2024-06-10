package DomainLayer;
import java.util.ArrayList;
public class Role {
    private ArrayList<String> roles;
    private static Role instance;

    private Role(){
        roles = new ArrayList<>();
        initRoles();
    }
    private void initRoles(){
        String[] _roles = {"shiftManager", "cashier", "storekeeper"};
        for (String r : _roles){
            this.roles.add(r);
        }
    }

    public static Role getInstance(){
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
