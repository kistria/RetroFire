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

    private Card.Direction direction;

    private Paint paint = new Paint();

    public Missile(int positionX, int positionY, Card.Direction direction) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        paint.setColor(Color.YELLOW);
    }

    public void draw(Canvas canvas) {
        if (Card.Direction.RIGHT == direction) {
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

    public boolean hasHit(Card card) {
        if (Card.Direction.LEFT == direction) {
            if (positionX <= card.getPositionX() + Card.WIDTH && positionX >= card.getPositionX() && positionY >= card.getPositionY() && positionY <= card.getPositionY() + Card.HEIGHT) {
                return true;
            } else if (positionX <= card.getPositionX() + Card.WIDTH && positionX >= card.getPositionX() && positionY + HEIGHT >= card.getPositionY() && positionY + HEIGHT <= card.getPositionY() + Card.HEIGHT) {
                return true;
            }
        } else {
            if (positionX + WIDTH >= card.getPositionX() && positionX + WIDTH <= card.getPositionX() + Card.WIDTH && positionY >= card.getPositionY() && positionY <= card.getPositionY() + Card.HEIGHT) {
                return true;
            } else if (positionX + WIDTH >= card.getPositionX() && positionX + WIDTH <= card.getPositionX() + Card.WIDTH && positionY + HEIGHT >= card.getPositionY() && positionY + HEIGHT <= card.getPositionY() + Card.HEIGHT) {
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
