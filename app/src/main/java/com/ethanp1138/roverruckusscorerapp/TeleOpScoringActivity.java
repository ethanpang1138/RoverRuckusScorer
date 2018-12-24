package com.ethanp1138.roverruckusscorerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.Arrays;

public class TeleOpScoringActivity extends AppCompatActivity implements ScoringActivity {

    private static final String[] TELEOP_SCORING_ACHIEVEMENTS = {"Gold minerals scored", "Silver minerals scored",
                                                                "Minerals scored in depot", "Latched to Lander",
                                                                "Completely Parked", "Partially Parked", "None"};
    private static final int[] TELEOP_SCORING_POINTS = {5, 5, 2, 50, 25, 15, 0};
    private static int[] prevMinerals = {0, 0, 0};
    private static boolean[] prevEndGameChecked1 = {false, false, false, false};
    private static boolean[] prevEndGameChecked2 = {false, false, false, false};

    //    private ArrayList<CheckBox> cbs1 = null;
//    private ArrayList<CheckBox> cbs2 = null;

    private ArrayList<RadioButton> rbs = null;
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


/*
        CheckBox cb1 = findViewById(R.id.endgame_1);
        CheckBox cb2 = findViewById(R.id.endgame_2);
        CheckBox cb3 = findViewById(R.id.endgame_3);
        CheckBox cb4 = findViewById(R.id.endgame_4);
        CheckBox cb5 = findViewById(R.id.endgame_5);
        CheckBox cb6 = findViewById(R.id.endgame_6);
        CheckBox[] boxes1 = {cb1, cb2, cb3};
        cbs1 = new ArrayList<CheckBox>(Arrays.asList(boxes1));
        for(CheckBox cb : cbs1){
            setEndGameOnClickListeners(cb);
        }
        CheckBox[] boxes2 = {cb4, cb5, cb6};
        cbs2 = new ArrayList<CheckBox>(Arrays.asList(boxes2));
        for(CheckBox cb : cbs2){
            setEndGameOnClickListeners(cb);
        }
*/
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

    // listener for end game radio buttons
    public void onEndGameClick(View view) {
        int index = 0;
        String scoreType = ((RadioButton) view).getText().toString();
        for(int i = 0; i < TELEOP_SCORING_ACHIEVEMENTS.length; i++){
            if(TELEOP_SCORING_ACHIEVEMENTS[i].equals(scoreType)){
                index = i;
            }
        }
        int value = TELEOP_SCORING_POINTS[index];
        points += value;
        for(int i = 0; i < prevEndGameChecked1.length; i++){
            if(prevEndGameChecked1[i] == true){
                int prevPoints = TELEOP_SCORING_POINTS[i + 3];
                points -= prevPoints;
            }
        }
        prevEndGameChecked1[index - 3] = true;
        for(int i = 0; i < prevEndGameChecked1.length; i++){
            if(i != (index - 3)){
                prevEndGameChecked1[i] = false;
            }
        }
    }
/*
    private void setEndGameOnClickListeners(final CheckBox cb){
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {

                for(int i = 0 ; i < cbs1.size(); i++){
                    if(cbs1.get(i).equals(cb)){
                        for(int j = 0; j < cbs1.size(); j++){
                            cbs1.get(j).setChecked(false);
                        }
                    } else {
                        for(int j = 0; j < cbs1.size(); j++){
                            cbs2.get(j).setChecked(false);
                        }
                    }
                }
                cb.setChecked(true);
                String scoreType = button.getText().toString();
                int index = 0;
                for(int i = 0; i < TELEOP_SCORING_ACHIEVEMENTS.length; i++){
                    if(TELEOP_SCORING_ACHIEVEMENTS[i].equals(scoreType)){
                        index = i;
                    }
                }
                int value = TELEOP_SCORING_POINTS[index];
                points += isChecked ? value : -value;
                updateScore();
            }
        });
    }
*/
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

    public void onResetScoreClick(View view) {
//        for(int i = 0; i < cbs1.size(); i++){
//            cbs1.get(i).setChecked(false);
//            cbs2.get(i).setChecked(false);
//        }
        for(int i = 0; i < prevEndGameChecked1.length; i++){

        }
        for(int i = 0; i < ets.size(); i++){
            ets.get(i).setText("");
        }
        points = 0;
        updateScore();
    }

    private void updateScore(){
        TextView scoreText = findViewById(R.id.teleOp_points_text);
        scoreText.setText("Score: " + points);
    }


}
