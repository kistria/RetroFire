package com.example.kevin.retrofire;


import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Weapon {
    public enum RateOfFire {
        LOW(15), NORMAL(10), FAST(5);

        private final int value;

        RateOfFire(int value) {
            this.value = value;
        }
    }

    private final RateOfFire rateOfFire;
    private final List<Missile> missiles;
    private final Card.Direction direction;
    private int cmpt = 0;

    public Weapon(RateOfFire rateOfFire, Card.Direction direction) {
        this.rateOfFire = rateOfFire;
        this.missiles = new ArrayList<>();
        this.direction = direction;
    }

    public void fire(int positionX, int positionY) {
        if (cmpt % rateOfFire.value == 0) {
            missiles.add(new Missile(positionX, positionY, direction));
        }
        cmpt++;
    }

    public void draw(Canvas canvas) {
        missiles.forEach(missile -> missile.draw(canvas));
    }

    public void update(int width, int height) {
        missiles.forEach(Missile::update);
        missiles.removeIf(missile -> missile.checkEdgesCollision(width, height));
    }
}
