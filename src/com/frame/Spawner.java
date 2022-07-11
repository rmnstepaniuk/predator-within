package src.com.frame;

import src.com.entities.agents.Wolf;

import java.util.Random;

public class Spawner {

    private static Panel panel;
    private final static Random random = new Random();

    public Spawner(Panel panel) {
        Spawner.panel = panel;
    }

    public Wolf spawnWolf() {
        return new Wolf(Spawner.panel, random.nextInt(Panel.worldWidth - 50), random.nextInt(Panel.worldHeight - 50));
    }

}
