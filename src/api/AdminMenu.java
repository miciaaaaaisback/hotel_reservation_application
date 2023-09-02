package api;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu {
    public void displayAdminMenu() {
        System.out.println("Admin Menu");
        System.out.println("0. Populate the system with test data");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to main menu");
        System.out.print("Please select an option (0-5): ");
    }

    public int getAdminChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                choice = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.err.println("Input non valido. Inserisci un numero intero valido:");
                scanner.nextLine();
            }
        }
        return choice;
    }

}
