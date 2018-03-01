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
    private static final Paint paint = new Paint();
    private final Weapon weapon;

    public Ship(int positionX, int positionY, int life, Speed speed, Direction direction) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.life = life;
        this.speed = speed;
        this.direction = direction;
        this.weapon = new Weapon(Weapon.RateOfFire.LOW, direction);
        paint.setColor(Color.BLUE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(positionX, positionY, positionX + WIDTH, positionY + HEIGHT, paint);

        weapon.draw(canvas);
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
    public void update(int width, int height) {
        switch (direction) {
            case LEFT:
                positionX -= speed.getValue();
                break;
            case RIGHT:
                positionX += speed.getValue();
                break;
        }

        weapon.update(width, height);

        if (Direction.RIGHT == direction) {
            weapon.fire(positionX + WIDTH, positionY + HEIGHT / 2);
        } else {
            weapon.fire(positionX, positionY + HEIGHT / 2);
        }
    }
}
