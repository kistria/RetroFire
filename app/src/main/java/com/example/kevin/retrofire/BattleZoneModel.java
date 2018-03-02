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

    private final List<Card> enemies;
    private final List<Card> playerCards;
    private int rateOfEnemySpawn = 200;
    private int cmpt = 0;

    public BattleZoneModel() {
        this.enemies = new ArrayList<>();
        this.playerCards = new ArrayList<>();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void addPlayerCard(Card card) {
        playerCards.add(card);
    }

    public void addEnemy() {
        Random r = new Random();

        int posX = width - Card.WIDTH;
        int posY = r.nextInt(height - Card.HEIGHT);

        Weapon weapon = new Weapon(Weapon.RateOfFire.LOW, Card.Direction.LEFT);
        enemies.add(new Ship(posX, posY, 10, Card.Speed.LOW, Card.Direction.LEFT, weapon, Color.RED));
    }

    public void drawAll(Canvas canvas) {
        Bitmap bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas bufferCanvas = new Canvas(bufferBitmap);

        playerCards.forEach(card -> card.draw(bufferCanvas));
        enemies.forEach(enemy -> enemy.draw(bufferCanvas));

        canvas.drawBitmap(bufferBitmap, 0, 0, paint);
    }

    private void checkCollision() {
        //Check edges collision
        playerCards.removeIf(card -> card.checkEdgesCollision(width, height));
        enemies.removeIf(enemy -> enemy.checkEdgesCollision(width, height));

        //Check ship collision
        playerCards.removeIf(card -> enemies.removeIf(enemy -> enemy.checkShipCollision(card)));

        //Check player hit ennemy
        playerCards.forEach(card -> enemies.forEach(enemy -> card.getWeapon().hasHit(enemy)));

        //Check enemy hit player card
        enemies.forEach(enemies -> playerCards.forEach(card -> enemies.getWeapon().hasHit(card)));
    }

    public void update() {
        if (cmpt % rateOfEnemySpawn == 0) {
            addEnemy();
        }

        playerCards.removeIf(Card::canRemove);
        enemies.removeIf(Card::canRemove);

        playerCards.removeIf(card -> {
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
