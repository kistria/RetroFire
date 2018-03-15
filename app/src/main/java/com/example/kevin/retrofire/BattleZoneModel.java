package com.example.kevin.retrofire;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.kevin.retrofire.card.Card;
import com.example.kevin.retrofire.ship.BasicShip;
import com.example.kevin.retrofire.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BattleZoneModel {
    private int width;
    private int height;
    private transient Paint paint = new Paint();

    private final List<Ship> enemies;
    private final List<Card> playerCards;
    private int rateOfEnemySpawn = 200;
    private int cmpt = 0;

    private int hpBar = 10;
    private int score = 0;

    public BattleZoneModel() {
        this.enemies = new ArrayList<>();
        this.playerCards = new ArrayList<>();
    }

    public List<Card> getPlayerCards () { return playerCards; }

    public int getHpBar(){
        return hpBar;
    }

    public int getScore () {return score;}

    public void setScore (int score) { this.score = score;}

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

        int posX = width - Ship.getWIDTH();
        int posY = r.nextInt(height - Ship.getHEIGHT());
        enemies.add(new BasicShip(posX, posY, Ship.Direction.LEFT, Color.RED));
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
        playerCards.removeIf(card -> card.getShip().checkEdgesCollision(width, height));

        // lose life when an enemy reach the left side
        enemies.forEach(enemy -> {
            if(enemy.checkEdgesCollision(width, height)){
                hpBar-=10;
            }
        });
        //Check enemies edges collision
        enemies.removeIf(enemy -> enemy.checkEdgesCollision(width, height));

        //Check ship collision
        playerCards.removeIf(card -> enemies.removeIf(enemy -> enemy.checkShipCollision(card.getShip())));

        //Check player hit ennemy
        playerCards.forEach(card -> enemies.forEach(enemy -> card.getShip().getWeapon().hasHit(enemy)));

        //Check enemy hit player card
        enemies.forEach(enemies -> playerCards.forEach(card -> enemies.getWeapon().hasHit(card.getShip())));
    }

    public void update() {
        if (cmpt % rateOfEnemySpawn == 0) {
            addEnemy();
        }

        playerCards.removeIf(card -> card.getShip().canRemove());
        enemies.removeIf(Ship::canRemove);

        playerCards.removeIf(card -> {
            boolean remove = card.getShip().canRemove();
            if (!remove) {
                card.getShip().update(width, height);
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

    // Change every player's ship Y position depending on the accelerometer Y change
    public void accelerometerChange(int value) {
        playerCards.forEach(ship -> {
            int y =  ship.getShip().getPositionY() + value;
            if(y + Ship.getHEIGHT() < height && y > 0)
                ship.getShip().setPositionY(y);
        });
    }
}
