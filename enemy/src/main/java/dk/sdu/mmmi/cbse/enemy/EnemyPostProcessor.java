package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IPostProcessingService;

import java.util.Random;

public class EnemyPostProcessor implements IPostProcessingService {
    private final Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        if (world.getEntities(Enemy.class).isEmpty()) {
            Entity enemy = new Enemy();

            int maxX = Math.max(0, gameData.getDisplayWidth() - 1);
            int maxY = Math.max(0, gameData.getDisplayHeight() - 1);
            int randomValue = random.nextInt(4);

            if (randomValue == 0)  {
                enemy.setX(0);
                enemy.setY(random.nextInt(maxY + 1));
            } else if (randomValue == 1) {
                enemy.setX(maxX);
                enemy.setY(random.nextInt(maxY + 1));
            } else if (randomValue == 2) {
                enemy.setX(random.nextInt(maxX + 1));
                enemy.setY(0);
            } else {
                enemy.setX(random.nextInt(maxX + 1));
                enemy.setY(maxY);
            }

            world.addEntity(enemy);
        }
    }
}