package com.handlers;

import com.agents.Wolf;
import com.assets.Bullet;
import com.entities.Entity;

import java.awt.*;

public class CollisionHandler {

    public static <T extends Entity, V extends Entity> boolean entityCollision(T agent1, V agent2) {
        Rectangle agent1HitBox = agent1.getHitBox();
        Rectangle agent2HitBox = agent2.getHitBox();
        if (agent1.equals(agent2)) return false;
//        else if (agent1 instanceof Wolf && agent2 instanceof Wolf) return false;
        return agent1HitBox.intersects(agent2HitBox);
    }

    public static <T extends Entity> boolean bulletCollision(Bullet bullet, T agent) {
        Rectangle bulletHitBox = bullet.getHitBox();
        Rectangle agentHitBox = agent.getHitBox();

        return bulletHitBox.intersects(agentHitBox);
    }

}
