/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.gameEnding.GameEndingSPI;
import dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBindsSPI;
import dk.sdu.mmmi.cbse.common.services.IProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPluginService;
import dk.sdu.mmmi.cbse.common.services.IPostProcessingService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.util.stream.Collectors.toList;

class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private final List<IPluginService> gamePluginServices;
    private final List<IProcessingService> entityProcessingServiceList;
    private final List<IPostProcessingService> postEntityProcessingServices;
    private final List<GameEndingSPI> gameEndingServices;
    private AnimationTimer animationTimer;

    private Text scoreText, healthText;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ScheduledExecutorService poller = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "http-poller");
        t.setDaemon(true);
        return t;
    });

    Game(List<IPluginService> gamePluginServices, List<IProcessingService> entityProcessingServiceList, List<IPostProcessingService> postEntityProcessingServices, List<GameEndingSPI> gameEndingServices) {
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServiceList = entityProcessingServiceList;
        this.postEntityProcessingServices = postEntityProcessingServices;
        this.gameEndingServices = gameEndingServices;

        scoreText = new Text(10, 20, "Destroyed asteroids: " + httpGetScoreValue() );
        healthText = new Text(10, 40, "Health: " + httpGetHealthValue() );
    }

    public void start(Stage window) throws Exception {
        gameWindow.setPrefSize(900, 900);
        gameWindow.getChildren().add(scoreText);
        gameWindow.getChildren().add(healthText);

        Scene scene = new Scene(gameWindow);
        gameData.setPane(gameWindow);

        // Use the discovered keybind implementation as the shared key source for all modules.
        GameKeyBindsSPI gameKeyBindsSPI = getGameKeyBindsSPI().getFirst();
        if (!(gameKeyBindsSPI instanceof dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds)) {
            throw new IllegalStateException("GameKeyBindsSPI implementation must extend GameKeyBinds.");
        }
        dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds gameKeyBinds = (dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds) gameKeyBindsSPI;
        gameData.setKeyBinds(gameKeyBinds);
        gameKeyBindsSPI.setKeyBinds(scene);

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        // Ensure external health service is initialized to 1 when the game starts
        httpPost("http://localhost:8082/health?amount=" + 1);
        // Refresh displayed health value
        healthText.setText("Health: " + httpGetHealthValue() );
        window.show();

        // Schedule background polling for score and health (once per second).
        poller.scheduleAtFixedRate(() -> {
            try {
                long score = httpGetLong("http://localhost:8081/score", 0);
                long health = httpGetLong("http://localhost:8082/health", 1);
                Platform.runLater(() -> {
                    scoreText.setText("Destroyed asteroids: " + score);
                    healthText.setText("Health: " + health);
                });
            } catch (Exception ex) {
                // Swallow any exceptions from polling to avoid crashing the UI thread.
                ex.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);

        // Lookup all Game Plugins using ServiceLoader
        for (IPluginService iGamePlugin : getGamePluginServices()) {
            iGamePlugin.IStart(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }
    }

    public void render() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameData.updateDeltaTime(now);
                if (!gameData.isGameEnded() && hasGameEnded()) {
                    gameData.setGameEnded(true);
                    for (GameEndingSPI gameEndingService : getGameEndingServices()) {
                        gameEndingService.endGame(gameData, world);
                    }
                    animationTimer.stop();
                    httpPost("http://localhost:8081/reset");
                    return;
                }
                update();
                draw();
                scoreText.setText("Destroyed asteroids: " + httpGetScoreValue() );
                healthText.setText("Health: " + httpGetHealthValue() );
            }
        };
        if (!hasGameEnded())
            animationTimer.start();
    }

    private void update() {
        for (IProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        remove();
        place();
    }

    private void place() {
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    private void remove() {
        for (Entity polygonEntity : polygons.keySet()) {
            if (!world.getEntities().contains(polygonEntity)) {
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }
    }

    public List<IPluginService> getGamePluginServices() {
        return gamePluginServices;
    }

    public List<IProcessingService> getEntityProcessingServices() {
        return entityProcessingServiceList;
    }

    public List<IPostProcessingService> getPostEntityProcessingServices() {
        return postEntityProcessingServices;
    }

    public List<GameKeyBindsSPI> getGameKeyBindsSPI() {
        return ServiceLoader.load( GameKeyBindsSPI.class )
            .stream()
            .map( ServiceLoader.Provider::get )
            .collect( toList() );
    }

    public List<GameEndingSPI> getGameEndingServices() {
        return gameEndingServices;
    }

    private boolean hasGameEnded() {
        return httpGetHealthValue() <= 0;
    }

    private long httpGetScoreValue() {
        return httpGetLong("http://localhost:8081/score", 0);
    }

    private long httpGetHealthValue() {
        return httpGetLong("http://localhost:8082/health", 1);
    }

    private long httpGetLong(String uri, long fallbackValue) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            return Long.parseLong(body.trim());

        } catch (Exception ex) {
            ex.printStackTrace();
            return fallbackValue;
        }
    }

    private void httpPost(String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
