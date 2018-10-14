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

    public void autonScoringClick(View view) {
        Intent intent = new Intent(this, AutonScoringActivity.class);
        startActivity(intent);
    }
}
