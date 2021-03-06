package com.example.kevin.retrofire.ship;


import android.graphics.Canvas;

public abstract class Ship {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    private int positionX;
    private int positionY;
    private int life;
    private final Speed speed;
    private final Direction direction;
    protected int color;
    private final Weapon weapon;

    public Ship(int positionX, int positionY, int life, Speed speed, Direction direction, int color, Weapon weapon) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.life = life;
        this.speed = speed;
        this.direction = direction;
        this.color = color;
        this.weapon = weapon;
    }

    public abstract void draw(Canvas canvas);

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

    public boolean checkShipCollision(Ship ship) {
        if (positionX <= ship.getPositionX() + WIDTH && positionX >= ship.getPositionX() && positionY >= ship.getPositionY() && positionY <= ship.getPositionY() + HEIGHT) {
            return true;
        } else if (positionX + WIDTH <= ship.getPositionX() + WIDTH && positionX + WIDTH >= ship.getPositionX() && positionY >= ship.getPositionY() && positionY <= ship.getPositionY() + HEIGHT) {
            return true;
        } else if (positionX + WIDTH <= ship.getPositionX() + WIDTH && positionX + WIDTH >= ship.getPositionX() && positionY + HEIGHT >= ship.getPositionY() && positionY + HEIGHT <= ship.getPositionY() + HEIGHT) {
            return true;
        } else if (positionX <= ship.getPositionX() + WIDTH && positionX >= ship.getPositionX() && positionY + HEIGHT >= ship.getPositionY() && positionY + HEIGHT <= ship.getPositionY() + HEIGHT) {
            return true;
        }

        return false;
    }

    public void destroy() {
        this.life = 0;
    }

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

    public boolean canRemove() {
        return isDead() && weapon.nbMissile() == 0;
    }

    public boolean isDead() {
        return life <= 0;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int y) {
        this.positionY = y;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void takeDamage(int damage) {
        this.life -= damage;
    }

    enum Speed {
        LOW(2), NORMAL(4), FAST(6);

        private final int value;

        Speed(int speed) {
            this.value = speed;
        }

        public int getValue() {
            return value;
        }
    }

    enum Life {
        LOW(10), NORMAL(20), TANK(40);

        private final int value;

        Life(int life) {
            this.value = life;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Direction {
        LEFT, RIGHT
    }

}
