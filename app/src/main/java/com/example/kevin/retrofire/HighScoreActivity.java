package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreActivity extends Activity {
    private ScoreRestService scoreRestService = new ScoreRestService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Button back = findViewById(R.id.back);
        TextView scores1 = findViewById(R.id.scores1);
        TextView scores2 = findViewById(R.id.scores2);

        ArrayList<String> scores = scoreRestService.getHighScoreList();

        for(int i=1; i<6; i++)
            scores1.setText(scores1.getText().toString()  + i + "." + scores.get(i-1).split(" ")[0] + " : " + scores.get(i-1).split(" ")[1]);

        for(int i=6; i<11; i++)
            scores2.setText(scores2.getText().toString() + i + "." + scores.get(i-1).split(" ")[0] + " : " + scores.get(i-1).split(" ")[1]);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });


    }
}
