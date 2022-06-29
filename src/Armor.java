package src;

public class Armor extends Item{
    public String name;
    public int serial;
    public int room;

    public Armor(String name){
        this.name = name;
    }

    public void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
    }

    public void setName(String name){
        this.name = name;
    }
}
