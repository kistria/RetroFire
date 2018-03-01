package com.example.kevin.retrofire;


import android.graphics.Canvas;

interface Card {
    //TODO rename Card en Ship et Ship en BasicShip ou je sais pas
    int WIDTH = 100;
    int HEIGHT = 100;

    void update(int width, int height);

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
