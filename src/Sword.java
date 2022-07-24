package src;

public class Sword extends Item{

    public String name;
    public int serial;
    public int room;
    public char type = '|';
    public Sword(String name){
        this.name = name;
        setType('|');
    }

    public void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
    }

}
