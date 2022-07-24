package src;

import java.util.ArrayList;

public class Player extends Creature {

    public Item sword;
    public Item armor;
    public String name;
    public int serial;
    public int hpMoves;
    public int room;
    public char type = '@';
    public ArrayList<Item> pack = new ArrayList<Item>();

    public Player(){
        System.out.println("Player:");
    }

    public void addItem(Item item){
        pack.add(item);
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
