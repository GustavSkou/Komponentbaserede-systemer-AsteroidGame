package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPluginService;
import dk.sdu.mmmi.cbse.common.player.Player;
public class PlayerPlugin implements IPluginService {

    private Entity player;

    @Override
    public void IStart(GameData gameData, World world) {
        player = new Player();
        player.setX((double) gameData.getDisplayHeight() /2);
        player.setY((double) gameData.getDisplayWidth() /2);
        world.addEntity(player);
    }

    @Override
    public void ICleanUp(GameData gameData, World world) {
        world.removeEntity(player);
    }
}