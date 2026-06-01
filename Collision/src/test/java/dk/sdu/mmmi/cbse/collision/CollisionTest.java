package dk.sdu.mmmi.cbse.collision;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

class CollisionTest {

    private static final class EntityCollisionTest extends CollisionProcessor {

        public World testWorld;
        private Entity[] entities;

        private EntityCollisionTest() {
            testWorld = new World();

            for(int i = 0; i < 2; i++) {
                Entity entity = new Entity();
                entity.setX(100);
                entity.setY(100);   
                testWorld.addEntity(entity);
            }
        }
    }

    @Test
    void TestEntityCollision() {
        EntityCollisionTest entityCollisionTest = new EntityCollisionTest();
        assertEquals(2, entityCollisionTest.testWorld.getEntities().size());
        entityCollisionTest.process(null, entityCollisionTest.testWorld);
        assertEquals(0, entityCollisionTest.testWorld.getEntities().size());
    }
}