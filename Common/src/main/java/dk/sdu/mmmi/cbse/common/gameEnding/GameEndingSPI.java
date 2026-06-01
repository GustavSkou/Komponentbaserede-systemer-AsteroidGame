package dk.sdu.mmmi.cbse.common.gameEnding;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface GameEndingSPI {
    void endGame(GameData gameData, World world);
}
