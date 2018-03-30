package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HighScoreActivity extends Activity {
    private ScoreRestService scoreRestService = new ScoreRestService();
    private ArrayList<String> scores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Button back = findViewById(R.id.back);
        TextView scores1 = findViewById(R.id.scores1);
        TextView scores2 = findViewById(R.id.scores2);

        try {
            scores = scoreRestService.getHighScoreList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = scores.size(); i < 10; i++) {
            scores.add("");
        }

        for(int i=1; i<6; i++)
            setScores(scores1, i);

        for(int i=6; i<11; i++)
            setScores(scores2, i);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });


    }

    private void setScores(TextView textView, int i){
        if(!scores.get(i-1).equals(""))
            textView.setText(textView.getText().toString()  + i + ". " + scores.get(i-1).split(" ")[0] + " : " + scores.get(i-1).split(" ")[1]);
    }
}
