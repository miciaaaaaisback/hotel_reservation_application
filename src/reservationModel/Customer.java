package reservationModel;

import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        String emailRegex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (pattern.matcher(email).matches() != true){
            throw new IllegalArgumentException("The email is not valid");
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

/*    public static void main(String[] args) {
        try {
            // Creating a new Customer object with valid email
            Customer customer1 = new Customer("Francesca", "Gatti", "francesca@gmail.com");
            System.out.println(customer1);

            // Creating a new Customer object with an invalid email (will throw an exception)
            Customer customer2 = new Customer("Jane", "Smith", "jane.smith@gmail");
            System.out.println(customer2);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }*/

}
