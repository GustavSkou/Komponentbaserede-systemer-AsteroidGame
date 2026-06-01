package dk.sdu.mmmi.cbse.gameKeyBinds;

import dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds;
import dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBindsSPI;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class GameKeyboardKeyBinds extends GameKeyBinds implements GameKeyBindsSPI {
    private static boolean[] keyState;

    private static final int KEYS = 4;


    public GameKeyboardKeyBinds() {
        UP = 0;
        LEFT = 1;
        RIGHT = 2;
        SPACE = 3;
        keyState = new boolean[KEYS];
    }

    public void update() { }

    public void setKey(int k, boolean b) {
        keyState[k] = b;
    }

    public boolean isDown(int k) {
        return keyState[k];
    }

    @Override
    public void setKeyBinds(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                setKey(LEFT, true);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                setKey(RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                setKey(UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                setKey(SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.LEFT)) {
                setKey(LEFT, false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                setKey(RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.UP)) {
                setKey(UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                setKey(SPACE, false);
            }

        });
    }
}