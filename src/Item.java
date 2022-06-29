package src;

public class Item extends Displayable{

    public Creature owner;
    public int room;
    public String mg;
    public Action action;

    public void setOwner(Creature owner){
        this.owner = owner;
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
}
