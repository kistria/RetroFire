package com.example.kevin.retrofire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button b,b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        b = (Button)findViewById(R.id.button3);
        b1 = (Button)findViewById(R.id.button4);
        b2 = (Button)findViewById(R.id.button5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newActivity(RetroFire.class);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newActivity(RetroFire.class);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newActivity(RetroFire.class);
            }
        });

    }

    private void newActivity(Class c) {
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }

}
