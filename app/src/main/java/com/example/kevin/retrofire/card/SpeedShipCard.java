package com.example.kevin.retrofire.card;


import android.graphics.Canvas;

import com.example.kevin.retrofire.ship.Ship;
import com.example.kevin.retrofire.ship.SpeedShip;

public class SpeedShipCard implements Card {
    private final Ship speedShip;

    public SpeedShipCard(int positionX, int positionY, int color) {
        this.speedShip = new SpeedShip(positionX, positionY, Ship.Direction.RIGHT, color);
    }

    @Override
    public void draw(Canvas canvas) {
        speedShip.draw(canvas);
    }

    @Override
    public Ship getShip() {
        return speedShip;
    }

    @Override
    public Cooldown getCooldown() {
        return Cooldown.NORMAL;
    }
}
