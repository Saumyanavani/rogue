package src;

public class Sword extends Item{

    public String name;
    public int serial;
    public int room;

    public Sword(String name){
        this.name = name;
    }

    public void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
    }

}
