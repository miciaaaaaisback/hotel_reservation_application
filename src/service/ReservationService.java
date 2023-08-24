package service;

import reservationModel.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ReservationService {

    private static Collection<IRoom> rooms = new ArrayList<>();
    private static Collection<Reservation> reservations = new ArrayList<>();
    private static Collection<IRoom> availableRooms = new ArrayList<>();
    private static Collection<Reservation> customerReservations = new ArrayList<>();


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
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        for (IRoom room : rooms) {
            if (room.isFree()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    public void printAllReservations() {
        for(Reservation reservation : reservations){
            System.out.println(reservation);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

}

