package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button easy = findViewById(R.id.easy);
        Button medium = findViewById(R.id.medium);
        Button hard = findViewById(R.id.hard);

        easy.setOnClickListener(view -> newActivity(Difficulty.EASY));
        medium.setOnClickListener(view -> newActivity(Difficulty.MEDIUM));
        hard.setOnClickListener(view -> newActivity(Difficulty.HARD));
    }

    private void newActivity(Difficulty difficulty) {
        Intent intent = new Intent(this, EnterNameActivity.class);
        intent.putExtra("difficulty", difficulty.getValue());
        startActivity(intent);
    }

    enum Difficulty {
        EASY(300), MEDIUM(200), HARD(80);

        private final int value;

        Difficulty(int difficulty) {
            this.value = difficulty;
        }

        public int getValue() {
            return value;
        }
    }

}
