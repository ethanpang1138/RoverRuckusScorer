package com.ethanp1138.roverruckusscorerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }
    // "Auton Scoring" button goes to AutonScoringActivity
    public void onAutonScoringClick(View view) {
        Intent intent = new Intent(this, AutonScoringActivity.class);
        startActivity(intent);
    }
    // "TeleOp Scoring" button goes to TeleOpScoringActivity
    public void onTeleOpScoringClick(View view) {
        Intent intent = new Intent(this, TeleOpScoringActivity.class);
        startActivity(intent);
    }
}
