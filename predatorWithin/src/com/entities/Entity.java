package com.entities;

import com.agents.Wolf;
import com.frame.Panel;

import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public int viewRadius;

    public Vector2d position, velocity, acceleration;

    protected BufferedImage up_1, up_2, down_1, down_2, right_1, right_2, left_1, left_2;
    protected String direction;

    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    public int width, height;

    public boolean visible = true;

    protected Panel panel;

    public Entity(Panel panel) {
        this.panel = panel;
    }

    public void update() {}

    public void draw(Graphics2D g2d) {}

    public Rectangle getHitBox() { return new Rectangle(); }

    public Entity findTarget(List<Entity> entities, Player player) {
        Entity entity;
        Entity target = null;
        double distanceToTarget = Double.MAX_VALUE;
        Vector2d desired = new Vector2d();
        for (Entity temp : entities) {
            entity = temp;
            if (!this.equals(entity) ||
                (this instanceof Wolf && entity instanceof Wolf)) {
                desired.sub(entity.position, this.position);
                if (desired.length() < distanceToTarget && desired.length() < this.viewRadius) {
                    distanceToTarget = desired.length();
                    target = entity;
                }
            }
        }
        desired.sub(player.position, this.position);
        if (desired.length() < distanceToTarget /* && desired.length() < this.viewRadius */) {
            target = player;
        }
        return player;
    }

    protected boolean closeToEdges() {
        return  (this.position.x < 100 || this.position.x > Panel.worldWidth - this.width - 100) ||
                (this.position.y < 100 || this.position.y > Panel.worldHeight - this.width - 100);
    }

}
