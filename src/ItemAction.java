package src;

public class ItemAction extends Action{

    public Item owner;
    public String type;
    public int value;
    public String name;

    public ItemAction(Item owner){
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
