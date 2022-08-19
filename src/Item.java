package src;

public class Item extends Displayable{

    public Creature owner;
    public int room;
    public String mg;
    public Action action;
    public String name;
    public boolean worn = false;
    public boolean wielded = false;

    public void setOwner(Creature owner){
        this.owner = owner;
    }

    public void printName(){
        System.out.println("Name is : " + this.name);
    }

    public void setRoom(int room){
        this.room = room;
    }

    public void setMg(String mg){
        this.mg = mg;
    }

    public void setAction(ItemAction action) {
        this.action = action;
    }

    public String returnName() {
        System.out.println(worn);
        this.printName();
        if ((this.worn) || (this.wielded)) {
            if(this.worn) {
                return ("[w] " + this.name);
            }
            if (this.wielded){
                return ("[t] " + this.name);
            }
        } else {
            return this.name;
        }
        return " ";
    }
}
