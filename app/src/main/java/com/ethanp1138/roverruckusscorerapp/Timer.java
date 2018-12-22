package com.ethanp1138.roverruckusscorerapp;

import android.os.CountDownTimer;
import android.widget.Toast;

public class Timer {

    private final int maxTime = 30000; // auton time limit is 30 seconds
    private int time = 30000; // holds current time
    boolean startFromBeginning = true; // determines whether to begin timer from 30 sec or not
    AutonScoringActivity activity = null;
    CountDownTimer timer = null; // android os timer
    public Timer(AutonScoringActivity activity){
        this.activity = activity;
    } // constructor

    // starts timer from beginning or from where it left off depending on boolean: startFromBeginning
    public void startTimer(){
        if(startFromBeginning){
            initTimer(maxTime);
            timer.start();
            startFromBeginning = false;
        } else {
            resumeTimer(time);
        }
    }
    // starts a timer from the current time
    private void resumeTimer(int currentTime){
        initTimer(currentTime);
        timer.start();
    }
    // stops timer and start a new one from beginning
    public void restartTimer(){
        stopTimer();
        initTimer(maxTime);
        startFromBeginning = true;
    }
    // makes a new timer with given start time
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
    // stops timer
    public void stopTimer(){
        if(timer != null) {
            timer.cancel();
        }
    }
}
