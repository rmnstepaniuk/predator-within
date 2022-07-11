package src.com.steering.behaviours;

import src.com.entities.Entity;
import src.com.steering.Steering;

import javax.vecmath.Vector2d;

public class WanderBehaviour {

    private static double wanderAngle = 0;

    public static <T extends Entity> void wander(T agent) {
        int wanderRadius = 10;
        Vector2d steerForce = new Vector2d();

        Vector2d wanderVector = (Vector2d) agent.velocity.clone();
        wanderVector.normalize();
        wanderVector.scale(wanderRadius);

        wanderAngle += agent.velocity.angle(wanderVector);

        Vector2d displacement = new Vector2d(0, -1);
        displacement.scale(wanderRadius);
        Vector2d rotated = Steering.rotateVector(displacement, wanderAngle);

        double ANGLE_CHANGE = Math.PI / 9;
        wanderAngle += (Math.random() * ANGLE_CHANGE) - ANGLE_CHANGE * .5f;

        steerForce.add(wanderVector, rotated);
        Steering.applyForce(agent, steerForce);
        Steering.steer(agent);
    }

}
