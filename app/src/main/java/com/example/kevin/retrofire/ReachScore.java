package com.example.kevin.retrofire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/* Display the score every 1000 hits */

public class ReachScore extends BroadcastReceiver{

    private String language;

    private final String ACTION_REACH_SCORE ="ACTION_REACH_SCORE";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ACTION_REACH_SCORE)){
            int score = intent.getIntExtra("scoreReach",1);
            language = context.getResources().getConfiguration().locale.getDisplayLanguage();
            if(language.equals("français")){
                Toast.makeText(context, "Vous avez atteint un score de plus de "+ score+" !", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "You have reached a score of over "+ score+" !", Toast.LENGTH_LONG).show();
            }

        }
    }
}


