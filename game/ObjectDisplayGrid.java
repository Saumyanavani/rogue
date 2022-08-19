package game;

import asciiPanel.AsciiPanel;
import src.Displayable;
import src.Item;
import src.Monster;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    private static AsciiPanel terminal;
    private Stack<Displayable>[][] objectGrid = null;

    private List<InputObserver> inputObservers = null;

    private static int height;
    private static int width;
    private Random random = new Random();

    public ObjectDisplayGrid(int _width, int _height) {
        width = _width;
        height = _height;

        terminal = new AsciiPanel(width, height);

        objectGrid = new Stack[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                objectGrid[i][j] = new Stack<Displayable>();
            }
        }
        initializeDisplay();

        super.add(terminal);
        super.setSize(width * 9, height * 16);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // super.repaint();
        // terminal.repaint( );
        super.setVisible(true);
        terminal.setVisible(true);
        terminal.addKeyListener(this);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
                System.out.println("Here");
            }
        }
    }

    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }

    // we have to override, but we don't use this
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void initializeDisplay() {
        Char ch = new Char(' ');
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                addObjectToDisplay(ch, i, j);
            }
        }
        terminal.repaint();
    }

    public void fireUp() {
        if (this.requestFocusInWindow()) {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow Succeeded");
        } else {
            System.out.println(CLASSID + ".ObjectDisplayGrid(...) requestFocusInWindow FAILED");
        }
    }

    public void addObjectToDisplay(Displayable ch, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                ch.dispPosX = x;
                ch.dispPosY = y;
                objectGrid[x][y].push(ch);
                writeToTerminal(x, y);
            }
        }
    }

    public void dropItem(Displayable ch, int x, int y){
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                if (objectGrid[x][y].size() > 1){
                    Displayable keep = objectGrid[x][y].pop();
                    objectGrid[x][y].push(ch);
                    objectGrid[x][y].push(keep);
                }
                ch.dispPosX = x;
                ch.dispPosY = y;
                writeToTerminal(x, y);
            }
        }
    }

    private void writeToTerminal(int x, int y) {
        Displayable cha = (Displayable) objectGrid[x][y].peek();
        char ch = ((Displayable) cha).getChar();
        terminal.write(ch, x, y);
        terminal.repaint();
    }


    public Displayable getItem(int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                if (objectGrid[x][y].size() > 1){
                    Displayable k = objectGrid[x][y].pop();
                    if (objectGrid[x][y].size() > 1){
                        return (Displayable) objectGrid[x][y].peek();
                    }
                    objectGrid[x][y].push(k);
                    writeToTerminal(x, y);
                }
            }
        }
        return new Char('0');
    }

    public Displayable getObjectFromDisplay(int x, int y){
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
//                if (objectGrid[x][y].size() > 1){
//                    Displayable r = (Displayable) objectGrid[x][y].pop();
//                    writeToTerminal(x, y);
//                    return r;
//                }

                return (Displayable) objectGrid[x][y].peek();
            }
        }
        return new Char('0');
    }

    public Displayable removeObjectFromDisplay(int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                if (objectGrid[x][y].size() > 1) {
                    Displayable remove = (Displayable) objectGrid[x][y].pop();
                    Displayable removedItem = null;
//                    if (objectGrid[x][y].size() > 1){
//                        if (objectGrid[x][y].peek() instanceof Item){
//                            removedItem = (Displayable) objectGrid[x][y].pop();
//                            objectGrid[x][y].push(remove);
//                        }
//                    }
                    writeToTerminal(x, y);
                    return remove;
                }
            }
        }
        return new Char(' ');
    }

    public int getlength() {
        return objectGrid.length;
    }

    public int getzlength(){
        return objectGrid[0].length;
    }

//    public void teleport(Monster monster, int posX, int posY) {
//        ArrayList<Displayable> teleports = new ArrayList<>();
//        for (int i = 0; i < objectGrid.length; i++){
//            for (int j = 0; j < objectGrid[0].length; j++){
//                if ((getObjectFromDisplay(i, j).getChar() == '.') || (getObjectFromDisplay(i, j).getChar() == '+') || (getObjectFromDisplay(i, j).getChar() == '#')){
//                    Displayable x = getObjectFromDisplay(i, j);
//                    teleports.add(x);
//                    x.dispPosX = i;
//                    x.dispPosY = j;
//                }
//            }
//        }
//        Displayable teleport_dest =teleports.get(random.nextInt(teleports.size()));
//        Displayable removed = removeObjectFromDisplay(posX, posY);
//        objectGrid[teleport_dest.dispPosX][teleport_dest.dispPosY].push(monster);
//        System.out.println("Teleported to : " + teleport_dest.dispPosY + teleport_dest.dispPosY + teleport_dest.getChar());
//    }
}
