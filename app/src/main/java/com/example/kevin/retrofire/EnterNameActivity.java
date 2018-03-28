package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EnterNameActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        EditText pseudo = findViewById(R.id.player_name);
        Button start = findViewById(R.id.start);


        start.setOnClickListener(view -> newActivity(pseudo.getText().toString()));
    }

    private void newActivity(String pseudo) {
        Intent intent = new Intent(this, RetroFire.class);
        intent.putExtra("pseudo", pseudo);
        startActivity(intent);
    }
}
