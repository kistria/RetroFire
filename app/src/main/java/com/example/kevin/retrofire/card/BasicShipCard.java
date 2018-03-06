package com.example.kevin.retrofire.card;


import android.graphics.Canvas;

import com.example.kevin.retrofire.ship.BasicShip;
import com.example.kevin.retrofire.ship.Ship;

public class BasicShipCard implements Card {
    private final Ship basicShip;

    public BasicShipCard(int positionX, int positionY, int color) {
        this.basicShip = new BasicShip(positionX, positionY, Ship.Direction.RIGHT, color);
    }

    @Override
    public void draw(Canvas canvas) {
        basicShip.draw(canvas);
    }

    @Override
    public Ship getShip() {
        return basicShip;
    }
}
