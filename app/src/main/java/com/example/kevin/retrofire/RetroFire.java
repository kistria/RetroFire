package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kevin.retrofire.card.BasicShipCard;
import com.example.kevin.retrofire.card.SpeedShipCard;
import com.example.kevin.retrofire.card.TankShipCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RetroFire extends Activity implements View.OnTouchListener, View.OnDragListener, SensorEventListener {
    protected volatile boolean running = true;
    private BattleZoneModel model = null;
    private ReachScore br = new ReachScore();
    private IntentFilter filter;
    private int threshold =1000;
    private int x;
    private int y;
    private String currentCardId;
    private ProgressBar hp;
    private TextView scoreView;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Button card1, card2, card3, card4;
    private TextView tvPseudo;
    private int secondsLeft = 0;
    private List<Button> listPlayerCard = new ArrayList<>();
    private ScoreRestService scoreRestService = new ScoreRestService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_fire);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // On récupere le niveau de difficulté
        Intent intent = getIntent();
        int difficulty = intent.getIntExtra("difficulty", 200);
        String pseudo = intent.getStringExtra("pseudo");

        filter = new IntentFilter("ACTION_REACH_SCORE");

        this.model = new BattleZoneModel(difficulty);
        BattleZoneView view = findViewById(R.id.battleZone);
        view.setModel(this.model);

        tvPseudo = findViewById(R.id.pseudo);
        tvPseudo.setText(pseudo);

        hp = findViewById(R.id.hp);
        hp.setMax(100);
        hp.setProgress(100);

        scoreView = findViewById(R.id.score);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        listPlayerCard.addAll(Arrays.asList(card1, card2, card3, card4));

        findViewById(R.id.card1).setOnTouchListener(this);
        findViewById(R.id.card2).setOnTouchListener(this);
        findViewById(R.id.card3).setOnTouchListener(this);
        findViewById(R.id.card4).setOnTouchListener(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        view.setOnDragListener(this);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (running) {
                        runOnUiThread(() -> {
                            int score = model.getScore();
                            scoreView.setText(String.format("%06d", score));
                            model.setScore(++score);
                            hp.setProgress(model.getHpBar());
                            if(score == threshold){
                                filter.addAction("ACTION_REACH_SCORE");
                                sendBroadcast(new Intent("ACTION_REACH_SCORE").putExtra("scoreReach",score));
                                threshold += 1000;
                            }
                            if (model.getHpBar() <= 0) {
                                endGame(score, pseudo, difficulty);
                            }
                        });
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void endGame(int score, String pseudo, int difficulty) {
        Intent intent = new Intent(RetroFire.this, EndGameMenuActivity.class);
        intent.putExtra("pseudo", tvPseudo.getText().toString());
        intent.putExtra("difficulty", difficulty);
        running = false;
        try {
            scoreRestService.sendScore(pseudo, String.valueOf(score));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
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
                    startCooldown(basicCard.getCooldown().getValue());
                } else if (currentCardId.equals("card2")) {
                    SpeedShipCard speedCard = new SpeedShipCard(x, y, Color.BLUE);

                    model.addPlayerCard(speedCard);
                    startCooldown(speedCard.getCooldown().getValue());
                } else if (currentCardId.equals("card3")) {
                    TankShipCard tankCard = new TankShipCard(x, y, Color.BLUE);
                    model.addPlayerCard(tankCard);
                    startCooldown(tankCard.getCooldown().getValue());
                } else if (currentCardId.equals("card4")) {
                    BasicShipCard basicCard = new BasicShipCard(x, y, Color.BLUE);
                    model.addPlayerCard(basicCard);
                    startCooldown(basicCard.getCooldown().getValue());
                }
                break;

            case DragEvent.ACTION_DROP:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        registerReceiver(br,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        unregisterReceiver(br);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            model.accelerometerChange((int) sensorEvent.values[0] - 5);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void startCooldown(Long cooldown) {
        new CountDownTimer(cooldown * 1000, 100) {
            @Override
            public void onTick(long ms) {
                for (Button button : listPlayerCard) {
                    button.setEnabled(false);
                }
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    for (Button c : listPlayerCard) {
                        c.setText(String.valueOf(secondsLeft));
                    }
                }
            }

            @Override
            public void onFinish() {
                for (Button c : listPlayerCard) {
                    c.setEnabled(true);
                }
                card1.setText("Basic");
                card2.setText("Speed");
                card3.setText("Tank");
                card4.setText("Basic");
            }
        }.start();
    }
}
