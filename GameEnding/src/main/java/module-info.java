import dk.sdu.mmmi.cbse.common.gameEnding.GameEndingSPI;
import dk.sdu.mmmi.cbse.common.services.IPluginService;

module GameEnding {
    requires Common;
    requires javafx.graphics;
    requires java.net.http;

    uses IPluginService;
    provides GameEndingSPI with dk.sdu.mmmi.cbse.gameending.GameEnding;
}
