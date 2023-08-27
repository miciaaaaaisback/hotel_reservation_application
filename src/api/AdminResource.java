package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource INSTANCE;
    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    private AdminResource() {
    }

    // Get a customer by email
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    // Add rooms
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    // Get all rooms
    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    // Get all customers
    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Display all reservations
    public void displayAllReservations() {
        reservationService.printAllReservations();
    }

    public static AdminResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdminResource();
        }
        return INSTANCE;
    }
}
