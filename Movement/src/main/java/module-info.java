import dk.sdu.mmmi.cbse.common.movement.MovementSPI;

module Movement {
    requires Common;
    requires CommonMovement;
    requires CommonGameControlls;
    uses dk.sdu.mmmi.cbse.common.movement.MovementSPI;
    uses dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds;
    provides MovementSPI with dk.sdu.mmmi.cbse.movement.Movement;
}