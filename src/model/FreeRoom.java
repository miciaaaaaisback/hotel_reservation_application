package model;

//Ereditare una classe madre comporta che la sottoclasse avrà accesso ai campi e ai metodi della superclasse.
// Questo significa che la sottoclasse può utilizzare questi membri direttamente come se fossero suoi, a meno che non siano stati dichiarati come privati (private) nella superclasse.

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, 0D, enumeration);
    }

    @Override
    public String toString() {
        return "FreeRoom{" +
                "roomNumber='" + getRoomNumber() + '\'' +
                ", price=" + getRoomPrice() +
                ", enumeration='" + getRoomType() + '\'' +
                '}';
    }
}

