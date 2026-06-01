package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IPluginService {

    void IStart(GameData gameData, World world);

    void ICleanUp(GameData gameData, World world);
}
