package com.example.kevin.retrofire.ship;


import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Weapon {
    private final RateOfFire rateOfFire;
    private final List<Missile> missiles;
    private Ship.Direction direction;
    private FirePower firePower;
    private int cmpt = 0;

    public Weapon(RateOfFire rateOfFire, Ship.Direction direction, FirePower firePower) {
        this.rateOfFire = rateOfFire;
        this.missiles = new ArrayList<>();
        this.direction = direction;
        this.firePower = firePower;
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

    public boolean hasHit(Ship ship) {
        return missiles.removeIf(missile -> {
            boolean hit = missile.hasHit(ship);
            if (hit) {
                ship.takeDamage(firePower.value);
            }
            return hit;
        });
    }

    public int nbMissile() {
        return missiles.size();
    }

    public enum RateOfFire {
        LOW(50), NORMAL(40), FAST(10);

        private final int value;

        RateOfFire(int value) {
            this.value = value;
        }
    }

    public enum FirePower {
        LOW(5), NORMAL(10), STRONG(20);

        private final int value;

        FirePower(int value) {
            this.value = value;
        }
    }

}
