package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class EndGameMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_end);

        Button replay = findViewById(R.id.replay);
        Button leave = findViewById(R.id.leave);

        replay.setOnClickListener(view -> {
            Intent intent = new Intent(this, RetroFire.class);
            startActivity(intent);
        });

        leave.setOnClickListener(view -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });

    }


}
