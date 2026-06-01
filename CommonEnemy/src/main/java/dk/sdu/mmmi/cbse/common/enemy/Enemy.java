package dk.sdu.mmmi.cbse.common.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Enemy extends Entity {
    public Enemy() {
        polygonCoordinates = new double[]{-5, -5, 10, 0, -5, 5};
        radius = 8;
        rotation = 20;
    }
}
