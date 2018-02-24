package com.example.kevin.retrofire;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class BattleZoneModel {
    private int width;
    private int height;
    private transient Paint paint = new Paint();

    private final List<Card> enemies;
    private final List<Card> playerCards;

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

    public void drawAll(Canvas canvas) {
        Bitmap bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas bufferCanvas = new Canvas(bufferBitmap);

        playerCards.forEach(card -> card.draw(bufferCanvas));

        canvas.drawBitmap(bufferBitmap, 0, 0, paint);
    }

    private void checkEdgesCollision() {
        playerCards.removeIf(card -> card.checkEdgesCollision(width, height));
    }

    public void update() {
        playerCards.forEach(Card::update);
        checkEdgesCollision();
    }
}
