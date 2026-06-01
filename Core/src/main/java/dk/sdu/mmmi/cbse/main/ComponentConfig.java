package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPluginService;
import dk.sdu.mmmi.cbse.common.services.IPostProcessingService;
import dk.sdu.mmmi.cbse.common.gameEnding.GameEndingSPI;
import java.util.List;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ComponentConfig {
    
    @Bean
    public Game game() {
        /*
            get the arguments using the serviceLoader to find the instaceses
        */
        return new Game ( 
            gamePluginServices(), 
            entityProcessingServiceList(), 
            postEntityProcessingServices(),
            gameEndingServices()
        );
    }

    @Bean
    public List<IPluginService> gamePluginServices() {
        return ServiceLoader.load( IPluginService.class )
            .stream()
            .map( ServiceLoader.Provider::get )
            .collect( toList() );
    }

    @Bean
    public List<IProcessingService> entityProcessingServiceList() {
        return ServiceLoader.load( IProcessingService.class )
            .stream()
            .map( ServiceLoader.Provider::get )
            .collect( toList() );
    }

    @Bean
    public List<IPostProcessingService> postEntityProcessingServices() {
        return ServiceLoader.load( IPostProcessingService.class )
            .stream()
            .map( ServiceLoader.Provider::get )
            .collect( toList() );
    }

    @Bean
    public List<GameEndingSPI> gameEndingServices() {
        return ServiceLoader.load( GameEndingSPI.class )
            .stream()
            .map( ServiceLoader.Provider::get )
            .collect( toList() );
    }
}
