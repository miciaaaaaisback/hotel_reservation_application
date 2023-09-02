package api;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource INSTANCE;
    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    private HotelResource() {
    }

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
        if (customer == null) {
            throw new NullPointerException("Customer not found.");
        }
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

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut, boolean freeRoom) {
        Collection<IRoom> rooms = findARoom(checkIn, checkOut);
        if (freeRoom) {
            return rooms.stream().filter(room -> room instanceof FreeRoom).toList();
        } else {
            return rooms.stream().filter(room -> !(room instanceof FreeRoom)).toList();
        }
    }

    public static HotelResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotelResource();
        }
        return INSTANCE;
    }
}


