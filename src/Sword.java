package src;

public class Sword extends Item{

    //public String name;
    public int serial;
    public int room;
    public char type = '|';
    public boolean wielded = false;
    public Sword(String name){

        this.name = name;
        setType('|');
    }

    public void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
    }

    //public String returnName(){
      //  return this.name;
    //}

    public String toString(){
        return "Sword";
    }

}
