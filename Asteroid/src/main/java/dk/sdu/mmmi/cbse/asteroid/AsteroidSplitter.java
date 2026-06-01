package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSplitterSPI;
import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

public class AsteroidSplitter implements AsteroidSplitterSPI {
    @Override
    public void split(Asteroid asteroid, World world) {
        Random random = new Random();

        asteroid.setHealth(asteroid.getHealth() - 1);

        if (asteroid.getHealth() <= 0) {
            return;
        }

        Entity[] babyAsteroids = getBabyAsteroids(asteroid);
        for (Entity babyAsteroid : babyAsteroids) {
            if (random.nextInt(2) == 0) {
                world.addEntity(babyAsteroid);
            }
        }
    }

    private Entity[] getBabyAsteroids(Asteroid asteroid) {
        Asteroid[] babyAsteroids = new Asteroid[2];
        double[][] velocities = getBabyVelocityVectors(asteroid);

        for (int i = 0; i < 2; i++) {
            Asteroid babyAsteroid = new Asteroid();

            babyAsteroid.setHealth(asteroid.getHealth());

            babyAsteroid.setXVelocity(velocities[i][0]);
            babyAsteroid.setYVelocity(velocities[i][1]);

            babyAsteroid.setX(asteroid.getX() + babyAsteroid.getXVelocity() * babyAsteroid.getRadius() * 2);
            babyAsteroid.setY(asteroid.getY() + babyAsteroid.getYVelocity() * babyAsteroid.getRadius() * 2);

            babyAsteroids[i] = babyAsteroid;
        }
        return babyAsteroids;
    }
    /*
    * Get the velocity for the new asteroids based on the mothers
    * velocity and then rotated to 45 and -45 degrees
    * */
    private double[][] getBabyVelocityVectors(Entity asteroid) {
        double x = asteroid.getXVelocity();
        double y = asteroid.getYVelocity();
        double currentRadian = Math.atan2(y,x);

        double radian = Math.toRadians(45);
        double x1 = x * Math.cos(currentRadian+radian) - y * Math.sin(currentRadian+radian);
        double y1 = x * Math.sin(currentRadian+radian) + y * Math.cos(currentRadian+radian);
        double x2 = x * Math.cos(currentRadian-radian) - y * Math.sin(currentRadian-radian);
        double y2 = x * Math.sin(currentRadian-radian) + y * Math.cos(currentRadian-radian);
        return new double[][] { {x1,y1}, {x2,y2} };
    }
}
