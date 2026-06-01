import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.services.IPluginService;
import dk.sdu.mmmi.cbse.common.services.IPostProcessingService;
import dk.sdu.mmmi.cbse.common.services.IProcessingService;

module Asteroid {
    exports dk.sdu.mmmi.cbse.asteroid;
    requires Common;
    requires CommonAsteroid;

    provides IProcessingService with dk.sdu.mmmi.cbse.asteroid.AsteroidProcessor;
    provides IPluginService with dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;
    provides IPostProcessingService with dk.sdu.mmmi.cbse.asteroid.AsteroidPostProcessor;

    provides AsteroidSplitterSPI with dk.sdu.mmmi.cbse.asteroid.AsteroidSplitter;
}