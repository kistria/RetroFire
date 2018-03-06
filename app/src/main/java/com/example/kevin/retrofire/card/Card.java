package com.example.kevin.retrofire.card;


import android.graphics.Canvas;

import com.example.kevin.retrofire.ship.Ship;

public interface Card {
    void draw(Canvas canvas);

    Ship getShip();
}
