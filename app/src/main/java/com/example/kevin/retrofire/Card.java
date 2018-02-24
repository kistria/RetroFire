package com.example.kevin.retrofire;


import android.graphics.Canvas;

interface Card {
    enum Speed{
        LOW(2), NORMAL(4), FAST(6);

        private final int value;

        Speed(int speed){
            this.value = speed;
        }

        public int getValue(){
            return value;
        }
    }

    enum Direction {
        LEFT, RIGHT
    }

    void draw(Canvas canvas);

    boolean checkEdgesCollision(int width, int height);

    void update();
}
