package src;

public class Monster extends Creature{

    public String name;
    public int room;
    public int serial;

    public Monster(String name){
        System.out.println("Monster created");
    }

    public void setName(String name){
        this.name = name;
    }

    public void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
    }


}
