package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class EndGameMenuActivity extends Activity {

    private String pseudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_end);

        Button replay = findViewById(R.id.replay);
        Button leave = findViewById(R.id.leave);

        Intent intent = getIntent();
        pseudo = intent.getStringExtra("pseudo");

        replay.setOnClickListener(view -> {
            Intent intentReplay = new Intent(this, RetroFire.class);
            intentReplay.putExtra("pseudo", pseudo);
            startActivity(intentReplay);
        });

        leave.setOnClickListener(view -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });

    }


}
