package com.ethanp1138.roverruckusscorerapp;

import android.os.CountDownTimer;

public class Timer {

    private int maxTime = 0; // time to start from
    private int time = 0; // holds current time
    boolean startFromBeginning = true; // determines whether to begin timer from beginning or not
    ScoringActivity activity = null;
    CountDownTimer timer = null; // android os timer

    // constructor
    public Timer(ScoringActivity activity){
        this.activity = activity;
        // set max time based on auton vs teleop period
        if(activity.getClass() == AutonScoringActivity.class){
            maxTime = 30000;
        } else {
            maxTime = 120000;
        }
        time = maxTime;
    }

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
