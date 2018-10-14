package com.example.ethan.turtleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import stanford.androidlib.SimpleActivity;

public class TurtleActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turtle);
    }

    public void radioClick(View view) {
        int id = view.getId();
        ImageView img = (ImageView) findViewById(R.id.turtle_image);
        /*
        if(id == R.id.leo_button){
            img.setImageResource(R.drawable.leo);
        } else if(id == R.id.don_button){
            img.setImageResource(R.drawable.don);
        } else if(id == R.id.raph_button){
            img.setImageResource(R.drawable.rafael);
        } else if(id == R.id.mike_button){
            img.setImageResource(R.drawable.mike);
        }
        */
        switch(id) {
            case R.id.leo_button: img.setImageResource(R.drawable.leo);
                break;
            case R.id.don_button: img.setImageResource(R.drawable.don);
                break;
            case R.id.raph_button: img.setImageResource(R.drawable.rafael);
                break;
            case R.id.mike_button: img.setImageResource(R.drawable.mike);
                break;
        }
    }
}
