package src;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DungeonXMLHandler extends DefaultHandler{

    private static final int DEBUG = 1;
    private static final String CLASSID = "DungeonXMLHandler";


    public Dungeon dungeon = null;
    private List<Room> rooms = new ArrayList<Room>();
    private Stack<Displayable> displayableBeingParsed = new Stack<Displayable>();
    private Passage passageBeingParsed = null;
    private Creature creatureBeingParsed = null;
    private Item itemBeingParsed = null;
    private Action actionBeingParsed = null;
    private boolean visible = false;
    public Dungeon getDungeon(){
        return dungeon;
    }
    private Room roomBeingParsed = null;
    public DungeonXMLHandler(){
    }
    private StringBuilder data = null;
    @Override
    public void startElement(String url, String localName, String qName, Attributes attributes) throws SAXException {
        if (DEBUG > 1){
            System.out.println(CLASSID + ".startElement qName: " + qName);
        }

        if (qName.equalsIgnoreCase("Dungeon")){
            String name = attributes.getValue("name");
            int width = Integer.parseInt(attributes.getValue("width"));
            int topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            int gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            int bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));
            dungeon = new Dungeon(name, width, gameHeight, topHeight, bottomHeight);
        } else if (qName.equalsIgnoreCase("Rooms")){

        } else if(qName.equalsIgnoreCase("Room")){
            String id = attributes.getValue("room");
            Room room = new Room(id);
            dungeon.addRoom(room);
            System.out.println("Room list size: " + dungeon.rooms.size());
            displayableBeingParsed.push(room);
            roomBeingParsed = room;
        } else if (qName.equalsIgnoreCase("Monster")){
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Creature monster = null;
            monster = new Monster(name);
            ((Monster) monster).setID(room, serial);
            creatureBeingParsed = monster;
            roomBeingParsed.addCreature(creatureBeingParsed);
            displayableBeingParsed.push(monster);
            dungeon.addCreature(monster);
        } else if (qName.equalsIgnoreCase(("Player"))) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Player player = new Player();
            dungeon.addCreature(player);
            creatureBeingParsed = player;
            displayableBeingParsed.push(player);
            roomBeingParsed.addPlayer(player);
            player.setRoom(room);
            player.setSerial(serial);
        } else if (qName.equalsIgnoreCase("Passages")){

        } else if (qName.equalsIgnoreCase("Passage")){
            int room1 = Integer.parseInt(attributes.getValue("room1"));
            int room2 = Integer.parseInt(attributes.getValue("room2"));
            Passage passage = new Passage();
            passage.setID(room1, room2);
            displayableBeingParsed.push(passage);
            passageBeingParsed = passage;
            dungeon.addPassage(passage);
        } else if (qName.equalsIgnoreCase("CreatureAction")){
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            CreatureAction creatureaction = new CreatureAction(creatureBeingParsed);
            creatureaction.setName(name);
            creatureaction.setType(type);
            creatureBeingParsed.addCreatureAction(creatureaction);
            actionBeingParsed = creatureaction;
        } else if (qName.equalsIgnoreCase("Armor")){
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Armor armor = new Armor(name);
            displayableBeingParsed.push(armor);
            itemBeingParsed = armor;
            System.out.println("ARMOR BEING DETECTED: " + armor.name);
            if(creatureBeingParsed != null){
                if (creatureBeingParsed.getClass() == Player.class){
                    Player player = (Player) creatureBeingParsed;
                    player.addItem(armor, name);
                }
            } else {
                dungeon.addItem(armor, name);
            }

        } else if (qName.equalsIgnoreCase("Sword")){
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Sword sword = new Sword(name);
            sword.setID(room, serial);
            displayableBeingParsed.push(sword);
            itemBeingParsed = sword;
            if (creatureBeingParsed != null){
                if (creatureBeingParsed.getClass() == Player.class){
                    Player player = (Player) creatureBeingParsed;
                    player.addItem(sword, name);
                }
            } else {
                dungeon.addItem(sword, name);
            }
//            if (creatureBeingParsed.getClass() == Player.class){
//                Player player = (Player) creatureBeingParsed;
//                player.setWeapon(sword);
//            } else {
//                dungeon.addItem(sword);
//            }
        } else if (qName.equalsIgnoreCase("Scroll")){
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Scroll scroll = new Scroll(name);
            scroll.setID(room, serial);
            if (creatureBeingParsed != null){
                if (creatureBeingParsed.getClass() == Player.class){
                    Player player = (Player) creatureBeingParsed;
                    player.addItem(scroll, name);
                }
            } else {
                dungeon.addItem(scroll, name);
            }
            //dungeon.addItem(scroll);
            displayableBeingParsed.push(scroll);
            itemBeingParsed = scroll;
        } else if (qName.equalsIgnoreCase("ItemAction")){
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            ItemAction action = new ItemAction ((Item) itemBeingParsed);
            action.setName(name);
            action.setType(type);
            itemBeingParsed.addAction(action);
            actionBeingParsed = action;
        }

        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        Displayable displayable = displayableBeingParsed;
