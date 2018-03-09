package com.example.kevin.retrofire.card;


import android.graphics.Canvas;

import com.example.kevin.retrofire.ship.Ship;

public interface Card {
    // payback
    enum Cooldown {
        LOW(4), NORMAL(2);

        private final long value;

        Cooldown(long cd) { this.value = cd; }

        public long getValue() { return value; }
    }

    void draw(Canvas canvas);

    Ship getShip();

    Cooldown getCooldown();

}
