package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class RetroFire extends Activity implements View.OnTouchListener, View.OnDragListener {
    private BattleZoneModel model = null;
    private int x;
    private int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_fire);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.model = new BattleZoneModel();
        BattleZoneView view = findViewById(R.id.battleZone);
        view.setModel(this.model);

        findViewById(R.id.card1).setOnTouchListener(this);
        findViewById(R.id.card2).setOnTouchListener(this);
        findViewById(R.id.card3).setOnTouchListener(this);
        findViewById(R.id.card4).setOnTouchListener(this);

        view.setOnDragListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(data, shadowBuilder, view, 0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                break;

            case DragEvent.ACTION_DRAG_LOCATION:
                x = (int) dragEvent.getX();
                y = (int) dragEvent.getY();
                break;

            case DragEvent.ACTION_DRAG_ENDED:
                model.addPlayerCard(new Ship(x, y, 10, Card.Speed.FAST, Card.Direction.RIGHT));
                break;

            case DragEvent.ACTION_DROP:
                break;
            default:
                break;
        }
        return true;
    }
}
