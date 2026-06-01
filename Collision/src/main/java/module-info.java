import dk.sdu.mmmi.cbse.common.services.IPostProcessingService;

module Collision {
    requires Common;
    requires CommonAsteroid;
    requires CommonPlayer;
    requires CommonBullet;
    requires CommonEnemy;
    requires java.net.http;
    uses dk.sdu.mmmi.cbse.common.asteroid.AsteroidSplitterSPI;
    provides IPostProcessingService with dk.sdu.mmmi.cbse.collision.CollisionProcessor;
}
