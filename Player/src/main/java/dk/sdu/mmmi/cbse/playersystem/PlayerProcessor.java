package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds;
import dk.sdu.mmmi.cbse.common.movement.MovementSPI;
import dk.sdu.mmmi.cbse.common.services.IProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.*;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerProcessor implements IProcessingService {

    private static final double shotCooldown = 5;
    private double remainingShotCooldown = 0;

    @Override
    public void process(GameData gameData, World world) {
        remainingShotCooldown = Math.max(0, remainingShotCooldown - gameData.getDeltaTime());
        GameKeyBinds keybinds = gameData.getKeyBinds();
        if (keybinds == null) {
            throw new IllegalStateException("Game keybinds have not been initialized.");
        }


        for (Entity player : world.getEntities(Player.class)) {

            getMovementSPIs().stream().findFirst().ifPresent(
                movementSPI -> {
                    movementSPI.controlMovement(player, gameData);
                }
            );

            if (keybinds.isDown(keybinds.getSPACE()) && remainingShotCooldown <= 0) {
                getBulletSPIs().stream().findFirst().ifPresent(
                    bulletSPI -> {
                        world.addEntity(bulletSPI.createBullet(player, gameData));
                    }
                );

                remainingShotCooldown = shotCooldown;
            }

            double changeX = Math.cos(Math.toRadians(player.getRotation()));
            double changeY = Math.sin(Math.toRadians(player.getRotation()));

            // giv player exp fart, desto længere du holder frem
            double speedFactor = Math.pow(player.getNormalizedSpeed(), 2);
            player.setXVelocity(speedFactor * changeX * 5);
            player.setYVelocity(speedFactor * changeY * 5);

            player.setX(player.getX() + player.getXVelocity() * 10 * gameData.getDeltaTime());
            player.setY(player.getY() + player.getYVelocity() * 10 * gameData.getDeltaTime());

            if (player.getX() < 0) {
                player.setX(gameData.getDisplayWidth());
            } else if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(1);
            }

            if (player.getY() < 0) {
                player.setY(gameData.getDisplayHeight());
            } else if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(1);
            }                        
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends MovementSPI> getMovementSPIs() {
        return ServiceLoader.load(MovementSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
