package com.example.kevin.retrofire;


import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Weapon {
    public enum RateOfFire {
        LOW(50), NORMAL(40), FAST(30);

        private final int value;

        RateOfFire(int value) {
            this.value = value;
        }
    }

    private final RateOfFire rateOfFire;
    private final List<Missile> missiles;
    private final Card.Direction direction;
    //TODO enum firePower
    private int firePower = 10;
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

    public boolean hasHit(Card card) {
        return missiles.removeIf(missile -> {
            boolean hit = missile.hasHit(card);
            if (hit) {
                card.takeDamage(firePower);
            }
            return hit;
        });
    }

    public int nbMissile() {
        return missiles.size();
    }
}
