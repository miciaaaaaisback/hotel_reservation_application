import api.AdminMenu;
import api.AdminResource;
import api.HotelResource;
import api.MainMenu;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
                    System.out.println("Do you already have an account? Yes/No.");
                    String haveAnAccount = scanner.next().trim().toLowerCase();
                    Customer customer = null;
                    String email = null;
                    if (!haveAnAccount.contains("y")) {
                        System.err.println("You must have an account.");
                        break;
//                        customer = createCustomer(scanner);
                    } else {
                        System.out.println("Write yuor email.");
                        email = scanner.next().trim().toLowerCase();
                        customer = hotelResource.getCustomer(email);
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date checkIn;
                    Date checkOut;
                    try {
                        System.out.println("Write your check in date in the format dd/MM/yyyy");
                        checkIn = sdf.parse(scanner.next().trim());
                        System.out.println("Write your check out date in the format dd/MM/yyyy");
                        checkOut = sdf.parse(scanner.next().trim());
                    } catch (ParseException ex) {
                        System.err.println("Date format not correct.");
                        break;
                    }
                    handleRoomReservation(hotelResource, customer, checkIn, checkOut);
                    break;
                case 2:
                    // Visualizzazione delle prenotazioni dell'utente
                    System.out.println("Write your email");
                    email = scanner.next().trim().toLowerCase();
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
                    handleAccountCreation(hotelResource, createCustomer(scanner));
                    break;
                case 4:
                    // Accesso all'area amministrativa
                    handleAdminMenu(adminMenu, adminResource, scanner);
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
            Customer customer,
            Date checkInDate,
            Date checkOutDate) {
        Collection<IRoom> roomsFound = hotelResource.findARoom(checkInDate, checkOutDate);
        try {
            Reservation reservedRoom = hotelResource.bookARoom(
                    customer.getEmail(),
                    roomsFound.stream().findFirst().get(),
                    checkInDate,
                    checkOutDate);
            System.out.println("Room reserved: " + reservedRoom);
        } catch (NullPointerException ex) {
            System.err.println("Customer unregistered.");
        } catch (Exception ex) {
            System.err.println("No rooms avaiable");
        }
    }

    private static void handleUserReservations(HotelResource hotelResource, String email) {
        for (Reservation reservation : hotelResource.getCustomersReservations(email)) {
            System.out.println(reservation);
        }
    }

    private static void handleAccountCreation(
            HotelResource hotelResource,
            Customer customer) {
        hotelResource.createACustomer(customer.getFirstName(), customer.getLastName(), customer.getEmail());
        System.out.println("User account successfully created.");
    }

    private static void handleAdminMenu(AdminMenu adminMenu, AdminResource adminResource, Scanner scanner) {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            adminMenu.displayAdminMenu();
            int adminChoice = adminMenu.getAdminChoice();

            switch (adminChoice) {
                case 1:
                    // Visualizzazione di tutti i clienti
                    displayAllCustomers(adminResource);
                    break;
                case 2:
                    // Visualizzazione di tutte le camere
                    displayAllRooms(adminResource);
                    break;
                case 3:
                    // Visualizzazione di tutte le prenotazioni
                    displayAllReservations(adminResource);
                    break;
                case 4:
                    // Aggiunta di una stanza
                    System.out.println("Is it a free room? ");
                    String isFreeRoom = scanner.next().trim().toLowerCase();
                    System.out.println("Write the room number in euro. ");
                    String roomNumber = scanner.next().trim().toUpperCase();
                    double roomPrice = 0;
                    if (!isFreeRoom.contains("y")) {
                        System.out.println("Write the price of the room. ");
                        roomPrice = scanner.nextDouble();
                    }
                    System.out.println("Write if the room is a single or a double room.");
                    String isDoubleRoom = scanner.next().trim().toLowerCase();
                    RoomType enumeration = isDoubleRoom.contains("d")
                            ? RoomType.DOUBLE : RoomType.SINGLE;

                    IRoom room4 = isFreeRoom.contains("y")
                            ? new FreeRoom(roomNumber, enumeration)
                            : new Room(roomNumber, roomPrice, enumeration);
                    addRoom(adminResource, room4);
                    System.out.println("Room added.");
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

    private static void displayAllCustomers(AdminResource adminResource) {
        adminResource.getAllCustomers();
        for (Customer customer : adminResource.getAllCustomers()) {
            System.out.println(customer);
        }
    }

    private static void displayAllRooms(AdminResource adminResource) {
        adminResource.getAllRooms();
        for (IRoom room : adminResource.getAllRooms()) {
            System.out.println(room);
        }
    }

    private static void displayAllReservations(AdminResource adminResource) {
        adminResource.displayAllReservations();
    }

    private static void addRoom(AdminResource adminResource, IRoom room4) {
        List<IRoom> rooms = new ArrayList<>();
        rooms.add(room4);
        adminResource.addRoom(rooms);
    }


    public static Customer createCustomer(Scanner scanner) {
        System.out.println("Write your first name");
        String firstName = scanner.next().trim().toLowerCase();
        System.out.println("Write your second name");
        String secondName = scanner.next().trim().toLowerCase();
        System.out.println("Write your email");
        String email = scanner.next().trim().toLowerCase();

        try {
            return new Customer(firstName, secondName, email);
        } catch (IllegalArgumentException ex) {
            System.err.println("Invalid email.");
        }
        return null;
    }
}
