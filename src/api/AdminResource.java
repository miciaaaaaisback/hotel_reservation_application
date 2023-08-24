package api;

import reservationModel.Customer;
import reservationModel.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    // Reference to the CustomerService and ReservationService
    private static CustomerService customerService = new CustomerService();
    private static ReservationService reservationService = new ReservationService();

    // Get a customer by email
    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    // Add rooms
    public static void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    // Get all rooms
    public static Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    // Get all customers
    public static Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Display all reservations
    public static void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
