package src.com.assets;

import src.com.frame.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bullet {

    private final int startingX;
    private final int startingY;
    private final int bulletDirectionCode;

    private int screenX, screenY;
    private final int width, height;

    public boolean visible;

    private BufferedImage image;

    private Panel panel;

    public Bullet(Panel panel, int x, int y, int bulletDirectionCode) {
        this.panel = panel;
        this.startingX = x - panel.player.worldX + panel.player.screenX + Panel.tileSize / 2;
        this.startingY = y - panel.player.worldY + panel.player.screenY + Panel.tileSize / 2;

        this.bulletDirectionCode = bulletDirectionCode;

        screenX = startingX;
        screenY = startingY;

        visible = true;

        width = Panel.tileSize / 6;
        height = Panel.tileSize / 6;

        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/src/res/bullet.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void move(int bulletDirectionCode) {
        int BULLET_SPEED = 10;
        int BULLET_DISTANCE = 200;
        switch (bulletDirectionCode) {
            case 8:
                this.screenY -= BULLET_SPEED;
                if (Math.abs(this.screenY - startingY) > BULLET_DISTANCE) {
                    visible = false;
                }
                break;
            case 2:
                this.screenY += BULLET_SPEED;
                if (Math.abs(this.screenY - startingY) > BULLET_DISTANCE) {
                    visible = false;
                }
                break;
            case 4:
                this.screenX -= BULLET_SPEED;
                if (Math.abs(this.screenX - startingX) > BULLET_DISTANCE) {
                    visible = false;
                }
                break;
            default:
            case 6:
                this.screenX += BULLET_SPEED;
                if (Math.abs(this.screenX - startingX) > BULLET_DISTANCE) {
                    visible = false;
                }
                break;
        }
    }

    public void update() {
        move(bulletDirectionCode);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, screenX, screenY, width, height, null);
    }

    public Rectangle getHitBox() {
        return new Rectangle(screenX, screenY, width, height);
    }

}
