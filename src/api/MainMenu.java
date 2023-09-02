package api;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    public void displayMenu() {
        System.out.println("Welcome to the Hotel Reservation System!");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
    }

    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                choice = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.err.println("Input non valido. Inserisci un numero intero valido:");
                scanner.next();
            }
        }
        return choice;
    }
}


