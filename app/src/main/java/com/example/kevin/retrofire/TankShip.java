package com.example.kevin.retrofire;


import android.graphics.Canvas;
import android.graphics.Paint;

public class TankShip extends Ship {

    public TankShip(int positionX, int positionY, Direction direction, int color) {
        super(positionX, positionY, Life.TANK.getValue(), Speed.LOW, direction, color, new Weapon(Weapon.RateOfFire.LOW, direction, Weapon.FirePower.STRONG));
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
