package com.example.kevin.retrofire;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleZoneModel {
    private int width;
    private int height;
    private transient Paint paint = new Paint();

    private final List<Ship> enemies;
    private final List<Ship> playerShips;
    private int rateOfEnemySpawn = 200;
    private int cmpt = 0;

    public BattleZoneModel() {
        this.enemies = new ArrayList<>();
        this.playerShips = new ArrayList<>();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void addPlayerCard(Ship ship) {
        playerShips.add(ship);
    }

    public void addEnemy() {
        Random r = new Random();

        int posX = width - Ship.getWIDTH();
        int posY = r.nextInt(height - Ship.getHEIGHT());
        enemies.add(new BasicShip(posX, posY, Ship.Direction.LEFT, Color.RED));
    }

    public void drawAll(Canvas canvas) {
        Bitmap bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas bufferCanvas = new Canvas(bufferBitmap);

        playerShips.forEach(card -> card.draw(bufferCanvas));
        enemies.forEach(enemy -> enemy.draw(bufferCanvas));

        canvas.drawBitmap(bufferBitmap, 0, 0, paint);
    }

    private void checkCollision() {
        //Check edges collision
        playerShips.removeIf(card -> card.checkEdgesCollision(width, height));
        enemies.removeIf(enemy -> enemy.checkEdgesCollision(width, height));

        //Check ship collision
        playerShips.removeIf(card -> enemies.removeIf(enemy -> enemy.checkShipCollision(card)));

        //Check player hit ennemy
        playerShips.forEach(card -> enemies.forEach(enemy -> card.getWeapon().hasHit(enemy)));

        //Check enemy hit player card
        enemies.forEach(enemies -> playerShips.forEach(card -> enemies.getWeapon().hasHit(card)));
    }

    public void update() {
        if (cmpt % rateOfEnemySpawn == 0) {
            addEnemy();
        }

        playerShips.removeIf(Ship::canRemove);
        enemies.removeIf(Ship::canRemove);

        playerShips.removeIf(card -> {
            boolean remove = card.canRemove();
            if (!remove) {
                card.update(width, height);
            }
            return remove;
        });

        enemies.removeIf(card -> {
            boolean remove = card.canRemove();
            if (!remove) {
                card.update(width, height);
            }
            return remove;
        });

        checkCollision();
        cmpt++;
    }
}
