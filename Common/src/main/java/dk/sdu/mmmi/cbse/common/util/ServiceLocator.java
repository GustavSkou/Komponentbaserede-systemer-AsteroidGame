package dk.sdu.mmmi.cbse.common.util;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public enum ServiceLocator {

    INSTANCE;

    private static final Map<Class, ServiceLoader> serviceLoaderRegister = new HashMap<>();
    private final ModuleLayer moduleLayer;

    ServiceLocator() {
        try {
            Path pluginsDir = Paths.get("plugins"); // Directory with plugins JARs

            // Search for plugins in the plugins directory
            ModuleFinder pluginsFinder = ModuleFinder.of(pluginsDir);

            // Find all names of all found plugin modules
            List<String> plugins = pluginsFinder
                    .findAll()
                    .stream()
                    .map(ModuleReference::descriptor)
                    .map(ModuleDescriptor::name)
                    .collect(Collectors.toList());

            // Create configuration that will resolve plugin modules
            // (verify that the graph of modules is correct)
            Configuration pluginsConfiguration = ModuleLayer
                    .boot()
                    .configuration()
                    .resolve(pluginsFinder, ModuleFinder.of(), plugins);

            // Create a module layer for plugins
            moduleLayer = ModuleLayer
                    .boot()
                    .defineModulesWithOneLoader(pluginsConfiguration, ClassLoader.getSystemClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public <T> List<T> locateAll(Class<T> serviceType) 
    {
        ServiceLoader<T> tServiceLoader = serviceLoaderRegister.get(serviceType);
        List<T> serviceInstances = new ArrayList<T>();

        // If there is no serviceloader for the type, we will try to find to and add it to the map
        if (tServiceLoader == null) {
            tServiceLoader = ServiceLoader.load(moduleLayer, serviceType);
            serviceLoaderRegister.put(serviceType, tServiceLoader);
        }

        try {
            for (T serviceInstance : tServiceLoader) {
                serviceInstances.add(serviceInstance);
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
        }

        return serviceInstances;
    }

}
