package com.ethanp1138.roverruckusscorerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AutonScoringActivity extends AppCompatActivity implements ScoringActivity {

    private static final String[] SCORING_ACHIEVEMENTS = {"Landed (30pts)", "Gold Mineral Sampled (25pts)", "Depot Claimed (15pts)", "Parked in Crater (10pts)"};
    private static final int[] ACHIEVEMENT_POINTS = {30, 25, 15, 10};
    private static int points = 0;
    private boolean timerRunning = false;
    private Timer timer = new Timer(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auton_scoring);

        // get layout of the auton scoring activity
        LinearLayout layout = findViewById(R.id.auton_widget_scoring_list);

        // create a label and two checkboxes for each scoring achievement
        for(String achievement : SCORING_ACHIEVEMENTS){
            addScoringAchievement(achievement, layout);
        }
    }
    // run timer
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
        updateTimer(30);
        Button startStopButton = findViewById(R.id.auton_start_timer_button);
        startStopButton.setText("Start");
        timerRunning = false;
    }
    // update the displayed time
    public void updateTimer(int currentTime){
        final TextView timerText = findViewById(R.id.auton_timer);
        timerText.setText("Time: " + currentTime + "s");
    }
    // alert user that time is up
    public void timeUp(){
        Toast.makeText(getApplicationContext(),"time is up", Toast.LENGTH_SHORT).show();
    }

    private void addScoringAchievement(String achievement, LinearLayout layout){

        //set up scoring to screen wih the auton_scoring_achievement.xmlnt.xml
        View scoringWidget = getLayoutInflater().inflate(R.layout.auton_scoring_achievement, null);

        //get scoring text view with id to change label text
        TextView achievementText = scoringWidget.findViewById(R.id.scoring_text);
        achievementText.setText(achievement);

        //get checkboxes with id
        CheckBox cb1 = scoringWidget.findViewById(R.id.robot1_checkbox);
        CheckBox cb2 = scoringWidget.findViewById(R.id.robot2_checkbox);

        //use helper method to set onClickListeners
        setOnClickListeners(cb1, achievement);
        setOnClickListeners(cb2, achievement);

        //add widget to layout
        layout.addView(scoringWidget);
    }


    //helper method to set onClickListeners and update points accordingly
    private void setOnClickListeners(CheckBox cb, final String achievementText){

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked){
                // get the top level layout that this view is in
                ViewGroup topLayout = (ViewGroup)button.getRootView();
                // get the linearLayout, which is the title widget
                ViewGroup titleLayout = (ViewGroup) topLayout.getChildAt(0);

                // get the text which specifies the achievement with id
                // String achievementText = ((TextView)linearLayout.findViewById(R.id.scoring_text)).getText().toString();

                //add points based on the type of scoring opportunity(landing earns some points, while parking earns a different amount)
                int index = 0;
                // find the scoring achievement
                for(int i = 0; i < SCORING_ACHIEVEMENTS.length; i++){
                    if(SCORING_ACHIEVEMENTS[i].equals(achievementText)){
                        index = i;
                    }
                }
                int value = ACHIEVEMENT_POINTS[index];
                // add/subtract the corresponding amount to points and update pointsText
                if(isChecked){
                    points += value;
                } else {
                    points -= value;
                }
                updateScore();

            }
        });
    }
    // go to teleOp scoring
    public void onTeleOpScoringClick(View view) {
        Intent toTeleOp = new Intent(this, TeleOpScoringActivity.class);
        startActivity(toTeleOp);
    }
    // go to home screen
    public void onHomeScreenClick(View view){
        Intent toHome = new Intent(this, StartMenuActivity.class);
        startActivity(toHome);
    }

    public void onResetScoreClick(View view) {
        // get layout of the auton scoring activity
        LinearLayout layout = findViewById(R.id.auton_widget_scoring_list);
        int num = layout.getChildCount();
        for(int i = 0 ; i < num; i++){
            LinearLayout linear = (LinearLayout) layout.getChildAt(i);
            RelativeLayout relative = (RelativeLayout) linear.getChildAt(1);
            CheckBox cb1 = (CheckBox) relative.getChildAt(0);
            CheckBox cb2 = (CheckBox) relative.getChildAt(1);
            cb1.setChecked(false);
            cb2.setChecked(false);
        }

        points = 0;
        updateScore();
    }

    private void updateScore(){
        TextView scoreText = findViewById(R.id.auton_points_text);
        scoreText.setText("Score: " + points);
    }
}
