package model;

//Ereditare una classe madre comporta che la sottoclasse avrà accesso ai campi e ai metodi della superclasse.
// Questo significa che la sottoclasse può utilizzare questi membri direttamente come se fossero suoi, a meno che non siano stati dichiarati come privati (private) nella superclasse.

public class FreeRoom extends Room {


    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    /*    public static void main(String[] args) {

        FreeRoom freeRoom1 = new FreeRoom("102", 0.0, RoomTypeEnumeration.RoomType.DOUBLE);

        System.out.println("Numero stanza: " + freeRoom1.getRoomNumber());
        System.out.println("Prezzo stanza: " + freeRoom1.getRoomPrice());
        System.out.println("Tipo stanza: " + freeRoom1.getRoomType());

        System.out.println(freeRoom1);
    }*/
}

