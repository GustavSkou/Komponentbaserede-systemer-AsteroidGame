package dk.sdu.mmmi.cbse.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author jcs
 */
public class World {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public Entity[] getEntitiesAsArray() {
        return entityMap.values().toArray(new Entity[0]);
    }

    public <E extends Entity> List<E> getEntities(Class<E>... entityTypes) {
        List<E> result = new ArrayList<>();
        for (Entity entity : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(entity.getClass())) {
                    result.add((E) entity);
                }
            }
        }
        return result;
    }
}
