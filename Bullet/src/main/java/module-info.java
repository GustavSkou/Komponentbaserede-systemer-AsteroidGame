import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPluginService;

module Bullet {
    requires Common;
    requires CommonBullet;
    provides IPluginService with dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
    provides BulletSPI with dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
    provides IProcessingService with dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
}