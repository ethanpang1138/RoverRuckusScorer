package com.ethanp1138.roverruckusscorerapp;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public interface ScoringActivity{

    public void onStartTimerClick(View view);
    public void onRestartTimerClick(View view);
    public void updateTimer(int currentTime);
    public void timeUp();

}
