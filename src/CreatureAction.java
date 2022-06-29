package src;

public class CreatureAction extends Action{
    public Creature owner;
    public String name;
    public String type;

    public CreatureAction(Creature owner){
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
