package dk.sdu.mmmi.cbse.common.movement;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface MovementSPI {
    void controlMovement(Entity entity, GameData gameData);
}