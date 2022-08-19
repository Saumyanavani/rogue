package src;

public class Scroll extends Item{

    private final String name;
    private int room;
    private int serial;
    public char type = '?';
    public Scroll(String name){
        this.name = name;
        setType('?');
    }

    public void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
    }

    public String toString(){
        return "Scroll";
    }

    public String returnName(){
        return this.name;
    }
}
