package game;
import src.*;
import src.Player;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Random;
public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 1;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid displayGrid;
    private Dungeon dungeon = null;
    private int playerX;
    private int playerY;
    private Player player;
    private boolean displayPack = false;
    private int moveCount = 0;
    private boolean wear = false;
    private boolean take = false;
    private boolean drop = false;
    private boolean endGame = false;
    private boolean read = false;
    private boolean help = false;
    private int hmoves = 0;
    private boolean hallucinateMode = false;
    private int hCheck = 0;

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
                if (!endGame){
                    if (wear) {
                        if (ch >= 48 && ch <= 57){
                            Item armor = player.pack.get(ch - 49);
                            if (armor instanceof Armor){
                                player.setArmor(armor);
                                armor.worn = true;
                                player.change = true;
                                for (int i = 0; i < 25; i++) {
                                    displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                                StringBuilder packstr = new StringBuilder("Info: Wearing Armor");
                                for (int k = 0; k < packstr.length(); k++){
                                    displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                                wear = !wear;
                            } else {
                                for (int i = 0; i < 100; i++) {
                                    displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                                StringBuilder packstr = new StringBuilder("Info: Chosen item is not an Armor!");
                                for (int k = 0; k < packstr.length(); k++){
                                    displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                            }
                        } else {
                            for (int i = 0; i < 25; i++) {
                                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Chosen armor doesn't exist");
                            for (int k = 0; k < packstr.length(); k++){
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            wear = !wear;
                        }
                    } else if (take) {
                        if (ch >= 48 && ch <= 57){
                            Item sword = player.pack.get(ch - 49);
                            if (sword instanceof Sword){
                                player.setArmor(sword);
                                sword.wielded = true;
                                for (int i = 0; i < 100; i++) {
                                    displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                                StringBuilder packstr = new StringBuilder("Info: Wielding Sword");
                                for (int k = 0; k < packstr.length(); k++){
                                    displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                                take = !take;
                            } else {
                                for (int i = 0; i < 100; i++) {
                                    displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                                StringBuilder packstr = new StringBuilder("Info: Chosen item is not a sword!");
                                for (int k = 0; k < packstr.length(); k++){
                                    displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                            }
                        } else {
                            for (int i = 0; i < 100; i++) {
                                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Chosen sword doesn't exist");
                            for (int k = 0; k < packstr.length(); k++){
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            take = !take;
                        }
                    } else if (read) {
                        if (ch >= 48 && ch <= 57){
                            Item scroll = player.pack.get(ch - 49);
                            if (scroll instanceof Scroll){
                                //player.setWeapon(sword);
                                //sword.wielded = true;
                                doScrollActions(scroll, player);
                                player.pack.remove(ch-49);
                                read = !read;
                            } else {
                                for (int i = 0; i < 100; i++) {
                                    displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                                StringBuilder packstr = new StringBuilder("Info: Chosen item is not a sroll!");
                                for (int k = 0; k < packstr.length(); k++){
                                    displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                            }
                        } else {
                            for (int i = 0; i < 100; i++) {
                                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Please choose item in pack");
                            for (int k = 0; k < packstr.length(); k++){
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            read = !read;
                        }
                    } else if (drop) {
                        if (ch >= 48 && ch <= 57){
                            int ch1 = ch - 49;
                            System.out.println("CH is " + ch1);
                            itemDrop(playerX, playerY, ch1);
                        }
                    } else if (help) {
                        if (ch == 'h'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Move player left");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 'j'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Move player down");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 'k'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Move player up");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 'l'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: move player right");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 'i'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Turn pack display on/off");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 'p'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Pick Item");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 'd') {
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: d <number> is used to drop selected item from pack");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 'w'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: w <number> used to wear chosen armor");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 't'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: t <number> used to wield selected sword");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 'r'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: r <number> to read chosen scroll");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == 'c'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Take off/change worn armor");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if ((ch == 'e') || (ch == 'E')){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Used to end game");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (ch == '?'){
                            for( int i = 0; i< 100; i++){
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Lists all commands");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try {
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        help = false;
                    } else if (ch == 'X') {
                        System.out.println("got an X, ending input checking");
                        return false;
                    } else if (ch == 'h') {
                        playerm(playerX - 1, playerY, player);
                        System.out.println("Here");
                    } else if (ch == 'k') {
                        playerm(playerX, playerY - 1, player);
                        System.out.println("Here");
                    } else if (ch == 'j') {
                        playerm(playerX, playerY + 1, player);
                        System.out.println("Here");
                    } else if (ch == 'l') {
                        playerm(playerX + 1, playerY, player);
                        System.out.println("Here");
                    } else if (ch == 'i') {
                        displayPack = !displayPack;
                        showPack();
                    } else if (ch == 'p') {
                        itemPick(playerX, playerY);
                    } else if (ch == 'd') {
                        showPack();
                        for (int i = 0; i < 25; i++) {
                            displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        StringBuilder packstr = new StringBuilder("Info: Pick item to drop");
                        for (int k = 0; k < packstr.length(); k++) {
                            displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        drop = !drop;
                        //itemDrop(playerX, playerY);
                    } else if (ch == 'w') {
                        for (int i = 0; i < 100; i++) {
                            displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        StringBuilder packstr = new StringBuilder("Info: Pick Armor to wear");
                        for (int k = 0; k < packstr.length(); k++) {
                            displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        wear = !wear;
                    } else if (ch == 't') {
                        for (int i = 0; i < 100; i++) {
                            displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        StringBuilder packstr = new StringBuilder("Info: Pick Sword to take");
                        for (int k = 0; k < packstr.length(); k++) {
                            displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        take = !take;
                    } else if (ch == 'r') {
                        for (int i = 0; i < 100; i++) {
                            displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        StringBuilder packstr = new StringBuilder("Info: Pick Scroll to read");
                        for (int k = 0; k < packstr.length(); k++) {
                            displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        read = !read;
                    } else if ((ch == 'e') || (ch == 'E')) {
                        for (int i = 0; i < 100; i++) {
                            displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        StringBuilder packstr = new StringBuilder("Info: E pressed! Game has ended!");
                        for (int k = 0; k < packstr.length(); k++) {
                            displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        endGame = !endGame;
                    } else if (ch == '?') {
                        for (int i = 0; i < 100; i++) {
                            displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        StringBuilder packstr = new StringBuilder("Info: h j k l p i d e w t r c ? 0-9 H. H <cmd> for more info!");
                        for (int k = 0; k < packstr.length(); k++) {
                            displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                    } else if (ch == 'c') {
                        if (player.change) {
                            for (int l = 0; l < player.pack.size(); l++) {
                                if (player.pack.get(l) instanceof Armor) {
                                    if (player.pack.get(l).worn) {
                                        player.pack.get(l).worn = false;
                                        player.armor = null;
                                        player.change = false;
                                    }
                                }
                            }
                            for (int x = 0; x < 100; x++) {
                                displayGrid.removeObjectFromDisplay(x, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: Armor has been unequipped");
                            for (int x = 0; x < packstr.length(); x++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(x)), x, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                try { // delay monster attack
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            for (int i = 0; i < 100; i++) {
                                displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr = new StringBuilder("Info: No armor being worn!");
                            for (int k = 0; k < packstr.length(); k++) {
                                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                        }
                    } else if (ch == 'H') {
                        help = true;
                        for( int i = 0; i< 100; i++){
                            displayGrid.removeObjectFromDisplay(i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                        }
                        StringBuilder packstr = new StringBuilder("Info: Choose command for more info!");
                        for (int k = 0; k < packstr.length(); k++) {
                            displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            try {
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                 else {
                    System.out.println("character " + ch + " entered on the keyboard");
                }
            }
        }
        return true;
    }

    private void doScrollActions(Item scroll, Player player) {
        Random random = new Random();
        System.out.println("Read scroll");
        for (int i = 0; i < scroll.actions.size(); i++){
            System.out.println(scroll.actions.size());
            System.out.println(scroll.actions.get(i).type);
            if (scroll.actions.get(i).type.equalsIgnoreCase("item")){
                if (scroll.actions.get(i).name.equalsIgnoreCase("BlessArmor")){
                    for (int k = 0; k < player.pack.size(); k++){
                        Item item = player.pack.get(k);
                        if (item instanceof Armor){
                            if (item.worn){
                                item.v += scroll.actions.get(i).v;
                                for (int j = 0; j < 150; j++) {
                                    displayGrid.removeObjectFromDisplay( j, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                                StringBuilder packstr = new StringBuilder("Info: " + scroll.actions.get(i).msg);
                                for (int l = 0; l < packstr.length(); l++){
                                    displayGrid.addObjectToDisplay(new Char(packstr.charAt(l)), l, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                            } else {
                                for (int j = 0; j < 150; j++) {
                                    displayGrid.removeObjectFromDisplay( j, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                                StringBuilder packstr = new StringBuilder("Info: Scroll had no effect because no armor is worn!");
                                for (int l = 0; l < packstr.length(); l++){
                                    displayGrid.addObjectToDisplay(new Char(packstr.charAt(l)), l, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                                }
                            }
                        }
                    }
                }
                if (scroll.actions.get(i).name.equalsIgnoreCase("hallucinate")){
                    hallucinateMode = true;
                    hCheck = scroll.actions.get(i).v;
                    hmoves = 0;
                }
            }
        }
    }

    public void itemPick(int playerX, int playerY) {
        Displayable p = displayGrid.getItem(playerX, playerY);
        System.out.println(p.getClass());
        if (p instanceof Item){
            player.addItem((Item) p, ((Item) p).name);
            for (int i = 0; i < 100; i++) {
                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
            StringBuilder packstr = new StringBuilder("Info: " + p.toString() + " added to pack");
            for (int k = 0; k < packstr.length(); k++){
                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
        }
    }

    public void itemDrop(int playerX, int playerY, int ch1){
//        int ch = ch1;
//        int ch_n = ch1 - 49;
//        ch = ch_n;
        System.out.println("number is " + (int) ch1);
        int size = player.pack.size();
        System.out.println(size);
        if (ch1 < size){
            System.out.println("IF STATEMENT WORKS");
            Item it = player.pack.remove(ch1);
            displayGrid.dropItem(it, playerX, playerY);

            for (int i = 0; i < 100; i++) {
                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
            StringBuilder packstr = new StringBuilder("Info: " + it.toString() + " dropped!");
            for (int k = 0; k < packstr.length(); k++){
                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
        } else {
            for (int i = 0; i < 100; i++) {
                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
            StringBuilder packstr = new StringBuilder("Info: Item index out of range!" );
            for (int k = 0; k < packstr.length(); k++){
                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
        }
        //System.out.println("Here");


        drop = !drop;
    }

    public void showPack(){
        if (displayPack){
            if (player.pack.size() > 0){
                for (int i = 0; i < 100; i++) {
                    displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight)-3);
                }
                StringBuilder packstr = new StringBuilder("Pack:");
                ArrayList<Item> pack = player.pack;
                for (int j = 0; j < pack.size(); j++){
                    //System.out.println(pack.get(j).worn);
                    String it_str = " " + Integer.toString(j+1) + ": " + pack.get(j).returnName();

                    if (j != pack.size() - 1){
                        it_str += ", ";
                    }
                    packstr.append(it_str);
                }
                for (int k = 0; k < packstr.length(); k++){
                    displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight)-3);
                }


            } else {

                for (int i = 0; i < 100; i++) {
                    displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                }
                StringBuilder packstr = new StringBuilder("Info: Pack is empty!");
                for (int k = 0; k < packstr.length(); k++){
                    displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                }
            }
            //displayGrid.addObjectToDisplay();
        } else {
            for (int i = 0; i < 100; i++) {
                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight)-3);
            }
            StringBuilder packstr = new StringBuilder("Pack:");
            for (int k = 0; k < packstr.length(); k++){
                displayGrid.addObjectToDisplay(new Char(packstr.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight)-3);
            }
            for (int i = 0; i < 100; i++) {
                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
            StringBuilder packstr2 = new StringBuilder("Info: ");
            for (int k = 0; k < packstr2.length(); k++){
                displayGrid.addObjectToDisplay(new Char(packstr2.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
        }
    }

    public void playerm(int posX, int posY, Player player){
        ArrayList<Char> hchars = new ArrayList<Char>();
        hchars.add(new Char(']'));
        hchars.add(new Char(')'));
        hchars.add(new Char('?'));
        hchars.add(new Char('T'));
        hchars.add(new Char('S'));
        hchars.add(new Char('H'));
        hchars.add(new Char('@'));
        ArrayList<Displayable> hspaces = new ArrayList<Displayable>();
        Displayable space = null;
        char spaceChar;
        int length = displayGrid.getlength();
        int width = displayGrid.getzlength();
        for (int x = 0; x < dungeon.width; x++) {
            for (int y = 0; y < dungeon.gameHeight; y++) {
                space = displayGrid.getObjectFromDisplay(x, y);
                spaceChar = space.getChar();
                for (Char h : hchars){
                    if (h.getChar() == spaceChar){
                        hspaces.add(space);
                    }
                }
            }
        }

        if (hallucinateMode){
            if (hmoves < hCheck){
                displayGrid.hallucinate(hchars, hspaces);
            } else {
                displayGrid.stop(hspaces);
            }
        }



        Object p = displayGrid.getObjectFromDisplay(posX, posY);
        System.out.println(p.getClass());
        Displayable s = (Displayable) p;
        System.out.println("Class of displayable is " + p.getClass());
        moveCount += 1;
        hmoves += 1;
        if (moveCount >= player.getHpm()){
            player.Hp = player.Hp + 1;
            moveCount = 0;
        }
        if ((s.getChar()) != 'X' && (s.getChar() != ' ')){
            char check = s.getChar();
            if (check == 'T' || check == 'S' || check == 'H'){
                if (((Displayable) p).Hp > 0){
                    fight((Monster) p, player, posX, posY);
                    System.out.println("Creature here");
                }

            } else {
                System.out.println(player.getHpm());
                for (int i = 0; i < 100; i++){
                    displayGrid.removeObjectFromDisplay(i, 0);
                }
                String top = "HP: " + player.Hp + " core: " + player.score;
                for (int i = 0; i < top.length(); i++){
                    displayGrid.addObjectToDisplay(new Char(top.charAt(i)), i, 0);
                }
                Displayable removed = displayGrid.removeObjectFromDisplay(playerX, playerY);
                displayGrid.addObjectToDisplay(new Char('@'), posX, posY);
                this.playerX = posX;
                this.playerY = posY;
            }
        }
    }

    public void fight(Monster monster, Player player, int posX, int posY){
        System.out.println("Player fighting monster");
        Random random = new Random();
        int dam = random.nextInt(player.maxHit);
        dam += 1;
        for (int i = 0; i < player.pack.size(); i++){
            Item item = player.pack.get(i);
            if (item instanceof Sword){
                if (item.wielded){
                    System.out.println("Before armor: " + dam);
                    dam += item.v;
                    System.out.println("After armor: " + dam);
                }
            }
        }
        if (dam != 0){
            monster.setHp(monster.Hp - dam);
            if (monster.Hp <= 0){
                for (int a = 0; a < monster.creatureActions.size(); a++){
                    CreatureAction creatureAction = monster.creatureActions.get(a);
                    if (creatureAction.type.equalsIgnoreCase("death")){
                        if (creatureAction.name.equalsIgnoreCase("remove")){
                            displayGrid.removeObjectFromDisplay(posX, posY);
                            displayGrid.addObjectToDisplay(new Char('.'), posX, posY);
                        } else if (creatureAction.name.equalsIgnoreCase("YouWin")){
                            for (int i = 0; i < 100; i++) {
                                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr2 = new StringBuilder("Info: " + creatureAction.msg);
                            for (int k = 0; k < packstr2.length(); k++){
                                displayGrid.addObjectToDisplay(new Char(packstr2.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }

                            try {
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < 100; i++) {
                                displayGrid.removeObjectFromDisplay( i, 0);
                            }
                            //System.out.println("Score" + player.score);
                            player.score = player.score + creatureAction.v;
                            //System.out.println("Score" + player.score);
                            StringBuilder packstr3 = new StringBuilder("Hp: " + player.score + " " + "Core: " + player.score);
                            for (int k = 0; k < packstr3.length(); k++){
                                displayGrid.addObjectToDisplay(new Char(packstr3.charAt(k)), k, 0);
                            }

                        }

                    }
                }
            } else {
                for (int i = 0; i < 100; i++) {
                    displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                }
                StringBuilder packstr2 = new StringBuilder("Info: " + "Player hit Monster for " + dam + " " + "damage! ");
                for (int k = 0; k < packstr2.length(); k++){
                    displayGrid.addObjectToDisplay(new Char(packstr2.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                }
                for (int b = 0; b < monster.creatureActions.size(); b++){
                    if (monster.creatureActions.get(b).type.equalsIgnoreCase("hit")){
                        if (monster.creatureActions.get(b).name.equalsIgnoreCase("Teleport")){
                            ArrayList<Displayable> teleports = new ArrayList<Displayable>();
                            Displayable s = null;
                            char schar;
                            int length = displayGrid.getlength();
                            int width = displayGrid.getzlength();
                            for (int i = 0; i < length; i++){
                                for (int j = 0; j < width; j++){
                                    s = displayGrid.getObjectFromDisplay(i, j);
                                    schar = s.getChar();
                                    if ((schar == '.') || (schar == '+') || (schar == '#')){
                                        teleports.add(s);
                                    }
                                }
                            }
                            Displayable tel = teleports.get(random.nextInt(teleports.size()));
                            int teleX = tel.dispPosX;
                            int teleY = tel.dispPosY;
                            Displayable removed = displayGrid.removeObjectFromDisplay(posX, posY);
                            displayGrid.addObjectToDisplay(removed, teleX, teleY);
                            System.out.println(teleX + " " + teleY);
                        }
                    }
                }
            }
        }
        Random rand2 = new Random();
        int dam2 = rand2.nextInt(monster.maxHit);
        dam2 += 1;
        for (int i = 0; i < player.pack.size(); i++){
            Item item = player.pack.get(i);
            if (item instanceof Armor){
                if (item.worn){
                    //System.out.println("Before armor: " + dam2);
                    dam2 -= item.v;
                    if (dam2 < 0){
                        dam2 = 0;
                    }
                    //System.out.println("After armor: " + dam2);
                }
            }
        }
        if (dam2 != 0){
            player.setHp(player.Hp - dam);
            if (player.Hp <= 0){

                for (int a = 0; a < player.creatureActions.size(); a++) {
                    CreatureAction creatureAction = player.creatureActions.get(a);
                    if (creatureAction.type.equalsIgnoreCase("death")){
                        if (creatureAction.name.equalsIgnoreCase("remove")){
                            displayGrid.removeObjectFromDisplay(playerX, playerY);
                            displayGrid.addObjectToDisplay(new Char('.'), playerX, playerY);
                        }
                        if (creatureAction.name.equalsIgnoreCase("YouWin")){
                            for (int i = 0; i < 100; i++) {
                                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr4 = new StringBuilder("Info: " + creatureAction.msg);
                            for (int k = 0; k < packstr4.length(); k++){
                                displayGrid.addObjectToDisplay(new Char(packstr4.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }

                        }
                        if (creatureAction.name.equalsIgnoreCase("EndGame")){
                            for (int i = 0; i < 100; i++) {
                                displayGrid.removeObjectFromDisplay( i, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            StringBuilder packstr4 = new StringBuilder("Info: " + creatureAction.msg);
                            for (int k = 0; k < packstr4.length(); k++){
                                displayGrid.addObjectToDisplay(new Char(packstr4.charAt(k)), k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
                            }
                            if (!endGame){
                                endGame = !endGame;
                            }
                        }
                        if (creatureAction.name.equalsIgnoreCase("ChangeDisplayedType")){
                            player.type = creatureAction.c;
                            if (!endGame){
                                endGame = !endGame;
                            }
                        }
                        if (creatureAction.name.equalsIgnoreCase("UpdateDisplay")){
                            Displayable removed = displayGrid.removeObjectFromDisplay(playerX, playerY);
                            displayGrid.addObjectToDisplay(new Char(player.type), playerX, playerY);
                            if (!endGame){
                                endGame = !endGame;
                            }
                        }
                        for (int i = 0; i < 100; i++) {
                            displayGrid.removeObjectFromDisplay( i, 0);
                        }
                        //System.out.println("Score" + player.score);
                        player.score = player.score + creatureAction.v;
                        //System.out.println("Score" + player.score);
                        StringBuilder packstr3 = new StringBuilder("Hp: " + player.score + " " + "Core: " + player.score);
                        for (int k = 0; k < packstr3.length(); k++){
                            displayGrid.addObjectToDisplay(new Char(packstr3.charAt(k)), k, 0);
                        }
                    }

                }
            } else {
                for (int i = 0; i < 100; i++) {
                    displayGrid.removeObjectFromDisplay( i, 0);
                }
                String top = "HP: " + player.Hp + " core: " + "0";
                for (int i = 0; i < top.length(); i++){
                    displayGrid.addObjectToDisplay(new Char(top.charAt(i)), i, 0);
                }
                for (int a = 0; a < player.creatureActions.size(); a++) {
                    CreatureAction creatureAction = player.creatureActions.get(a);
                    if (creatureAction.type.equalsIgnoreCase("hit")) {
                        if (creatureAction.name.equalsIgnoreCase("dropPack")){
                            itemDropaction(playerX, playerY, 0);
                            System.out.println("Item dropped");
                        }
                    }
                    if (creatureAction.type.equalsIgnoreCase("hit")) {
                        if (creatureAction.name.equalsIgnoreCase("emptyPack")){
                            while(player.pack.size() > 0){
                                itemDropaction(playerX, playerY, 0);
                                System.out.println("Item dropped");
                            }
                        }
                    }
                }
            }
        }


    }

    private void itemDropaction(int playerX, int playerY, int i) {
        //System.out.println("number is " + (int) ch1);
        int size = player.pack.size();
        System.out.println(size);
        if (i < size){
            Item it = player.pack.remove(i);
            displayGrid.dropItem(it, playerX, playerY);

            for (int k = 0; k < 100; k++) {
                displayGrid.removeObjectFromDisplay( k, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
            StringBuilder packstr = new StringBuilder("Info: " + it.toString() + " dropped!");
            for (int j = 0; j < packstr.length(); j++){
                displayGrid.addObjectToDisplay(new Char(packstr.charAt(j)), j, (dungeon.gameHeight + dungeon.topHeight + dungeon.bottomHeight));
            }
        } else {
            System.out.println("Pack empty");
        }


        //drop = !drop;
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
