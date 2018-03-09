package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.kevin.retrofire.card.BasicShipCard;
import com.example.kevin.retrofire.card.SpeedShipCard;
import com.example.kevin.retrofire.card.TankShipCard;

public class RetroFire extends Activity implements View.OnTouchListener, View.OnDragListener {
    private BattleZoneModel model = null;
    private int x;
    private int y;
    private String currentCardId;
    private Button card1, card2, card3, card4;
    private int secondsLeft = 0;

    private static final String TAG= "RetroFireActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_fire);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.model = new BattleZoneModel();
        BattleZoneView view = findViewById(R.id.battleZone);
        view.setModel(this.model);

        card1 = findViewById(R.id.card1);
        card2 =findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
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
                    BasicShipCard basicCard = new BasicShipCard(x, y, Color.BLUE);
                    model.addPlayerCard(basicCard);

                    startCooldown(basicCard.getCooldown().getValue(),card1);
                } else if (currentCardId.equals("card2")) {
                    SpeedShipCard speedCard = new SpeedShipCard(x, y, Color.BLUE);
                    model.addPlayerCard(speedCard);
                    startCooldown(speedCard.getCooldown().getValue(),card2);
                } else {
                    TankShipCard tankCard = new TankShipCard(x, y, Color.BLUE);
                    model.addPlayerCard(tankCard);
                    startCooldown(tankCard.getCooldown().getValue(), card3);
                }
                break;

            case DragEvent.ACTION_DROP:
                break;
            default:
                break;
        }
        return true;
    }

    private void startCooldown(Long cooldown,Button card){
        new CountDownTimer(cooldown * 1000,100){
            @Override
            public void onTick(long ms) {
                card.setEnabled(false);
                if (Math.round((float)ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float)ms / 1000.0f);
                    card.setText(String.valueOf(secondsLeft));
                }
            }
            @Override
            public void onFinish() {
                card.setEnabled(true);
            }
        }.start();
    }
}
