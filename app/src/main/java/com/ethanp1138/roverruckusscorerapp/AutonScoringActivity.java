package com.ethanp1138.roverruckusscorerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AutonScoringActivity extends AppCompatActivity {

    private static String[] SCORINGACHIEVEMENTS = {"Landed", "Depot Claimed", "Parked in Crater", "Gold Mineral Sampled"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auton_scoring);

        //get layout of the scoring activity
        LinearLayout layout = findViewById(R.id.activity_auton_scoring);


        //create a label and two checkboxes for each scoring achievement
        for(String achievement : SCORINGACHIEVEMENTS){
            addScoringAchievement(achievement, layout);
        }
    }

    private void addScoringAchievement(String achievement, LinearLayout layout){

        //set up scoring to screen wih the scoring_achievement.xml
        View scoring = getLayoutInflater().inflate(R.layout.scoring_achievement,null);

        //get widget with id to change label text
        TextView tv = scoring.findViewById(R.id.scoring_text);
        tv.setText(achievement);

        //add widget to layout
        layout.addView(scoring);
    }
}
