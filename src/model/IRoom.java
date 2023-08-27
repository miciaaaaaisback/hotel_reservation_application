package model;

//Un'interfaccia in Java contiene solo le dichiarazioni dei metodi, ma non ne fornisce l'implementazione.
//Gli oggetti delle classi che implementano un'interfaccia devono fornire l'implementazione concreta dei metodi dichiarati nell'interfaccia.
public interface IRoom {
    String getRoomNumber();
    Double getRoomPrice();
    RoomType getRoomType();
    boolean isFree();
    @Override
    int hashCode();
}
