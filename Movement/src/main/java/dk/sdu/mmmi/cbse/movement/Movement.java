package dk.sdu.mmmi.cbse.movement;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds;
import dk.sdu.mmmi.cbse.common.movement.MovementSPI;

public class Movement implements MovementSPI {

    @Override
    public void controlMovement(Entity entity, GameData gameData) {
        GameKeyBinds keys = gameData.getKeyBinds();
        if (keys == null) {
            throw new IllegalStateException("Game keybinds have not been initialized.");
        }

        if (keys.isDown(keys.getLEFT())) {
            entity.setRotation(entity.getRotation() - 3);
        }

        if (keys.isDown(keys.getRIGHT())) {
            entity.setRotation(entity.getRotation() + 3);
        }

        if (keys.isDown(keys.getUP())) {
            entity.setNormalizedSpeed(entity.getNormalizedSpeed() + 0.01);
        } else {
            entity.setNormalizedSpeed(entity.getNormalizedSpeed() - 0.05);
        }
    }
}