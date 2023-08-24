package reservationModel;

import java.util.Date;

public class Room implements IRoom{

    private final String roomNumber;
    private final Double price;
    private final RoomTypeEnumeration.RoomType enumeration;

    public Room(String roomNumber, Double price, RoomTypeEnumeration.RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public  Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomTypeEnumeration.RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", enumeration=" + enumeration +
                '}';
    }

/*    public static void main(String[] args) {

        Room room1 = new Room("101", 150.0, RoomTypeEnumeration.RoomType.SINGLE);

        System.out.println("Numero stanza: " + room1.getRoomNumber());
        System.out.println("Prezzo stanza: " + room1.getRoomPrice());
        System.out.println("Tipo stanza: " + room1.getRoomType());

        System.out.println(room1);
    }*/
}
