package com.steering.behaviours;

import com.entities.Entity;
import com.steering.Steering;

import javax.vecmath.Vector2d;

public class FleeBehaviour {
    public static <T extends Entity> void flee(T agent, T target) {
        Vector2d desiredVelocity = new Vector2d();
        Vector2d steerForce = new Vector2d();

        desiredVelocity.sub(agent.position, target.position);
        desiredVelocity.normalize();
        desiredVelocity.scale(agent.speed);

        steerForce.sub(desiredVelocity, agent.velocity);

        Steering.applyForce(agent, steerForce);
        Steering.steer(agent);
    }
}
