package src;

import java.util.ArrayList;

public class Player extends Creature {

    public Item sword = null;
    public Item armor = null;
    public String name;
    public int serial;
    public int hpMoves;
    public int room;
    public char type = '@';
    public ArrayList<Item> pack = new ArrayList<Item>();
    public int score = 0;

    public Player(){
        System.out.println("Player:");
    }

    public void addItem(Item item, String name){
        pack.add(item);
        System.out.println(pack.size());
    }
    public void setWeapon(Item sword){
        this.sword = sword;
    }


    public void setArmor(Item armor){
        this.armor = armor;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

}
