import api.AdminMenu;
import api.AdminResource;
import api.HotelResource;
import api.MainMenu;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HotelApplication {

    public static void main(String[] args) {
        AdminResource adminResource = AdminResource.getInstance();
        HotelResource hotelResource = HotelResource.getInstance();
        AdminMenu adminMenu = new AdminMenu();
        MainMenu mainMenu = new MainMenu();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            mainMenu.displayMenu();
            int choice = mainMenu.getUserChoice();

            switch (choice) {
                case 1:
                    // Ricerca e prenotazione di una stanza
                    System.out.println("Do you already have an account? Yes/No.");
                    String haveAnAccount = scanner.next().trim().toLowerCase();
                    Customer customer;
                    String email;
                    if (!haveAnAccount.contains("y")) {
                        System.err.println("You must have an account.");
                        break;
                    } else {
                        System.out.println("Write your email.");
                        email = scanner.next().trim().toLowerCase();
                        customer = hotelResource.getCustomer(email);
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date checkIn;
                    Date checkOut;
                    try {
                        System.out.println("Write your check-in date in the format dd/MM/yyyy");
                        checkIn = sdf.parse(scanner.next().trim());
                        System.out.println("Write your check-out date in the format dd/MM/yyyy");
                        checkOut = sdf.parse(scanner.next().trim());
                    } catch (ParseException ex) {
                        System.err.println("Date format not correct.");
                        break;
                    }

                    System.out.println("Do you want a free room?");
                    boolean isFree = scanner.next().trim().toLowerCase().startsWith("y");

                    handleRoomReservation(hotelResource, customer, checkIn, checkOut, scanner, isFree);
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
                    try {
                        handleAccountCreation(hotelResource, createCustomer(scanner));
                    } catch (NullPointerException ex) {
                        System.err.println("Input not valid.");
                    }
                    break;
                case 4:
                    // Accesso all'area amministrativa
                    handleAdminMenu(adminMenu, adminResource, scanner, hotelResource);
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
        Scanner scanner = new Scanner(System.in);
        Room room1 = new Room("101", 100.0, RoomType.SINGLE);
        Room room2 = new Room("102", 150.0, RoomType.DOUBLE);
        Room room3 = new FreeRoom("103", RoomType.SINGLE);

        List<IRoom> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        adminResource.addRoom(roomList);

        hotelResource.createACustomer("Mario", "Rossi", "mario@gmail.com");
        hotelResource.createACustomer("Arturo", "Gatti", "arturo@gmail.com");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date checkInDate = null;
        Date checkOutDate = null;

        try {
            checkInDate = dateFormat.parse("05/08/2023");
            checkOutDate = dateFormat.parse("10/08/2023");
        } catch (ParseException e) {
            System.err.println("Formato data non valido. Inserisci un'altra data:");
            scanner.next();
        }

        hotelResource.bookARoom("mario@gmail.com", room3, checkInDate, checkOutDate);
    }

    private static void handleRoomReservation(
            HotelResource hotelResource,
            Customer customer,
            Date checkInDate,
            Date checkOutDate, Scanner scanner, boolean isFree) {
        List<Reservation> reservations = new ArrayList<>();

        while (true) {
            Collection<IRoom> roomsFound = hotelResource.findARoom(checkInDate, checkOutDate, isFree);
            if (roomsFound.isEmpty()) {
                System.err.println("No rooms available.");
                int toleranceDays = getToleranceDays(scanner);

                if (toleranceDays == -1) {
                    // L'utente ha scelto di tornare al menu principale
                    break;
                }

                // Aggiungi giorni di tolleranza alle date di check-in e check-out
                checkInDate = incrementDate(checkInDate, toleranceDays);
                checkOutDate = incrementDate(checkOutDate, toleranceDays);
                continue;
            } else {
                System.out.println("Available rooms:");
                int roomNumber = 1;
                for (IRoom room : roomsFound) {
                    System.out.println(roomNumber + ". " + room);
                    roomNumber++;
                }

                int selectedRoomNumber;
                try {
                    System.out.println("Select a room by entering its number (0 to finish):");
                    selectedRoomNumber = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.err.println("Invalid input for room number. Please enter a valid number.");
                    scanner.next(); // Consuma l'input non valido
                    continue; // Riprende il ciclo per chiedere nuovamente all'utente di selezionare una stanza
                }

                if (selectedRoomNumber == 0) {
                    // L'utente ha scelto di terminare la selezione
                    break;
                }

                if (selectedRoomNumber < 1 || selectedRoomNumber > roomsFound.size()) {
                    System.err.println("Invalid room number. Please select a valid room.");
                    continue; // Riprende il ciclo per chiedere nuovamente all'utente di selezionare una stanza
                }

                // Ottieni la stanza corretta in base all'indice dell'utente
                IRoom selectedRoom = roomsFound.toArray(new IRoom[0])[selectedRoomNumber - 1];

                try {
                    Reservation reservedRoom = hotelResource.bookARoom(
                            customer.getEmail(),
                            selectedRoom,
                            checkInDate,
                            checkOutDate
                    );
                    reservations.add(reservedRoom);
                    System.out.println("Room reserved: " + reservedRoom);
                } catch (NullPointerException ex) {
                    System.err.println("Customer unregistered.");
                } catch (Exception ex) {
                    System.err.println("Error while booking the room.");
                }
            }
        }

        if (!reservations.isEmpty()) {
            System.out.println("Your reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    private static int getToleranceDays(Scanner scanner) {
        System.out.println("Enter how many days out the room recommendation should search if there are no available rooms (0 to cancel and return to the main menu):");
        int toleranceDays = scanner.nextInt();

        if (toleranceDays == 0) {
            System.out.println("Returning to the main menu.");
            return -1; // -1 indica che l'utente ha scelto di annullare
        }

        return toleranceDays;
    }

    private static Date incrementDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
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

    private static void handleAdminMenu(AdminMenu adminMenu, AdminResource adminResource, Scanner scanner, HotelResource hotelResource) {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            adminMenu.displayAdminMenu();
            int adminChoice = adminMenu.getAdminChoice();

            switch (adminChoice) {
                case 0:
                    //Popola con dati di test
                    initializeSystem(adminResource, hotelResource);
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
                    System.out.println("Is it a free room? Yes/No");
                    String isFreeRoom = scanner.next().trim().toLowerCase();
                    if (!(isFreeRoom.contains("yes") || isFreeRoom.contains("no"))) {
                        System.out.println("Invalid input.");
                        break;
                    }
                    System.out.println("Write the room number. ");
                    Integer roomNumber = null;
                    try {
                        roomNumber = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for room number. Please enter a valid integer.");
                        scanner.nextLine();
                    }
                    double roomPrice = 0;
                    if (!isFreeRoom.contains("y")) {
                        try {
                            System.out.println("Write the price of the room in euro. ");
                            roomPrice = scanner.nextDouble();
                        } catch (InputMismatchException e) {
                            System.err.println("Invalid input for room price. Please enter a valid number.");
                            scanner.nextLine();
                            break;
                        }
                    }

                    System.out.println("Write if the room is a single or a double room. SINGLE/DOUBLE");
                    String isDoubleRoom = scanner.next().trim().toLowerCase();
                    RoomType enumeration = isDoubleRoom.contains("d")
                            ? RoomType.DOUBLE : RoomType.SINGLE;

                    IRoom room4 = isFreeRoom.contains("y")
                            ? new FreeRoom(roomNumber.toString(), enumeration)
                            : new Room(roomNumber.toString(), roomPrice, enumeration);
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
