package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {
    
    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        /* 
            Create the spring container context with the Config defined in the ComponentConfig class
        */
        try (AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(ComponentConfig.class)) {
            Game game = appContext.getBean(Game.class);
            game.start(window);
            game.render();
        }        

    }

}
