package service;

import model.*;

import java.util.*;

public class ReservationService {
    private static ReservationService INSTANCE;
    private Collection<IRoom> rooms = new HashSet<>();
    private Collection<Reservation> reservations = new LinkedList<>();
    //private static Collection<IRoom> availableRooms = new ArrayList<>();
    //private static Collection<Reservation> customerReservations = new ArrayList<>();

    private ReservationService() {}

    public void addRoom(IRoom room) {
        rooms.add(room);
    }

    public IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        // Check if the room is already reserved for any overlapping date range
        for (Reservation reservation : reservations) {
            if (reservation.getRoom() == room) {
                Date existingCheckIn = reservation.getCheckInDate();
                Date existingCheckOut = reservation.getCheckOutDate();

                // Check for date range overlap
                if (checkInDate.before(existingCheckOut) && checkOutDate.after(existingCheckIn)) {
                    // There is an overlap, room is already booked
                    throw new IllegalArgumentException("Room is already booked for the specified date range");
                }
            }
        }

        // If no conflict is found, create and add the reservation
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }


    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new HashSet<>();
        for (IRoom room : rooms) {
            if (room.isFree()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    public void printAllReservations() {
        System.out.println("Printing all reservations");
        for(Reservation reservation : reservations){
            System.out.println(reservation);
        }
        System.out.println("All reservations printed");
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    public static ReservationService getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }
}

