package src;

import java.util.ArrayList;
import java.util.List;

public class Creature extends Displayable{
    public int room;
    public List<CreatureAction> creatureActions = new ArrayList<CreatureAction>();

    public Creature(){
        System.out.println("Created Creature");
    }
    public int hpm;

    public void setRoom(int room){
        this.room = room;
    }

    public void addCreatureAction(CreatureAction action){
        creatureActions.add(action);
    }

    /*
     public void setDeathAction(CreatureAction da){

     }

     public void setHitAction(Action ha){

     }
    */

}
