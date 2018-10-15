package com.ethanp1138.roverruckusscorerapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AutonScoringActivity extends AppCompatActivity {

    private static final String[] SCORING_ACHIEVEMENTS = {"Landed", "Depot Claimed", "Parked in Crater", "Gold Mineral Sampled"};
    private static final int[] ACHIEVEMENT_POINTS = {30, 15, 10, 25};
    private static int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auton_scoring);

        //get layout of the scoring activity
        LinearLayout layout = findViewById(R.id.widget_scoring_list);


        //create a label and two checkboxes for each scoring achievement
        for(String achievement : SCORING_ACHIEVEMENTS){
            addScoringAchievement(achievement, layout);
        }
    }


    private void addScoringAchievement(String achievement, LinearLayout layout){

        //set up scoring to screen wih the scoring_achievement.xml
        View scoringWidget = getLayoutInflater().inflate(R.layout.scoring_achievement, null);

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
                //get the top level layout that this view is in
                ViewGroup topLayout = (ViewGroup)button.getRootView();
                //get the linearLayout, which is the widget itself
                ViewGroup linearLayout = (ViewGroup) topLayout.getChildAt(0);

                int children = topLayout.getChildCount();
                int childcount = linearLayout.getChildCount();
                String accessName = linearLayout.getAccessibilityClassName().toString();

                //get the text which specifies the achievement with id
                //String achievementText = ((TextView)linearLayout.findViewById(R.id.scoring_text)).getText().toString();

                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(AutonScoringActivity.this);
                builder.setMessage("layout: " + accessName + "\n text: " + achievementText + "\n topLayout child count: " + children +
                                   "\n linearLayout child count: " + childcount);
                AlertDialog dialog = builder.create();
                dialog.show();
*/
                //get text view which displays score
                TextView scoreText = linearLayout.findViewById(R.id.points_text);
                //add points based on the type of scoring opportunity(landing earns some points, while parking earns a different amount)
                int index = 0;
                //find the scoring achievement
                for(int i = 0; i < SCORING_ACHIEVEMENTS.length; i++){
                    if(SCORING_ACHIEVEMENTS[i].equals(achievementText)){
                        index = i;
                    }
                }
                int value = ACHIEVEMENT_POINTS[index];
                //add/subtract the corresponding amount to points and update pointsText
                if(isChecked){
                    points += value;
                } else {
                    points -= value;
                }
                scoreText.setText("Score: " + points);

            }
        });
    }

}
