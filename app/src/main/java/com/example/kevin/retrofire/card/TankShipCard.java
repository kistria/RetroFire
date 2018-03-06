package com.example.kevin.retrofire.card;


import android.graphics.Canvas;

import com.example.kevin.retrofire.ship.Ship;
import com.example.kevin.retrofire.ship.TankShip;

public class TankShipCard implements Card {
    private final Ship tankShip;

    public TankShipCard(int positionX, int positionY, int color) {
        this.tankShip = new TankShip(positionX, positionY, Ship.Direction.RIGHT, color);
    }

    @Override
    public void draw(Canvas canvas) {
        tankShip.draw(canvas);
    }

    @Override
    public Ship getShip() {
        return tankShip;
    }
}
