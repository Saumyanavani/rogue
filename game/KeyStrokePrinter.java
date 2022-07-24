package game;
import src.*;
import src.Player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 1;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid displayGrid;
    private Dungeon dungeon = null;
    private int playerX;
    private int playerY;
    private Player player;

    public KeyStrokePrinter(ObjectDisplayGrid grid, Player player, Dungeon dungeon, int ppX, int ppY) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
        this.dungeon = dungeon;
        this.playerX = ppX;
        this.playerY = ppY;
        this.player = player;
    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
    }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {

        char ch;

        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() == null) {
                processing = false;
            } else {
                ch = inputQueue.poll();
                if (DEBUG > 1) {
                    System.out.println(CLASSID + ".processInput peek is " + ch);
                }
                if (ch == 'X') {
                    System.out.println("got an X, ending input checking");
                    return false;
                } else if (ch == 'h'){
                    playerm(playerX - 1, playerY);
                    System.out.println("Here");
                } else if (ch == 'k') {
                    playerm(playerX, playerY - 1);
                    System.out.println("Here");
                } else if (ch == 'j') {
                    playerm(playerX, playerY + 1);
                    System.out.println("Here");
                } else if (ch == 'l') {
                    playerm(playerX + 1, playerY);
                    System.out.println("Here");
                } else {
                    System.out.println("character " + ch + " entered on the keyboard");
                }
            }
        }
        return true;
    }

    public void playerm(int posX, int posY){
        Displayable p = displayGrid.getObjectFromDisplay(posX, posY);
        System.out.println(p.getClass());
        Displayable s = p;
        if ((p.getChar()) != 'X' &&  (p.getChar()) != ' '){
            char check = p.getChar();
            if (check == 'T' || check == 'S' || check == 'H'){
                System.out.println("Creature here");
            } else {
                Displayable removed = displayGrid.removeObjectFromDisplay(playerX, playerY);
                displayGrid.addObjectToDisplay(new Char('@'), posX, posY);
                this.playerX = posX;
                this.playerY = posY;
            }
        }
    }
    @Override
    public void run() {
        displayGrid.registerInputObserver(this);
        boolean working = true;
        while (working) {
            rest();
            working = (processInput( ));
        }
    }
}
