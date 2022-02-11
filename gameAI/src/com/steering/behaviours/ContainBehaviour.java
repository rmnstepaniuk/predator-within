package com.steering.behaviours;

import com.entities.Entity;
import com.frame.Panel;
import com.steering.Steering;

import javax.vecmath.Vector2d;

public class ContainBehaviour {

    public static <T extends Entity> void contain(T agent) {
        Vector2d desired;
        Vector2d steerForce = new Vector2d();
        if (agent.position.x < 25 || agent.position.x > Panel.worldWidth - agent.width - 25) {
            desired = new Vector2d(-agent.velocity.x, agent.velocity.y);
            steerForce.sub(desired, agent.velocity);
            Steering.applyForce(agent, steerForce);
        }
        if (agent.position.y < 25 || agent.position.y > Panel.worldHeight - agent.width - 25) {
            desired = new Vector2d(agent.velocity.x, -agent.velocity.y);
            steerForce.sub(desired, agent.velocity);
            Steering.applyForce(agent, steerForce);
        }
        Steering.steer(agent);
    }

}
