package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class EndGameMenuActivity extends Activity {

    private String pseudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_end);

        Button replay = findViewById(R.id.replay);
        Button home = findViewById(R.id.home);
        Button leave = findViewById(R.id.leave);

        Intent intent = getIntent();
        pseudo = intent.getStringExtra("pseudo");
        int difficulty = intent.getIntExtra("difficulty", 200);

        replay.setOnClickListener(view -> {
            Intent intentReplay = new Intent(this, RetroFire.class);
            intentReplay.putExtra("pseudo", pseudo);
            intentReplay.putExtra("difficulty", difficulty);
            startActivity(intentReplay);
        });

        home.setOnClickListener(view -> {
            Intent intentHome = new Intent(this, HomeActivity.class);
            startActivity(intentHome);
        });

        leave.setOnClickListener(view -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });

    }


}
