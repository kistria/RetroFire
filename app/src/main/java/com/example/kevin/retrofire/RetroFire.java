package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kevin.retrofire.card.BasicShipCard;
import com.example.kevin.retrofire.card.SpeedShipCard;
import com.example.kevin.retrofire.card.TankShipCard;

public class RetroFire extends Activity implements View.OnTouchListener, View.OnDragListener {
    private BattleZoneModel model = null;
    private int x;
    private int y;
    private String currentCardId;
    private ProgressBar hp;
    private TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_fire);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.model = new BattleZoneModel();
        BattleZoneView view = findViewById(R.id.battleZone);
        view.setModel(this.model);

        hp = findViewById(R.id.hp);
        hp.setMax(100);
        hp.setProgress(100);

        scoreView = findViewById(R.id.score);
        findViewById(R.id.card1).setOnTouchListener(this);
        findViewById(R.id.card2).setOnTouchListener(this);
        findViewById(R.id.card3).setOnTouchListener(this);
        findViewById(R.id.card4).setOnTouchListener(this);

        view.setOnDragListener(this);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(() -> {
                            int score = model.getScore();
                            scoreView.setText(String.format("%06d",  score));
                            model.setScore(++score);
                            hp.setProgress(model.getHpBar());
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(data, shadowBuilder, view, 0);
            currentCardId = view.getResources().getResourceEntryName(view.getId());
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
                if (currentCardId.equals("card1")) {
                    model.addPlayerCard(new BasicShipCard(x, y, Color.BLUE));
                } else if (currentCardId.equals("card2")) {
                    model.addPlayerCard(new SpeedShipCard(x, y, Color.BLUE));
                } else {
                    model.addPlayerCard(new TankShipCard(x, y, Color.BLUE));
                }
                break;

            case DragEvent.ACTION_DROP:
                break;
            default:
                break;
        }
        return true;
    }

}
