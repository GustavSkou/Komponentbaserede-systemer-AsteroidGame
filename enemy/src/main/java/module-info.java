import dk.sdu.mmmi.cbse.common.services.IPluginService;
import dk.sdu.mmmi.cbse.common.services.IPostProcessingService;
import dk.sdu.mmmi.cbse.common.services.IProcessingService;

module Enemy {
    exports dk.sdu.mmmi.cbse.enemy;
    requires Common;
    requires CommonEnemy;
    requires CommonBullet;
    requires CommonPlayer;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides IPluginService with dk.sdu.mmmi.cbse.enemy.EnemyPlugin;
    provides IProcessingService with dk.sdu.mmmi.cbse.enemy.EnemyProcessor;
    provides IPostProcessingService with dk.sdu.mmmi.cbse.enemy.EnemyPostProcessor;
}
