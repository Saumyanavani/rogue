package src;

public class Scroll extends Item{

    private final String name;
    private int room;
    private int serial;

    public Scroll(String name){
        this.name = name;
    }

    public void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
    }
}
