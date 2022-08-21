package src;

import game.Char;

public class Displayable {

    public int maxHit;
    public int hpMoves;
    public int Hp;
    public char type;
    public int v;
    public int x;
    public int y;
    public int width;
    public int height;
    public int dispPosX;
    public int dispPosY;
    private boolean hallucinate = false;
    private char hChar = 'X';
    public Displayable(){
    }

    public void setInvisible(){
    }

    public void setVisible(){}

    public void setMaxHit(int maxHit){
        this.maxHit = maxHit;
    }

    public void setHpMove(int hpMoves){
        this.hpMoves = hpMoves;
    }

    public void setHp(int Hp){
        this.Hp = Hp;
    }

    public void setType(char t){
        this.type = t;
    }

    public void setIntValue(int v){
        this.v = v;
    }

    public void setPosX(int x){
        this.x = x;
    }

    public void setPosY(int y){
        this.y = y;
    }

    public void setWidth(int x){
        this.width = x;
    }

    public void setHeight(int y){
        this.height = y;
    }

    public char getChar() {
        if (hallucinate) {
            return getHChar();
        } else {
            return getType();
        }
    }

    public void setHallucinate(boolean val) {
        hallucinate = val;
    }

    public void setHChar(char _hChar) {
        hChar = _hChar;
    }

    public char getHChar() {
        return hChar;
    }

    public char getType() {
        return type;
    }

    public int getHpm(){
        return this.hpMoves;
    }
}
