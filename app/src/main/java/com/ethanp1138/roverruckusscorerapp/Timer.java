package com.ethanp1138.roverruckusscorerapp;

import android.os.CountDownTimer;
import android.widget.Toast;

public class Timer {

    private final int maxTime = 30000;
    private int time = 30000;
    boolean startFromBeginning = true;
    AutonScoringActivity activity = null;
    CountDownTimer timer = null;
    public Timer(AutonScoringActivity activity){
        this.activity = activity;
    }


    public void startTimer(){
        if(startFromBeginning){
            initTimer(maxTime);
            timer.start();
            startFromBeginning = false;
        } else {
            resumeTimer(time);
        }
    }
    private void resumeTimer(int currentTime){
        initTimer(currentTime);
        timer.start();
    }

    public void restartTimer(){
        stopTimer();
        initTimer(maxTime);
        startFromBeginning = true;
    }
    private void initTimer(int startTime){
        timer = new CountDownTimer(startTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = (int)millisUntilFinished;
                activity.updateTimer(time / 1000);
            }

            @Override
            public void onFinish() {
                activity.updateTimer(0);
                activity.timeUp();
            }
        };
    }
    public void stopTimer(){
        timer.cancel();
    }
}
