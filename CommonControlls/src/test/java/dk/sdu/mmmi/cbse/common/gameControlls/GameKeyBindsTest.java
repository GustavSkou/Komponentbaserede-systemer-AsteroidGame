package dk.sdu.mmmi.cbse.common.gameControlls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GameKeyBindsTest {

    private static final class TestGameKeyBinds extends GameKeyBinds {

        private final boolean down;

        private TestGameKeyBinds(boolean down) {
            this.down = down;
            UP = 10;
            LEFT = 11;
            RIGHT = 12;
            SPACE = 13;
        }

        @Override
        public boolean isDown(int k) {
            return down;
        }
    }

    @Test
    void configuredKeyBinds() {
        TestGameKeyBinds keyBinds = new TestGameKeyBinds(true);

        assertEquals(10, keyBinds.getUP());
        assertEquals(11, keyBinds.getLEFT());
        assertEquals(12, keyBinds.getRIGHT());
        assertEquals(13, keyBinds.getSPACE());
    }

    @Test
    void KeyStateIsDown() {
        TestGameKeyBinds keyBinds = new TestGameKeyBinds(true);

        assertTrue(keyBinds.isDown(keyBinds.getSPACE()));
    }
}