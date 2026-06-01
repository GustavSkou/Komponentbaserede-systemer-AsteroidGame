package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletOwner;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IProcessingService;

public class BulletControlSystem implements IProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            bullet.setX(bullet.getX() + bullet.getXVelocity() * gameData.getDeltaTime());
            bullet.setY(bullet.getY() + bullet.getYVelocity() * gameData.getDeltaTime());
            removeOutOffBoundsBullets(bullet, gameData, world);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setPolygonCoordinates(
                1, -1,
                1, 1,
                -1, 1,
                -1, -1);
        double changeX = Math.cos(Math.toRadians(shooter.getRotation()));
        double changeY = Math.sin(Math.toRadians(shooter.getRotation()));

        // add the shoots velocity to the bullets "normal" speed
        bullet.setXVelocity(shooter.getXVelocity() + changeX * 50 );
        bullet.setYVelocity(shooter.getYVelocity() + changeY * 50);
        
        bullet.setX(shooter.getX() + changeX * shooter.getRadius() * 3);
        bullet.setY(shooter.getY() + changeY * shooter.getRadius() * 3);

        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(1);
        ((Bullet) bullet).setOwner(resolveOwner(shooter));

        return bullet;
    }

    private BulletOwner resolveOwner(Entity shooter) {
        if ("Player".equals(shooter.getClass().getSimpleName())) {
            return BulletOwner.PLAYER;
        }
        return BulletOwner.ENEMY;
    }

    private void removeOutOffBoundsBullets(Entity bullet, GameData gameData, World world) {
        if (bullet.getX() > gameData.getDisplayWidth() || bullet.getX() < 0) {
            world.removeEntity(bullet);
        } else if (bullet.getY() > gameData.getDisplayHeight() || bullet.getY() < 0) {
            world.removeEntity(bullet);
        }
    }
}
