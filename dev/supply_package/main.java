package supply_package;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args){
        Supply supply = new Supply();
        System_user system_user = new System_user(supply);
        String filePath = "\"C:\\Users\\Yuval Ellins\\Desktop\\projects\\ADSS_Group_F\\dev\\supply_package\\configuration_file_supply\"";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read lines in a loop
            while ((line = br.readLine()) != null) {
                // what we suppose to process
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Hello, welcome to supply system, please identify by entering number of the option:\n");
        System.out.print("1.Manager\n");
        System.out.print("2.Worker\n");
        System.out.print("3.Cashier\n");
        Scanner scanner = new Scanner(System.in);
        int user_input = scanner.nextInt();
        switch (user_input) {
            case 1 -> {
                System.out.print("enter password for manager:\n");
                user_input = scanner.nextInt();
                if (user_input == sisma) {
                    system_user.print_menu_manager();
                    system_user.get_answer_manager();
                }
            }
            case 2 -> {
                System.out.print("enter password for manager:\n");
                user_input = scanner.nextInt();
                if (user_input == sisma) {
                    system_user.print_menu_worker();
                    system_user.get_answer_worker();
                }
            }
            case 3 -> {
                System.out.print("enter password for manager:\n");
                user_input = scanner.nextInt();
                if (user_input == sisma) {
                    system_user.print_menu_cashier();
                    system_user.get_answer_cashier();
                }
            }
        }
    }
}
