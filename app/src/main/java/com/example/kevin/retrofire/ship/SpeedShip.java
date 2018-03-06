package com.example.kevin.retrofire.ship;


import android.graphics.Canvas;
import android.graphics.Paint;

public class SpeedShip extends Ship {

    public SpeedShip(int positionX, int positionY, Direction direction, int color) {
        super(positionX, positionY, Life.LOW.getValue(), Speed.FAST, direction, color, new Weapon(Weapon.RateOfFire.FAST, direction, Weapon.FirePower.LOW), Cooldown.NORMAL);

    }

    @Override
    public void draw(Canvas canvas) {
        getWeapon().draw(canvas);

        if (!isDead()) {
            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawCircle(getPositionX() + getWIDTH() / 2, getPositionY() + getHEIGHT() / 2, getWIDTH() / 2, paint);
        }
    }
}
