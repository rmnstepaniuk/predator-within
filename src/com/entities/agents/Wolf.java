package src.com.entities.agents;

import src.com.entities.Entity;
import src.com.frame.Panel;
import src.com.steering.behaviours.*;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Wolf extends Entity {

    protected BufferedImage right_3, left_3;
    private int screenX, screenY;

    // Target and behaviour
    private Behaviour behaviour;
    private Entity target;

    public Wolf(Panel panel, int worldX, int worldY) {
        super(panel);
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = 2;
        this.direction = "left";
        this.viewRadius = 1000;

        this.position = new Vector2d(this.worldX, this.worldY);
        this.velocity = new Vector2d(1, 0);
        this.acceleration = new Vector2d(0, 0);

        screenX = this.worldX - panel.player.worldX + panel.player.screenX;
        screenY = this.worldY - panel.player.worldY + panel.player.screenY;

        this.width = Panel.tileSize / 3 * 2;
        this.height = Panel.tileSize / 2;

//        System.out.println("Wolf X: " + worldX + "\tWolf Y: " + worldY);

        loadImage();
    }

    private void loadImage() {
        try {
            right_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/src/res/wolf/wolf_right_1.png")));
            right_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/src/res/wolf/wolf_right_2.png")));
            right_3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/src/res/wolf/wolf_right_3.png")));

            left_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/src/res/wolf/wolf_left_1.png")));
            left_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/src/res/wolf/wolf_left_2.png")));
            left_3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/src/res/wolf/wolf_left_3.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (visible) {

            target = findTarget(panel.agents, panel.player);
            setBehaviour();
            switch (behaviour) {
                case CONTAIN:
                    ContainBehaviour.contain(this);
                    break;
                case WANDER:
                    WanderBehaviour.wander(this);
                    break;
                case FLEE:
                    FleeBehaviour.flee(this, target);
                    break;
                case SEEK:
                    SeekBehaviour.seek(this, target);
                    break;
            }
            screenX = (int) (this.position.x - panel.player.worldX + panel.player.screenX);
            screenY = (int) (this.position.y - panel.player.worldY + panel.player.screenY);
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) spriteNum = 2;
                else if (spriteNum == 2) spriteNum = 3;
                else spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2d) {
        if (visible) {
            BufferedImage image;
            getDirection();
            switch (direction) {
                default:
                case "left":
                    if (spriteNum == 1) image = left_1;
                    else if (spriteNum == 2) image = left_2;
                    else image = left_3;
                    break;
                case "right":
                    if (spriteNum == 1) image = right_1;
                    else if (spriteNum == 2) image = right_2;
                    else image = right_3;
                    break;
            }
            if (this.position.x > panel.player.worldX - panel.player.screenX - Panel.tileSize &&
                    this.position.x < panel.player.worldX + panel.player.screenX + Panel.tileSize &&
                    this.position.y > panel.player.worldY - panel.player.screenY - Panel.tileSize &&
                    this.position.y < panel.player.worldY + panel.player.screenY + Panel.tileSize) {
                g2d.drawImage(image, screenX, screenY, Panel.tileSize, Panel.tileSize, null);
            }
        }
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(this.screenX + width / 3, this.screenY + Panel.tileSize / 4, this.width, this.height);
    }

    private void setBehaviour() {
        if (target == null) {
            if (this.closeToEdges()) behaviour = Behaviour.CONTAIN;
            else behaviour = Behaviour.WANDER;
        }
        else behaviour = Behaviour.SEEK;
    }

    private void getDirection() {
        double angle = this.velocity.angle(new Vector2d(1, 0));
        if (angle > 0 && angle < Math.PI / 2) direction = "right";
        else direction = "left";
    }
}
