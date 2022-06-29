package src;

import java.util.ArrayList;
import java.util.List;

public class Room extends Structure{

    public String name;
    public List<Creature> creatures = new ArrayList<Creature>();
    public List<Item> items = new ArrayList<Item>();
    public Room(String name){
        this.name = name;
    }
    public Player player = null;

    public void setId(String room){
        this.name = room;
    }

    public void setCreature(Creature monster){

    }

    public void addCreature(Creature creatureBeingParsed) {
        this.creatures.add(creatureBeingParsed);
    }

    public void addPlayer(Player player) {
        this.player = player;
    }

    public void addItem(Item itemBeingParsed) {
        items.add(itemBeingParsed);
        System.out.println("Item added" + itemBeingParsed.getClass());
    }
}
