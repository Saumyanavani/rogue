package src;

import java.util.ArrayList;
import java.util.List;

public class Passage extends Structure{
    public String name;
    public int room1;
    public int room2;
    public int[] posX = new int[200];
    public int[] posY = new int[200];
    public int index = 0;
    public Passage(){
        System.out.println("Passage created");
    }

    public void setName(String name){
        this.name = name;
    }

    public void setID(int room1, int room2){
        this.room1 = room1;
        this.room2 = room2;
    }

    @Override
    public void setPosX(int x){
        posX[index] = x;
        System.out.println("posX: " + x);
    }

    @Override
    public void setPosY(int y){
        posY[index++] = y;
        System.out.println("posY: " + y);
    }


}
