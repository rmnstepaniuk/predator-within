package com.handlers;

import com.assets.Tile;
import com.frame.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TileHandler {

    private final Tile tile;
    private Panel panel;

    public TileHandler(Panel panel) {
        this.panel = panel;
        this.tile = new Tile();

        loadImage();
    }

    private void loadImage() {
        try {
            tile.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/grass.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        int worldX, worldY;
        int screenX, screenY;
        for (int i = 0; i < Panel.maxWorldCol; i++) {
            worldX = i * Panel.tileSize;
            screenX = worldX - panel.player.worldX + panel.player.screenX;

            for (int j = 0; j < Panel.maxWorldRow; j++) {
                worldY = j * Panel.tileSize;
                screenY = worldY - panel.player.worldY + panel.player.screenY;

                if (worldX > panel.player.worldX - panel.player.screenX - Panel.tileSize &&
                    worldX < panel.player.worldX + panel.player.screenX + Panel.tileSize &&
                    worldY > panel.player.worldY - panel.player.screenY - Panel.tileSize &&
                    worldY < panel.player.worldY + panel.player.screenY + Panel.tileSize) {
                    g2d.drawImage(tile.image, screenX, screenY, Panel.tileSize, Panel.tileSize, null);
                }
            }
        }
    }

}
