package api;

import java.util.Scanner;

public class AdminMenu {
    public void displayAdminMenu() {
        System.out.println("Admin Menu");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to main menu");
        System.out.print("Please select an option (1-5): ");
    }

    public int getAdminChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

}
