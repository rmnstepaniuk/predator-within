package com.frame;

import com.assets.Bullet;
import com.entities.Entity;
import com.entities.Player;
import com.handlers.CollisionHandler;
import com.handlers.KeyHandler;
import com.handlers.TileHandler;

import javax.swing.JPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Panel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    public final static int originalTileSize = 48;                    // 48x48
    public final static int scale = 2;
    public final static int tileSize = originalTileSize * scale;      // 96x96

    public final static int maxScreenCol = 12;
    public final static int maxScreenRow = 9;

    public final static int screenWidth = tileSize * maxScreenCol;    // 1152
    public final static int screenHeight = tileSize * maxScreenRow;   // 864

    // WORLD SETTINGS
    public final static int maxWorldCol = 25;
    public final static int maxWorldRow = 25;

    public final static int worldWidth = tileSize * maxWorldCol;
    public final static int worldHeight = tileSize * maxWorldRow;

    private Thread gameThread;
    private static final KeyHandler keyHandler = new KeyHandler();
    private final Spawner spawner = new Spawner(this);
    private final TileHandler tileHandler = new TileHandler(this);
    public Player player = new Player(this, keyHandler);
    public List<Entity> agents = new ArrayList<Entity>() {
        {
            add(spawner.spawnWolf());
            add(spawner.spawnWolf());
            add(spawner.spawnWolf());
            add(spawner.spawnWolf());
            add(spawner.spawnWolf());
            add(spawner.spawnWolf());
            add(spawner.spawnWolf());
            add(spawner.spawnWolf());
            add(spawner.spawnWolf());
            add(spawner.spawnWolf());
        }
    };

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.cyan);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update() {
        player.update();
        List<Bullet> bullets = player.bullets;
        Bullet bullet;
        for (int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i);
            if (bullet.visible) {
                bullet.update();
            }
            else bullets.remove(bullet);
        }
        for (Entity agent : agents) {
            agent.update();
        }
        // Check for possible collisions
        collisionCheck();
    }

    private void collisionCheck() {
        Entity agent;
        Bullet bullet;
        List<Bullet> bullets = player.bullets;

        for (int i = 0; i < agents.size(); i++) {
            agent = agents.get(i);
            if (CollisionHandler.entityCollision(agent, player)) {
                gameThread = null;
            }
            for (int k = 0; k < bullets.size(); k++) {
                bullet = bullets.get(k);
                if (CollisionHandler.bulletCollision(bullet, agent)) {
                    agent.visible = false;
                    agents.remove(agent);
                    bullet.visible = false;
                    bullets.remove(bullet);
                    break;
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        tileHandler.draw(g2d);
        player.draw(g2d);

        for (Bullet bullet : player.bullets) {
            bullet.draw(g2d);
        }
        for (Entity agent : agents) {
            agent.draw(g2d);
        }

        // Debug
/*
        Rectangle playerHitBox = player.getHitBox();
        Rectangle entityHitBox;
        Rectangle bulletHitBox ;
        g2d.setColor(Color.red);

        g2d.drawString("World X: " + player.worldX + ";   World Y: " + player.worldY, screenWidth / 2, 50);

        g2d.drawRect(playerHitBox.x, playerHitBox.y, playerHitBox.width, playerHitBox.height);
        for (Bullet bullet : player.bullets) {
            bulletHitBox = bullet.getHitBox();
            g2d.drawRect(bulletHitBox.x, bulletHitBox.y, bulletHitBox.width, bulletHitBox.height);
        }
        for (Entity agent : agents) {
            entityHitBox = agent.getHitBox();
            g2d.drawRect(entityHitBox.x, entityHitBox.y, entityHitBox.width, entityHitBox.height);
        }
*/
        g2d.dispose();
    }

    @Override
    public void run() {

        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            // FPS
            int FPS = 60;
            double drawInterval = (double) 1000000000 / FPS;
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // Update information such as character position etc
                update();

                // Draw screen components with updated information
                repaint();

                delta--;
            }
        }
    }

}
