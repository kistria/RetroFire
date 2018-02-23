package com.example.kevin.retrofire;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class RetroFire extends Activity {
    private BattleZoneModel model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_fire);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        this.model = new BattleZoneModel(metrics.widthPixels, metrics.heightPixels);
        ((BattleZoneView) findViewById(R.id.battleZone)).setModel(this.model);
    }

    public void onCardClicked(View view){
        model.addPlayerCard(new Ship(0,0, 100, 3));
    }
}
