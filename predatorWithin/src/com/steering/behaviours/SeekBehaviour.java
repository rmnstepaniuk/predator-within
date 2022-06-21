package com.steering.behaviours;

import com.entities.Entity;
import com.steering.Steering;

import javax.vecmath.Vector2d;

public class SeekBehaviour {

    public static <T extends Entity> void seek(T agent, T target) {
        Vector2d desiredVelocity = new Vector2d();
        Vector2d steerForce = new Vector2d();

        desiredVelocity.sub(target.position, agent.position);
        desiredVelocity.normalize();
        desiredVelocity.scale(agent.speed);

        steerForce.sub(desiredVelocity, agent.velocity);

        Steering.applyForce(agent, steerForce);
        Steering.steer(agent);
    }

}
