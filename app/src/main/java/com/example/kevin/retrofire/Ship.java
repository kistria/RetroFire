package com.example.kevin.retrofire;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ship implements Card {
    private int positionX;
    private int positionY;
    private int life;
    private final Speed speed;
    private final Direction direction;
    private static Paint paint = new Paint();

    public Ship(int positionX, int positionY, int life, Speed speed, Direction direction) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.life = life;
        this.speed = speed;
        this.direction = direction;
        paint.setColor(Color.BLUE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(positionX, positionY, positionX + WIDTH, positionY + HEIGHT, paint);
    }

    @Override
    public boolean checkEdgesCollision(int width, int height) {
        if (positionX < 0) {
            return true;
        } else if (positionY < 0) {
            return true;
        } else if (positionX + WIDTH > width) {
            return true;
        } else if (positionY + HEIGHT > height) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        switch (direction) {
            case LEFT:
                positionX -= speed.getValue();
                break;
            case RIGHT:
                positionX += speed.getValue();
                break;
        }
    }
}
