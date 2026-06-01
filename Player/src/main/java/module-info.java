import dk.sdu.mmmi.cbse.common.services.IProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPluginService;
import dk.sdu.mmmi.cbse.playersystem.PlayerProcessor;

module Player {
    requires Common;
    requires CommonBullet;
    requires CommonMovement;
    requires CommonGameControlls;
    requires CommonPlayer;
    uses dk.sdu.mmmi.cbse.common.movement.MovementSPI;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    uses dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds;
    provides IPluginService with dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
    provides IProcessingService with PlayerProcessor;
    exports dk.sdu.mmmi.cbse.playersystem;
}
