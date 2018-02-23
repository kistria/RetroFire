package com.example.kevin.retrofire;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ship {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    private int positionX;
    private int positionY;
    private int life;
    private int speed;
    private static Paint paint = new Paint();

    public Ship(int positionX, int positionY, int life, int speed) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.life = life;
        this.speed = speed;
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.BLUE);
        canvas.drawRect(positionX, positionY, positionX + WIDTH, positionY + HEIGHT, paint);

    }

    public void update() {
        positionX += speed;
    }
}
