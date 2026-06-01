package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPluginService;

public class AsteroidPlugin implements IPluginService {
    @Override
    public void IStart(GameData gameData, World world) {

    }

    public void ICleanUp(GameData gameData, World world) {
        for (Entity entity : world.getEntities(dk.sdu.mmmi.cbse.common.asteroid.Asteroid.class)) {
            world.removeEntity(entity);
        }
    }
}