package model;

// I have already tested in my previous classes..
public class Tester {
    public static void main(String[] args) {

        try {
            Customer customer = new Customer("Andrea", "Lamanna", "andre@gmail.com");
            System.out.println(customer);
            Customer customer2 = new Customer("Francesca", "Gatti", "franci@gmail");
            System.out.println(customer2);
        }
        catch(IllegalArgumentException ex){
            System.out.println(ex.getLocalizedMessage());
        }

    }
}

