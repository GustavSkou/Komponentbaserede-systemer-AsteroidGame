package dk.sdu.mmmi.cbse.common.gameControlls;

public abstract class GameKeyBinds {

    public int UP;
    public int LEFT;
    public int RIGHT;
    public int SPACE;

    public int getUP() {
        return UP;
    }

    public int getLEFT() {
        return LEFT;
    }

    public int getRIGHT() {
        return RIGHT;
    }

    public int getSPACE() {
        return SPACE;
    }

    public abstract boolean isDown(int k);
}
