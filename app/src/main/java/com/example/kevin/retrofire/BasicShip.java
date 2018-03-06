package com.example.kevin.retrofire;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BasicShip extends Ship {

    public BasicShip(int positionX, int positionY, Direction direction, int color) {
        super(positionX, positionY, Life.NORMAL.getValue(), Speed.NORMAL, direction, color, new Weapon(Weapon.RateOfFire.NORMAL, direction, Weapon.FirePower.NORMAL));
    }

    @Override
    public void draw(Canvas canvas) {
        getWeapon().draw(canvas);

        if (!isDead()) {
            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawRect(getPositionX(), getPositionY(), getPositionX() + getWIDTH(), getPositionY() + getHEIGHT(), paint);
        }
    }
}
