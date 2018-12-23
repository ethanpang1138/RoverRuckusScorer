package com.ethanp1138.roverruckusscorerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TeleOpScoringActivity extends AppCompatActivity implements ScoringActivity {

    private static final String[] TELEOP_SCORING_ACHIEVEMENTS = {"Gold minerals scored", "Silver minerals scored", "Minerals scored in depot", "Latched", "Completely Parked", "Partially Parked"};
    private static final int[] TELEOP_SCORING_POINTS = {5, 5, 2, 50, 25, 15};
    private static int[] prevMinerals = {0, 0, 0};
    private static  int points = 0;
    private boolean timerRunning = false;
    private Timer timer = new Timer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_scoring);

        createScoringAchievements();
    }

    private void createScoringAchievements(){
        EditText goldText = findViewById(R.id.gold_mineral_text_field);
        EditText silverText = findViewById(R.id.silver_mineral_text_field);
        EditText depotText = findViewById(R.id.depot_text_field);
        setMineralOnClickListeners(goldText);
        setMineralOnClickListeners(silverText);
        setMineralOnClickListeners(depotText);

        CheckBox cb1 = findViewById(R.id.endgame_1);
        CheckBox cb2 = findViewById(R.id.endgame_2);
        CheckBox cb3 = findViewById(R.id.endgame_3);
        CheckBox cb4 = findViewById(R.id.endgame_4);
        CheckBox cb5 = findViewById(R.id.endgame_5);
        CheckBox cb6 = findViewById(R.id.endgame_6);
        setOnClickListeners(cb1);
        setOnClickListeners(cb2);
        setOnClickListeners(cb3);
        setOnClickListeners(cb4);
        setOnClickListeners(cb5);
        setOnClickListeners(cb6);
    }

    private void setMineralOnClickListeners(EditText et){
        et.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String scoreType = ((EditText)v).getHint().toString();
                TextView scoreText = findViewById(R.id.teleOp_points_text);
                int minerals = Integer.parseInt(((EditText)v).getText().toString());
                int index = 0;
                for(int i = 0; i < TELEOP_SCORING_ACHIEVEMENTS.length; i++){
                    if(TELEOP_SCORING_ACHIEVEMENTS[i].equals(scoreType)){
                        index = i;
                    }
                }
                int value = TELEOP_SCORING_POINTS[index];
                int mineralDiff = minerals - prevMinerals[index];
                points += value * mineralDiff;
                scoreText.setText("Score: " + points);
                prevMinerals[index] = minerals;
            }
        });
    }

    private void setOnClickListeners(CheckBox cb){
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                String scoreType = button.getText().toString();
                TextView scoreText = findViewById(R.id.teleOp_points_text);
                int index = 0;
                for(int i = 0; i < TELEOP_SCORING_ACHIEVEMENTS.length; i++){
                    if(TELEOP_SCORING_ACHIEVEMENTS[i].equals(scoreType)){
                        index = i;
                    }
                }
                int value = TELEOP_SCORING_POINTS[index];
                points += isChecked ? value : -value;
                scoreText.setText("Score: " + points);
            }
        });
    }

    public void onStartTimerClick(View view){
        Button startStopButton = (Button)view;
        if(!startStopButton.getText().toString().contains(" 0 ")){
            timerRunning = !timerRunning;
            if(timerRunning){
                timer.startTimer();
                startStopButton.setText("Stop");
            } else {
                timer.stopTimer();
                startStopButton.setText("Resume");
            }
        }
    }
    public void onRestartTimerClick(View view){
        timer.restartTimer();
        updateTimer(120);
        Button startStopButton = findViewById(R.id.teleop_start_timer_button);
        startStopButton.setText("Start");
        timerRunning = false;
    }
    public void updateTimer(int currentTime){
        final TextView timerText = findViewById(R.id.teleop_timer);
        String formattedTime = formatTime(currentTime);
        timerText.setText("Time: " + formattedTime);
    }
    public void timeUp(){
        Toast.makeText(getApplicationContext(),"time is up", Toast.LENGTH_SHORT).show();
    }
    private String formatTime(int time){
        int min = 0;
        int sec = time % 60;
        while(time >= 60){
            time -= 60;
            min++;
        }
        String newTime = "" + min + "m " + sec + "s";
        return newTime;
    }

    public void onAutonScoringClick(View view) {
        Intent toAuton = new Intent(this, AutonScoringActivity.class);
        startActivity(toAuton);
    }

    public void onHomeScreenClick(View view){
        Intent toHome = new Intent(this, StartMenuActivity.class);
        startActivity(toHome);
    }

}
