module Core {
    requires Common;
    requires CommonBullet;    
    requires javafx.graphics;   
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires java.desktop;
    requires CommonGameControlls;
    requires java.net.http;

    exports dk.sdu.mmmi.cbse.main;
    
    opens dk.sdu.mmmi.cbse.main to javafx.graphics,spring.core;
    
    uses dk.sdu.mmmi.cbse.common.gameEnding.GameEndingSPI;
    uses dk.sdu.mmmi.cbse.common.services.IPluginService;
    uses dk.sdu.mmmi.cbse.common.services.IProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostProcessingService;
    uses dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBindsSPI;
}