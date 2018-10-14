package com.example.ethan.biggernumbertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import stanford.androidlib.SimpleActivity;

public class MainActivity extends SimpleActivity {
    //random nums for buttons
    private int rand1;
    private int rand2;
    //total points
    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //when app loads
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        points = 0;

        pickRandNums();
    }

    //assigns random nums to the buttons
    private void pickRandNums(){
        Random rand = new Random();
        rand1 = rand.nextInt(10);
        rand2 = rand.nextInt(10);
        while(rand2 == rand1){
            rand2 = rand.nextInt(10);
        }
        //display values
        //Button LButton = (Button)findById(R.id.left_button);
        Button LButton = $B(R.id.left_button);
        Button RButton = $B(R.id.right_button);
        LButton.setText(Integer.toString(rand1));
        RButton.setText(Integer.toString(rand2));

    }

    //updates scores on buttons and randomizes nums again
    private void updateScore(Boolean isLeft){
        //points = (rand1>rand2 ? (isLeft ? points + 1 : points - 1) : (isLeft ? points - 1 : points + 1));
        Boolean correct = false;

        if(isLeft){
            if(rand1 > rand2) correct = true;
        } else {
            if(rand2 > rand1) correct = true;
        }
        if(correct){
            points++;
            //display a temporary message that fades away
            //Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            toast("correct");
        } else {
            points--;
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();

        }

        //display new score
        TextView tv = (TextView) findViewById(R.id.points_field);
        tv.setText("points: " + points);

        //randomize numbers again
        pickRandNums();
    }

    public void leftButtonClick(View view) {
        updateScore(true);
    }

    public void rightButtonClick(View view) {
        updateScore(false);
    }
}
