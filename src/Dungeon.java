package src;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    public String name;
    public int width;
    public int topHeight;
    public int gameHeight;
    public int bottomHeight;


    public List<Room> rooms = new ArrayList<Room>();
    public List<Passage> passages = new ArrayList<Passage>();
    public List<Item> items = new ArrayList<Item>();
    public List<Creature> creatures = new ArrayList<Creature>();

    // Constructor
    public Dungeon(String name, int width, int gameHeight, int topHeight, int bottomHeight){
        this.name = name;
        this.width = width;
        this.gameHeight = gameHeight;
        this.topHeight = topHeight;
        this.bottomHeight = bottomHeight;
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public void addCreature(Creature creature){
        creatures.add(creature);
    }

    public void addPassage(Passage passage){
        passages.add(passage);
    }

    public void addItem(Item item){
        items.add(item);
    }

}