//        Action action = actionBeingParsed;
//        Item item = itemBeingParsed;
//        Creature creature = creatureBeingParsed;
        if (qName.equalsIgnoreCase("Room")) {
            roomBeingParsed = null;
            displayableBeingParsed.pop();
        } else if (qName.equalsIgnoreCase("Passage")) {
            passageBeingParsed = null;
            displayableBeingParsed.pop();
        } else if (qName.equalsIgnoreCase("Player")
                || qName.equalsIgnoreCase("Monster")) {
            creatureBeingParsed = null;
            displayableBeingParsed.pop();
        } else if (qName.equalsIgnoreCase("CreatureAction")
                || qName.equalsIgnoreCase("ItemAction")) {
            actionBeingParsed = null;
        } else if (qName.equalsIgnoreCase("Armor")
                || qName.equalsIgnoreCase("Scroll")
                || qName.equalsIgnoreCase("Sword")) {
            itemBeingParsed = null;
            displayableBeingParsed.pop();
        }

        if (qName.equalsIgnoreCase("posX")){
            displayableBeingParsed.peek().setPosX(Integer.parseInt(data.toString()));
        } else if (qName.equalsIgnoreCase("posY")){
            displayableBeingParsed.peek().setPosY(Integer.parseInt(data.toString()));
        } else if (qName.equalsIgnoreCase("type")){
            creatureBeingParsed.setType(data.charAt(0));
        } else if (qName.equalsIgnoreCase("visible")){
            visible = false;
        } else if (qName.equalsIgnoreCase("hp")){
            creatureBeingParsed.setHp(Integer.parseInt(data.toString()));
        } else if (qName.equals("hpMoves")){
            ((Player) creatureBeingParsed).setHpMove(Integer.parseInt(data.toString()));
        } else if (qName.equalsIgnoreCase("maxhit")){
            creatureBeingParsed.setMaxHit(Integer.parseInt(data.toString()));
        } else if (qName.equalsIgnoreCase("actionMessage")){
            actionBeingParsed.setMessage(data.toString());
        } else if (qName.equalsIgnoreCase("width")){
            displayableBeingParsed.peek().setWidth(Integer.parseInt(data.toString()));
        } else if (qName.equalsIgnoreCase("height")){
            displayableBeingParsed.peek().setHeight(Integer.parseInt(data.toString()));
        } else if (qName.equalsIgnoreCase("actionIntValue")){
            actionBeingParsed.setIntValue(Integer.parseInt(data.toString()));
        } else if (qName.equalsIgnoreCase("itemIntValue")){
            itemBeingParsed.setIntValue(Integer.parseInt(data.toString()));
        } else if (qName.equalsIgnoreCase("actionCharValue")) {
            actionBeingParsed.setCharValue(data.charAt(0));
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
