package dk.sdu.mmmi.cbse.gameending;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.gameEnding.GameEndingSPI;
import dk.sdu.mmmi.cbse.common.services.IPluginService;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ServiceLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameEnding implements GameEndingSPI {

    @Override
    public void endGame(GameData gameData, World world) {
        cleanupPlugins(gameData, world);

        Pane pane = gameData.getPane();
        if (pane == null) {
            return;
        }

        pane.getChildren().clear();

        Text titleText = new Text(300, 320, "Game Over");
        titleText.setFont(Font.font(36));

        Text currentScoreText = new Text(300, 380, "Current score: " + httpGetValue("http://localhost:8081/score"));
        currentScoreText.setFont(Font.font(24));

        Text highScoreText = new Text(300, 420, "High score: " + httpGetValue("http://localhost:8081/highscore"));
        highScoreText.setFont(Font.font(24));

        pane.getChildren().add(titleText);
        pane.getChildren().add(currentScoreText);
        pane.getChildren().add(highScoreText);
    }

    private void cleanupPlugins(GameData gameData, World world) {
        for (IPluginService pluginService : ServiceLoader.load(IPluginService.class)) {
            pluginService.ICleanUp(gameData, world);
        }
    }

    private long httpGetValue(String uri) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Long.parseLong(response.body().trim());
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}