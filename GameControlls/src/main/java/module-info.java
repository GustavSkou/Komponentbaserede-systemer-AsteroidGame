import dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBindsSPI;

module GameControlls {
    requires CommonGameControlls;
    requires javafx.graphics;
    uses dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBindsSPI;
    provides GameKeyBindsSPI with dk.sdu.mmmi.cbse.gameKeyBinds.GameKeyboardKeyBinds;
}