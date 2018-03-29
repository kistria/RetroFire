package com.example.kevin.retrofire;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.kevin.retrofire.card.Card;
import com.example.kevin.retrofire.ship.BasicShip;
import com.example.kevin.retrofire.ship.Ship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class BattleZoneModel {
    private int width;
    private int height;
    private transient Paint paint = new Paint();

    private final List<Ship> enemies;
    private final List<Card> playerCards;
    private int rateOfEnemySpawn;
    private int cmpt = 0;

    private int hpBar = 100;
    private int score = 0;

    public BattleZoneModel(int rateOfEnemySpawn) {
        this.rateOfEnemySpawn = rateOfEnemySpawn;
        this.enemies = new ArrayList<>();
        this.playerCards = new ArrayList<>();
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public int getHpBar() {
        return hpBar;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

        int posX = width - Ship.getWIDTH();
        int posY = r.nextInt(height - Ship.getHEIGHT());
        enemies.add(new BasicShip(posX, posY, Ship.Direction.LEFT, Color.RED));
    }

    public void drawAll(Canvas canvas) {
        Bitmap bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas bufferCanvas = new Canvas(bufferBitmap);

        for (Card card : playerCards) {
            card.draw(bufferCanvas);
        }
        for (Ship enemy : enemies) {
            enemy.draw(bufferCanvas);
        }

        canvas.drawBitmap(bufferBitmap, 0, 0, paint);
    }

    private void checkCollision() {
        //Check edges collision
        for (Card cardTemp : playerCards) {
            if (!cardTemp.getShip().isDead() && cardTemp.getShip().checkEdgesCollision(width, height)) {
                cardTemp.getShip().destroy();
            }
        }

        // lose life when an enemy reach the left side
        for (Ship ship : enemies) {
            if (!ship.isDead() && ship.checkEdgesCollision(width, height)) {
                hpBar -= 10;
            }
        }
        //Check enemies edges collision
        for (Ship shipTemp : enemies) {
            if (!shipTemp.isDead() && shipTemp.checkEdgesCollision(width, height)) {
                shipTemp.destroy();
            }
        }

        //Check ship collision
        for (Iterator<Card> it = playerCards.iterator(); it.hasNext(); ) {
            Card cardTemp = it.next();

            if (cardTemp.getShip().isDead()) {
                continue;
            }

            for (Iterator<Ship> it2 = enemies.iterator(); it2.hasNext(); ) {
                Ship shipTemp = it2.next();
                if (!shipTemp.isDead() && shipTemp.checkShipCollision(cardTemp.getShip())) {
                    cardTemp.getShip().destroy();
                    shipTemp.destroy();
                }
            }
        }

        //Check player hit ennemy
        for (Card playerCard : playerCards) {
            for (Ship enemy : enemies) {
                playerCard.getShip().getWeapon().hasHit(enemy);
            }
        }

        //Check enemy hit player card
        for (Ship enemy : enemies) {
            for (Card card : playerCards) {
                enemy.getWeapon().hasHit(card.getShip());
            }
        }
    }

    public boolean gameIsFinish() {
        return hpBar <= 0;
    }

    public void update() {
        if (cmpt % rateOfEnemySpawn == 0) {
            addEnemy();
        }

        for (Card cardTemp : playerCards) {
            if (!cardTemp.getShip().canRemove()) {
                cardTemp.getShip().update(width, height);
            } else {
                cardTemp.getShip().destroy();
            }
        }

        for (Ship shipTemp : enemies) {
            if (!shipTemp.canRemove()) {
                shipTemp.update(width, height);
            } else {
                shipTemp.destroy();
            }
        }

        checkCollision();
        cmpt++;
    }

    // Change every player's ship Y position depending on the accelerometer Y change
    public void accelerometerChange(int value) {
        for (Card ship : playerCards) {
            int y = ship.getShip().getPositionY() + (value*2);
            if (y + Ship.getHEIGHT() < height && y > 0)
                ship.getShip().setPositionY(y);
        }
    }
}
