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
    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = customerService.getCustomer(customerEmail);
        // Check if the room is available for the specified date range
        Collection<IRoom> availableRooms = reservationService.findRooms(checkInDate, checkOutDate);
        if (!availableRooms.contains(room)) {
            throw new IllegalArgumentException("Room is not available for the specified date range");
        }
        // If the room is available, make the reservation
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }


    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}


