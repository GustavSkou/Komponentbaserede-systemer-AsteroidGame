package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPluginService;

public class EnemyPlugin implements IPluginService {

    @Override
    public void IStart(GameData gameData, World world) {
        /*
        Entity enemy = new Enemy();
        enemy.setX(100);
        enemy.setY(200);
        world.addEntity(enemy);*/
    }

    @Override
    public void ICleanUp(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }
}
