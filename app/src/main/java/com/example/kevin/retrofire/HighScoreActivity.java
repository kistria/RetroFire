package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class HighScoreActivity extends Activity {
    private ArrayList<String> scores = new ArrayList<>(Arrays.asList("","","","","","","","","",""));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Button back = findViewById(R.id.back);
        TextView scores1 = findViewById(R.id.scores1);
        TextView scores2 = findViewById(R.id.scores2);

        for(int i=1; i<6; i++)
            scores1.setText(scores1.getText().toString()  + i + "." + scores.get(i-1) + "\n");

        for(int i=6; i<11; i++)
            scores2.setText(scores2.getText().toString() + i + "." + scores.get(i-1) + "\n");

        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });


    }
}
