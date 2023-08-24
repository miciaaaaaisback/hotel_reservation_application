import model.Customer;

public class Tester {
    public static void main(String[] args) {

        try {
            Customer customer = new Customer("First", "Second", "j@domain.com");
            System.out.println(customer);
        } catch(IllegalArgumentException ex) {
            System.out.println("Test fail");
        }

        try {
            Customer customer2 = new Customer("First", "Second", "email");
        } catch(IllegalArgumentException ex) {
            System.out.println("Test success");
        }
    }
}
