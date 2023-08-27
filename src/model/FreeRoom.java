package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, 0D, enumeration);
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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

