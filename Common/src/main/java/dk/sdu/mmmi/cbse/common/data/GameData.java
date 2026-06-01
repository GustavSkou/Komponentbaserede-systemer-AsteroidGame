package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.gameControlls.GameKeyBinds;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;

public class GameData {
    private Pane pane;
    private double deltaTime;
    private double timeNow = 0;
    private double timePre = 0;
    private GameKeyBinds keyBinds;
    private boolean gameEnded;
    private final List<Entity> pendingPlayerEntityHits = new ArrayList<>();

    public double getDeltaTime() {
        return deltaTime;
    }

    public void updateDeltaTime(double timeNow) {

        this.timeNow = timeNow / 100000000; // time in seconds
        this.deltaTime = this.timeNow - this.timePre; // time between frames
        this.timePre = this.timeNow;
    }

    public void registerPlayerEntityHit(Entity hitEntity) {
        pendingPlayerEntityHits.add(hitEntity);
    }

    public List<Entity> consumePendingPlayerEntityHits() {
        List<Entity> hits = new ArrayList<>(pendingPlayerEntityHits);
        pendingPlayerEntityHits.clear();
        return hits;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Pane getPane() {
        return pane;
    }

    public GameKeyBinds getKeyBinds() {
        return keyBinds;
    }

    public void setKeyBinds(GameKeyBinds keyBinds) {
        this.keyBinds = keyBinds;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public int getDisplayWidth() {
        return (int)pane.getWidth();
    }

    public int getDisplayHeight() {
        return (int)pane.getHeight();
    }
}