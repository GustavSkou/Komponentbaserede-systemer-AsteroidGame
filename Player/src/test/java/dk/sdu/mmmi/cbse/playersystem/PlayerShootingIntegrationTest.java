package dk.sdu.mmmi.cbse.playersystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dk.sdu.mmmi.cbse.common.data.GameData;
import javafx.scene.layout.Pane;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds;

public class PlayerShootingIntegrationTest {

    private static final class TestKeyBinds extends GameKeyBinds {
        public TestKeyBinds() {
            this.SPACE = 32;
        }
        @Override
        public boolean isDown(int k) {
            return k == this.SPACE;
        }
    }

    @Test
    void testPlayerShootsBullet() {
        GameData gameData = new GameData();
        gameData.setKeyBinds(new TestKeyBinds());
        Pane pane = new Pane();
        pane.setPrefSize(800, 600);
        gameData.setPane(pane);

        World world = new World();
        Player player = new Player();
        player.setX(100);
        player.setY(100);
        world.addEntity(player);

        assertEquals(1, world.getEntities().size());

        PlayerProcessor processor = new PlayerProcessor();
        processor.process(gameData, world);

        assertEquals(2, world.getEntities().size());
    }
}
