package com.example.kevin.retrofire;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ship implements Card {
    private int positionX;
    private int positionY;
    private int life;
    private final Speed speed;
    private final Direction direction;
    private int color;
    private final Weapon weapon;

    public Ship(int positionX, int positionY, int life, Speed speed, Direction direction, Weapon weapon, int color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.life = life;
        this.speed = speed;
        this.direction = direction;
        this.weapon = weapon;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        weapon.draw(canvas);

        if (!isDead()) {
            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawRect(positionX, positionY, positionX + WIDTH, positionY + HEIGHT, paint);
        }
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
    public boolean checkShipCollision(Card card) {
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

    @Override
    public void update(int width, int height) {
        weapon.update(width, height);

        if (!isDead()) {
            switch (direction) {
                case LEFT:
                    positionX -= speed.getValue();
                    break;
                case RIGHT:
                    positionX += speed.getValue();
                    break;
            }

            if (Direction.RIGHT == direction) {
                weapon.fire(positionX + WIDTH, positionY + HEIGHT / 2);
            } else {
                weapon.fire(positionX, positionY + HEIGHT / 2);
            }
        }
    }

    @Override
    public boolean canRemove() {
        return isDead() && weapon.nbMissile() == 0;
    }

    @Override
    public boolean isDead() {
        return life <= 0;
    }

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void takeDamage(int damage) {
        this.life -= damage;
    }
}
