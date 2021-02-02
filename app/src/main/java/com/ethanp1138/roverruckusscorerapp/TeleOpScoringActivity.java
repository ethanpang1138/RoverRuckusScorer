package com.ethanp1138.roverruckusscorerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class TeleOpScoringActivity extends AppCompatActivity implements ScoringActivity {

    private static final String[] TELEOP_SCORING_ACHIEVEMENTS = {"Gold minerals scored", "Silver minerals scored",
                                                                    "Minerals scored in depot", };
    private static final String[] ENDGAME_SCORING_ACHIEVEMENTS = {"Latched to Lander (50pts)", "Completely Parked (25pts)",
                                                                    "Partially Parked (15pts)", "None"};
    private static final int[] TELEOP_SCORING_POINTS = {5, 5, 2};
    private static final int[] ENDGAME_SCORING_POINTS = {50,25,15,0};
    private static int[] prevMinerals = {0, 0, 0};
    private static boolean[] prevEndGameChecked1 = {false, false, false, false};
    private static boolean[] prevEndGameChecked2 = {false, false, false, false};
    private ArrayList<RadioGroup> rgs = new ArrayList<>();
    private  ArrayList<EditText> ets = null;
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
        EditText[] edits = {goldText, silverText, depotText};
        ets = new ArrayList<EditText>(Arrays.asList(edits));
        for(EditText et : ets){
            setMineralOnClickListeners(et);
        }
        RadioGroup rg1 = findViewById(R.id.endGame_buttons_1);
        RadioGroup rg2 = findViewById(R.id.endGame_buttons_2);
        rgs.add(rg1);
        rgs.add(rg2);
    }

    private void setMineralOnClickListeners(final EditText et){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                int index = 0;
                String scoreType = et.getHint().toString();
                for(int i = 0; i < TELEOP_SCORING_ACHIEVEMENTS.length; i++){
                    if(TELEOP_SCORING_ACHIEVEMENTS[i].equals(scoreType)){
                        index = i;
                    }
                }
                int value = TELEOP_SCORING_POINTS[index];
                int minerals = 0;
                if(c.toString().length() == 0){
                    minerals = 0;
                } else {
                    minerals = Integer.parseInt(c.toString());
                }
                int mineralDiff = minerals - prevMinerals[index];
                points += mineralDiff * value;
                prevMinerals[index] = minerals;
                updateScore();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // listener for Robot 1 end game
    public void onEndGame1Click(View view) {
        // find the correct points for the radio button and add to points
        int index = 0;
        String scoreType = ((RadioButton) view).getText().toString();
        for(int i = 0; i < ENDGAME_SCORING_ACHIEVEMENTS.length; i++){
            if(ENDGAME_SCORING_ACHIEVEMENTS[i].equals(scoreType)){
                index = i;
            }
        }
        int value = ENDGAME_SCORING_POINTS[index];
        points += value;

        // find the points from the previous radio button and subtract it from points
        for(int i = 0; i < prevEndGameChecked1.length; i++){
            if(prevEndGameChecked1[i] == true){
                int prevPoints = ENDGAME_SCORING_POINTS[i];
                points -= prevPoints;
                prevEndGameChecked1[i] = false;
            }
        }
        // keep track that this button has been checked for next time
        prevEndGameChecked1[index] = true;
        updateScore();
    }

    // listener for Robot 2 end game
    public void onEndGame2Click(View view) {
        // find the correct points for the radio button and add to points
        int index = 0;
        String scoreType = ((RadioButton) view).getText().toString();
        for(int i = 0; i < ENDGAME_SCORING_ACHIEVEMENTS.length; i++){
            if(ENDGAME_SCORING_ACHIEVEMENTS[i].equals(scoreType)){
                index = i;
            }
        }
        int value = ENDGAME_SCORING_POINTS[index];
        points += value;

        //find the RadioGroup the button is in
        // get the id of the RadioGroup to determine which points to change
        // find the points from the previous radio button and subtract it from points
        for(int i = 0; i < prevEndGameChecked2.length; i++){
            if(prevEndGameChecked2[i] == true){
                int prevPoints = ENDGAME_SCORING_POINTS[i];
                points -= prevPoints;
                prevEndGameChecked2[i] = false;
            }
        }
        // keep track that this button has been checked for next time
        prevEndGameChecked2[index] = true;
        updateScore();
    }

    // start timer from where it left off
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
    // reset timer to max time
    public void onRestartTimerClick(View view){
        timer.restartTimer();
        updateTimer(120);
        Button startStopButton = findViewById(R.id.teleop_start_timer_button);
        startStopButton.setText("Start");
        timerRunning = false;
    }
    // update what the timer displays
    public void updateTimer(int currentTime){
        final TextView timerText = findViewById(R.id.teleop_timer);
        String formattedTime = formatTime(currentTime);
        timerText.setText("Time: " + formattedTime);
    }
    // alert user that time is up
    public void timeUp(){
        Toast.makeText(getApplicationContext(),"time is up", Toast.LENGTH_SHORT).show();
    }
    // convert time to "x m y s" format
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
    // button to go to auton scoring
    public void onAutonScoringClick(View view) {
        Intent toAuton = new Intent(this, AutonScoringActivity.class);
        startActivity(toAuton);
    }
    // button to go to home screen
    public void onHomeScreenClick(View view){
        Intent toHome = new Intent(this, StartMenuActivity.class);
        startActivity(toHome);
    }
    // button to reset score to 0 and unselect everything
    public void onResetScoreClick(View view) {
        // clear mineral text fields
        for(int i = 0; i < ets.size(); i++){
            ets.get(i).setText("");
        }
        // uncheck endgame all buttons
        for(RadioGroup rg : rgs){
            RadioButton rb1 = findViewById(rg.getCheckedRadioButtonId());
            rb1.setChecked(false);
        }
        points = 0;
        updateScore();
    }

    private void updateScore(){
        TextView scoreText = findViewById(R.id.teleOp_points_text);
        scoreText.setText("Score: " + points);
    }


}
