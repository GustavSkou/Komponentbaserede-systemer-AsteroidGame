package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.services.IProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyProcessor implements IProcessingService { 

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            enemy.setX(enemy.getX() + enemy.getXDirection() * 0.5 * gameData.getDeltaTime());
            enemy.setY(enemy.getY() + enemy.getYDirection() * 0.5 * gameData.getDeltaTime());

            enemy.setRotation(rotationToPlayer(enemy, world));

            int randomInt = random.nextInt(60);
            if (randomInt == 1) {

                getBulletSPIs().stream().findFirst().ifPresent(
                    spi -> {world.addEntity(spi.createBullet(enemy, gameData));}
                );
            }
        }
    }

    private double rotationToPlayer(Entity entity, World world) {
        double degrees = 0;
        for (Entity player : world.getEntities(Player.class)) {
            double a = entity.getX() - player.getX();
            double b = entity.getY() - player.getY();
            double radians = Math.atan2 (b, a);
            degrees = Math.toDegrees(radians) + 180;
        }
        return degrees;
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}