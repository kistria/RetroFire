package com.example.kevin.retrofire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EnterNameActivity extends Activity{

    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        EditText pseudo = findViewById(R.id.player_name);
        Button start = findViewById(R.id.start);

        language = this.getResources().getConfiguration().locale.getDisplayLanguage();
        Intent intent = getIntent();
        int difficulty = intent.getIntExtra("difficulty", 300);

        start.setOnClickListener(view -> newActivity(pseudo.getText().toString(), difficulty));
    }

    private void newActivity(String pseudo, int difficulty) {
        Intent intent = new Intent(this, RetroFire.class);
        if(pseudo.isEmpty()){
            if(language.equals("français")){
                pseudo = "JoueurInconnu";
            } else {
                pseudo = "UnknownPlayer";
            }
        }
        intent.putExtra("pseudo", pseudo);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}
