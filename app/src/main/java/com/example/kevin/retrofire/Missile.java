package com.example.kevin.retrofire;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Missile {
    private static int WIDTH = 40;
    private static int HEIGHT = 10;
    private static int SPEED = 20;

    private int positionX;
    private int positionY;

    private Ship.Direction direction;

    private Paint paint = new Paint();

    public Missile(int positionX, int positionY, Ship.Direction direction) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        paint.setColor(Color.YELLOW);
    }

    public void draw(Canvas canvas) {
        if (Ship.Direction.RIGHT == direction) {
            canvas.drawRect(positionX, positionY, positionX + WIDTH, positionY + HEIGHT, paint);
        } else {
            canvas.drawRect(positionX - WIDTH, positionY, positionX, positionY + HEIGHT, paint);
        }
    }

    public void update() {
        switch (direction) {
            case LEFT:
                positionX -= SPEED;
                break;
            case RIGHT:
                positionX += SPEED;
                break;
        }
    }

    public boolean hasHit(Ship ship) {
        if (Ship.Direction.LEFT == direction) {
            if (positionX - WIDTH <= ship.getPositionX() + Ship.getWIDTH() && positionX - WIDTH >= ship.getPositionX() && positionY >= ship.getPositionY() && positionY <= ship.getPositionY() + Ship.getHEIGHT()) {
                return true;
            } else if (positionX <= ship.getPositionX() + Ship.getWIDTH() && positionX >= ship.getPositionX() && positionY + HEIGHT >= ship.getPositionY() && positionY + HEIGHT <= ship.getPositionY() + Ship.getHEIGHT()) {
                return true;
            }
        } else {
            if (positionX + WIDTH >= ship.getPositionX() && positionX + WIDTH <= ship.getPositionX() + Ship.getWIDTH() && positionY >= ship.getPositionY() && positionY <= ship.getPositionY() + Ship.getHEIGHT()) {
                return true;
            } else if (positionX + WIDTH >= ship.getPositionX() && positionX + WIDTH <= ship.getPositionX() + Ship.getWIDTH() && positionY + HEIGHT >= ship.getPositionY() && positionY + HEIGHT <= ship.getPositionY() + Ship.getHEIGHT()) {
                return true;
            }
        }

        return false;
    }

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
}
