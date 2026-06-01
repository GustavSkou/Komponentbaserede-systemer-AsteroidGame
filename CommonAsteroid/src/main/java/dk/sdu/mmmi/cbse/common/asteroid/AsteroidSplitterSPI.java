package dk.sdu.mmmi.cbse.common.asteroid;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSplitterSPI {
    void split(Asteroid asteroid, World world);
}