package game;
import src.*;

import org.xml.sax.SAXException;
import src.DungeonXMLHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Rogue implements Runnable{

    private static final int DEBUG = 0;
    private Thread playerMotion;
    private boolean isRunning;
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid displayGrid = null;
    private Thread keyStrokePrinter;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static Dungeon dungeon;
    private static DungeonXMLHandler handler;
    private int hp;
    public Rogue(int width, int height){
        displayGrid = new ObjectDisplayGrid(width, height + 6);
    }
    private static Player player;
    private int ppX;
    private int ppY;

    @Override
    public void run(){

        displayGrid.fireUp();
        for (int i = 0; i < dungeon.rooms.size(); i += 1){
            Room room = dungeon.rooms.get(i);
            for (int j = 0; j < room.width; j++){
                displayGrid.addObjectToDisplay(new Char('X'), room.x + j, room.y + dungeon.topHeight);
                displayGrid.addObjectToDisplay(new Char('X'), room.x + j, room.y + + dungeon.topHeight + room.height - 1);
            }
            for (int j = 1; j < room.height - 1; j++){
                displayGrid.addObjectToDisplay(new Char('X'), room.x, room.y + j + dungeon.topHeight);
                displayGrid.addObjectToDisplay(new Char('X'), room.x + room.width - 1, room.y + j +dungeon.topHeight);
                for (int k = 1; k < room.width - 1; k++){
                    displayGrid.addObjectToDisplay(new Char('.'), room.x + k, room.y + j + dungeon.topHeight);
                }
            }

            //displayGrid.initializeDisplay();
            for (int x = 0; x < room.creatures.size(); x++){
                System.out.println("SIZE: " + room.creatures.size());
                Creature creature = room.creatures.get(x);
                System.out.println(creature.type);
                System.out.println(creature.x + " " + creature.y);
                displayGrid.addObjectToDisplay(creature, creature.x + room.x, creature.y + room.y + dungeon.topHeight);
                System.out.println("Creature pos: " + creature.x + room.x + creature.y + room.y + dungeon.topHeight);
            }

            if (room.player != null){
                player = room.player;
                hp = player.Hp;
                ppX = player.x + room.x;
                ppY = player.y + room.y + dungeon.topHeight;
                displayGrid.addObjectToDisplay(new Char('@'), player.x + room.x, player.y + room.y + dungeon.topHeight);
            }
        }

        for (int i = 0; i < dungeon.passages.size(); i++){
            Passage passage = dungeon.passages.get(i);
            //System.out.println("Passage position: " + passage.posX[0] + passage.posY[0]);
            //displayGrid.addObjectToDisplay(new Char('+'), passage.posX[0] , passage.posY[0] + dungeon.topHeight);
            for (int k = 0; k < passage.posX.length - 1; k++){
                int posX1 = passage.posX[k];
                int posY1 = passage.posY[k];
                int posX2 = passage.posX[k+1];
                int posY2 = passage.posY[k+1];
                if (posX1 == posX2){
                    if ((posX1 == passage.posX[0]) && (posY1 == passage.posY[0])){
                        displayGrid.addObjectToDisplay(new Char('+'), passage.posX[0] , passage.posY[0] + dungeon.topHeight);
                        if (posY1 < posY2){
                            posY1 ++;
                        } else {
                            posY1 --;
                        }
                    }
                    while(posY1 != posY2){
                        displayGrid.addObjectToDisplay(new Char('#'), posX1, posY1 + dungeon.topHeight);
                        if (posY1 < posY2){
                            posY1 ++;
                        } else {
                            posY1 --;
                        }
                    }
                    displayGrid.addObjectToDisplay(new Char('+'), posX1, posY1 + dungeon.topHeight);
                } else if (posY1 == posY2){
                    if ((posX1 == passage.posX[0]) && (posY1 == passage.posY[0])){
                        displayGrid.addObjectToDisplay(new Char('+'), passage.posX[0] , passage.posY[0] + dungeon.topHeight);
                        if (posX1 < posX2){
                            posX1 ++;
                        } else {
                            posX1 --;
                        }
                    }
                    while(posX1 != posX2){
                        displayGrid.addObjectToDisplay(new Char('#'), posX1, posY1 + dungeon.topHeight);
                        if (posX1 < posX2){
                            posX1 ++;
                        } else {
                            posX1 --;
                        }
                    }
                    displayGrid.addObjectToDisplay(new Char('+'), posX1, posY1 + dungeon.topHeight);
                }
            }
        }

        for(int i = 0; i < dungeon.items.size(); i++){
            Item item = dungeon.items.get(i);
            int itemr = item.room;
            if(item.getClass() == Sword.class){
                displayGrid.addObjectToDisplay(item, item.x + dungeon.rooms.get(itemr).x, item.y + dungeon.rooms.get(itemr).y + dungeon.topHeight);
            } else if(item.getClass() == Armor.class){
                displayGrid.addObjectToDisplay(item, item.x + dungeon.rooms.get(itemr).x, item.y + dungeon.rooms.get(itemr).y + dungeon.topHeight);
            } else if(item.getClass() == Scroll.class){
                displayGrid.addObjectToDisplay(item, item.x + dungeon.rooms.get(itemr).x, item.y + dungeon.rooms.get(itemr).y + dungeon.topHeight);
            }
        }

        String top = "HP: " + hp + " Core: " + player.score;
        for (int i = 0; i < top.length(); i++){
            displayGrid.addObjectToDisplay(new Char(top.charAt(i)), i, 0);
        }

        String pck = "Pack: ";
        for (int i = 0; i < pck.length(); i++){
            displayGrid.addObjectToDisplay(new Char(pck.charAt(i)), i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight)-3);
        }

        String inf = "Info: ";
        for (int i = 0; i < inf.length(); i++){
            displayGrid.addObjectToDisplay(new Char(inf.charAt(i)), i, dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight);
        }
    }

    public static void main(String[] args) throws Exception {
        String filename = null;

        if (args.length == 1) {
            filename = args[0];
        } else {
            System.out.println("java Test <xmlfilename>");
            return;
        }


        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            // just copy this
            SAXParser saxParser = saxParserFactory.newSAXParser();
            // just copy this
            DungeonXMLHandler handler = new DungeonXMLHandler();
            // just copy this.  This will parse the xml file given by fileName
            saxParser.parse(new File(filename), handler);
            dungeon = handler.dungeon;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }

        Rogue rogue = new Rogue(dungeon.width, dungeon.gameHeight + (dungeon.topHeight + dungeon.bottomHeight) - 2);
        Thread testThread = new Thread(rogue);
        testThread.start();

        testThread.join();
        rogue.playerMotion = new Thread(new KeyStrokePrinter(displayGrid, player, dungeon, rogue.ppX, rogue.ppY));
        rogue.playerMotion.start();
        rogue.playerMotion.join();
    }
}
