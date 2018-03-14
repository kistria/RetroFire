package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button play = findViewById(R.id.play);
        Button leave = findViewById(R.id.leave);

        leave.setOnClickListener(view -> {
            finish();
        });

        play.setOnClickListener(view -> newActivity(MenuActivity.class));
    }

    private void newActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }


}
