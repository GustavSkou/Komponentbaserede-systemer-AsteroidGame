package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostProcessingService;

import java.util.Random;

public class AsteroidPostProcessor implements IPostProcessingService {
    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        while (world.getEntities(dk.sdu.mmmi.cbse.common.asteroid.Asteroid.class).size() < 4) {
            Entity asteroid = new dk.sdu.mmmi.cbse.common.asteroid.Asteroid();
            asteroid.setX(random.nextInt(gameData.getDisplayWidth()));
            asteroid.setY(1);
            world.addEntity(asteroid);
        }
    }
}
