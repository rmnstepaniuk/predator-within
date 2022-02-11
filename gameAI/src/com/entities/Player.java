package com.entities;

import com.assets.Bullet;
import com.handlers.KeyHandler;
import com.frame.Panel;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Player extends Entity {
    private final KeyHandler keyHandler;

    public List<Bullet> bullets;
    private int directionCode;
    private int ammo;

    private boolean reloading = false;
    private int reloadTime = 0;

    public final int screenX;
    public final int screenY;

    public Player(Panel panel, KeyHandler keyHandler) {
        super(panel);
        this.keyHandler = keyHandler;

        screenX = Panel.screenWidth / 2 - (Panel.tileSize / 2);
        screenY = Panel.screenHeight / 2 - (Panel.tileSize / 2);

        initPlayer();
        loadImage();
    }

    private void initPlayer() {
        worldX = Panel.tileSize * Panel.maxWorldCol / 2;
        worldY = Panel.tileSize * Panel.maxWorldCol / 2;
        width = Panel.tileSize / 3;
        height = Panel.tileSize / 3;
        speed = 3;
        this.position = new Vector2d(worldX, worldY);
        this.velocity = new Vector2d(speed, 0);
        this.acceleration = new Vector2d(0, 0);
        direction = "right";
        ammo = 100;
        bullets = new ArrayList<>();
    }

    private void loadImage() {
        try {
            up_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/player_up_1.png")));
            up_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/player_up_2.png")));
            down_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/player_down_1.png")));
            down_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/player_down_2.png")));
            right_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/player_right_1.png")));
            right_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/player_right_2.png")));
            left_1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/player_left_1.png")));
            left_2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/player_left_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.fire ||
                keyHandler.upPressed || keyHandler.downPressed ||
                keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.fire) {
                fire();
            }
            if (keyHandler.upPressed) {
                this.worldY -= this.speed;
                direction = "up";
                directionCode = 8;
            }
            if (keyHandler.leftPressed) {
                this.worldX -= this.speed;
                direction = "left";
                directionCode = 4;
            }
            if (keyHandler.downPressed) {
                this.worldY += this.speed;
                direction = "down";
                directionCode = 2;
            }
            if (keyHandler.rightPressed) {
                this.worldX += this.speed;
                direction = "right";
                directionCode = 6;
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            if (reloading) {
                reloadTime++;
                if (reloadTime > 30) {
                    reloadTime = 0;
                    reloading = false;
                }
            }
        }
        this.position.x = worldX;
        this.position.y = worldY;
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image;

        switch (direction) {
            default:
            case "down":
                if (spriteNum == 1) image = down_1;
                else image = down_2;
                break;
            case "right":
                if (spriteNum == 1) image = right_1;
                else image = right_2;
                break;
            case "left":
                if (spriteNum == 1) image = left_1;
                else image = left_2;
                break;
            case "up":
                if (spriteNum == 1) image = up_1;
                else image = up_2;
                break;
        }
        g2d.drawImage(image, screenX, screenY, Panel.tileSize, Panel.tileSize, null);
    }

    private void fire() {
        if (ammo > 0) {
            if (!reloading) {
                bullets.add(new Bullet(this.panel, this.worldX, this.worldY, this.directionCode));
                ammo--;
                reloading = true;
            }
        }
        else {
            System.out.println("Out of ammo");
        }
    }

    public Rectangle getHitBox() {
        return new Rectangle(this.screenX + width, this.screenY + height, this.width, this.height);
    }

}
