package com.steering;

import com.entities.Entity;

import javax.vecmath.Vector2d;

public class Steering {

    public static <T extends Entity> void applyForce(T agent, Vector2d force) {
        agent.acceleration.setX(force.x);
        agent.acceleration.setY(force.y);
    }

    public static <T extends Entity> void steer(T agent) {
        agent.velocity.add(agent.acceleration);
        if (agent.velocity.length() > agent.speed) {
            agent.velocity.normalize();
            agent.velocity.scale(agent.speed);
        }
        agent.position.add(agent.velocity, agent.position);
    }

    public static Vector2d rotateVector(Vector2d vector, double angle) {
        double length = vector.length();
        vector.x = Math.cos(angle) * length;
        vector.y = Math.sin(angle) * length;
        return vector;
    }
}
