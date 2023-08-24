package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    // Reference to the CustomerService and ReservationService
    //Creare istanze delle classi CustomerService e ReservationService all'interno della classe HotelResource è un modo per ottenere
    //un riferimento a queste istanze e poterle utilizzare nei metodi della classe HotelResource.
    //Questa approccio è chiamato "dependency injection" (iniezione di dipendenza), e serve a separare l'istanziazione delle dipendenze dal resto del codice.
    private static CustomerService customerService = new CustomerService();
    private static ReservationService reservationService = new ReservationService();

    // Get a customer by email
    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    // Create a customer
    public static void createACustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    // Get a room by room number
    public static IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    // Book a room
    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    // Get reservations for a customer
    public static Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }

    // Find available rooms
    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}


