import api.AdminMenu;
import api.AdminResource;
import api.HotelResource;
import api.MainMenu;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        AdminResource adminResource = AdminResource.getInstance();
        HotelResource hotelResource = HotelResource.getInstance();
        AdminMenu adminMenu = new AdminMenu();
        MainMenu mainMenu = new MainMenu();
        Scanner scanner = new Scanner(System.in);

        initializeSystem(adminResource, hotelResource);

        while (true) {
            mainMenu.displayMenu();
            int choice = mainMenu.getUserChoice();

            switch (choice) {
                case 1:
                    // Ricerca e prenotazione di una stanza
                    System.out.println("Write your email");
                    String email = scanner.next().trim();
                    try {
                        Customer.checkEmail(email);
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Invalid email.");
                        break;
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date checkIn;
                    Date checkOut;
                    try {
                        checkIn = sdf.parse(scanner.next().trim());
                        checkOut = sdf.parse(scanner.next().trim());
                    } catch (ParseException ex) {
                        System.err.println("Date format not correct.");
                        break;
                    }
                    handleRoomReservation(hotelResource, email, checkIn, checkOut);
                    break;
                case 2:
                    // Visualizzazione delle prenotazioni dell'utente
                    System.out.println("Write your email");
                    email = scanner.next().trim();
                    try {
                        Customer.checkEmail(email);
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Invalid email.");
                        break;
                    }
                    handleUserReservations(hotelResource, email);

                    break;
                case 3:
                    // Creazione di un account
                    handleAccountCreation();
                    break;
                case 4:
                    // Accesso all'area amministrativa
                    handleAdminMenu(adminMenu);
                    break;
                case 5:
                    // Uscita dal programma
                    System.out.println("Thank you for using the Hotel Reservation System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }


    }

    private static void initializeSystem(AdminResource adminResource, HotelResource hotelResource) {

        Room room1 = new Room("101", 100.0, RoomType.SINGLE);
        Room room2 = new Room("102", 150.0, RoomType.DOUBLE);
        Room room3 = new FreeRoom("103", RoomType.SINGLE);

        List<IRoom> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        adminResource.addRoom(roomList);

        hotelResource.createACustomer("Mario", "Rossi", "mario@gmail.com");
        hotelResource.createACustomer("Arturo", "Gatti", "arturo@gatti.com");

    }

    private static void handleRoomReservation(
            HotelResource hotelResource,
            String email,
            Date checkInDate,
            Date checkOutDate) {
        Collection<IRoom> roomsFound = hotelResource.findARoom(checkInDate, checkOutDate);
        try {
            Reservation reservedRoom = hotelResource.bookARoom(
                    email,
                    roomsFound.stream().findFirst().get(),
                    checkInDate,
                    checkOutDate);
        } catch (Exception ex) {
            System.out.println("No rooms avaiable");
        }
    }

    private static void handleUserReservations(HotelResource hotelResource, String email) {
        for (Reservation reservation : hotelResource.getCustomersReservations(email)) {
            System.out.println(reservation);
        }
    }

    private static void handleAccountCreation() {

    }

    private static void handleAdminMenu(AdminMenu adminMenu) {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            adminMenu.displayAdminMenu();
            int adminChoice = adminMenu.getAdminChoice();

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
