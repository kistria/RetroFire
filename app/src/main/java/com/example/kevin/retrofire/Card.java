package com.example.kevin.retrofire;


import android.graphics.Canvas;

interface Card {
    int WIDTH = 100;
    int HEIGHT = 100;

    boolean checkShipCollision(Card card);

    void update(int width, int height);

    boolean canRemove();

    boolean isDead();

    int getPositionX();

    int getPositionY();

    Weapon getWeapon();

    void takeDamage(int damage);

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

    enum Direction {
        LEFT, RIGHT
    }

    void draw(Canvas canvas);

    boolean checkEdgesCollision(int width, int height);
}
