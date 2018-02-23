package com.example.kevin.retrofire;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class BattleZoneModel {
    private final int width;
    private final int height;
    private transient Paint paint = new Paint();

    private final List<Ship> enemies;
    private final List<Ship> playerCards;

    public BattleZoneModel(int width, int height) {
        this.width = width;
        this.height = height;
        this.enemies = new ArrayList<>();
        this.playerCards = new ArrayList<>();
    }

    //TODO CRER INTEERFACE CARD ET CHANGER NOM
    public void addPlayerCard(Ship ship) {
        playerCards.add(ship);
    }

    public void drawAll(Canvas canvas) {
        Bitmap bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas bufferCanvas = new Canvas(bufferBitmap);

        playerCards.forEach(card -> card.draw(bufferCanvas));

        int offsetWidth = (canvas.getWidth() - width) / 2 + 200; //TODO
        int offsetHeight = (canvas.getHeight() - height) / 2;
        canvas.drawBitmap(bufferBitmap, offsetWidth, offsetHeight, paint);
    }

    public void update() {
        playerCards.forEach(Ship::update);
    }
}
