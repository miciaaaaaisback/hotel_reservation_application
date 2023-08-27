import api.AdminMenu;
import api.HotelResource;
import api.MainMenu;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HotelResource hotelResource = new HotelResource();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        initializeSystem(hotelResource);

        while (!exit) {
            MainMenu.displayMenu();
            int choice = MainMenu.getUserChoice();

            switch (choice) {
                case 1:
                    // Ricerca e prenotazione di una stanza
                    handleRoomReservation();
                    break;
                case 2:
                    // Visualizzazione delle prenotazioni dell'utente
                    handleUserReservations();
                    break;
                case 3:
                    // Creazione di un account
                    handleAccountCreation();
                    break;
                case 4:
                    // Accesso all'area amministrativa
                    handleAdminMenu();
                    break;
                case 5:
                    // Uscita dal programma
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }

        System.out.println("Thank you for using the Hotel Reservation System!");
    }

    private static void initializeSystem(HotelResource hotelResource) {

        Room room1 = new Room("101", 100.0, RoomType.SINGLE);
        Room room2 = new Room("102", 150.0, RoomType.DOUBLE);

        hotelResource.createACustomer("Mario", "Rossi", "mario@gmail.com");
        hotelResource.createACustomer("Arturo", "Gatti", "arturo@gatti.com");

        hotelResource.getRoom("101").isFree();
    }

    private static void handleRoomReservation() {

    }

    private static void handleUserReservations() {

    }

    private static void handleAccountCreation() {

    }

    private static void handleAdminMenu() {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            AdminMenu.displayAdminMenu();
            int adminChoice = AdminMenu.getAdminChoice();

            switch (adminChoice) {
                case 1:
                    // Visualizzazione di tutti i clienti
                    displayAllCustomers();
                    break;
                case 2:
                    // Visualizzazione di tutte le camere
                    displayAllRooms();
                    break;
                case 3:
                    // Visualizzazione di tutte le prenotazioni
                    displayAllReservations();
                    break;
                case 4:
                    // Aggiunta di una stanza
                    addRoom();
                    break;
                case 5:
                    // Ritorno al menu principale
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private static void displayAllCustomers() {

    }

    private static void displayAllRooms() {

    }

    private static void displayAllReservations() {

    }

    private static void addRoom() {

    }
}
