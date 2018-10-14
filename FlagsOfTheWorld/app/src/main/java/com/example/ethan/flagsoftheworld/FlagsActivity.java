package com.example.ethan.flagsoftheworld;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FlagsActivity extends AppCompatActivity {

    //array of countries
    private static final String[] COUNTRIES = {"Japan", "Nepal", "United Kingdoms", "United States"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags);

        //add new buttons to the screen
        Button button = new Button(this);
        Button button2 = new Button(this);
        //get layout object
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_flags);
        //define button properties
        button.setText("My button");
        button2.setText("button2");

        //create a LayoutParam object to hold button layout properties
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,  //width
                ViewGroup.LayoutParams.WRAP_CONTENT); //height
        button.setLayoutParams(params);
        button2.setLayoutParams(params);

        //add buttons the index 1 in layout
        layout.addView(button, 1);
        //no index adds to end of views in layout
        layout.addView(button2);

        //create an image, label and checkbox for each country
        for(String name : COUNTRIES){
            addFlag(name, layout);
        }
    }

    private void addFlag(final String countryName, LinearLayout layout){
        //add flags to screen using the flag.xml
        View flag = getLayoutInflater().inflate(R.layout.flag, null); //don't put flags onscreen yet

        //get widget with id to change text
        TextView tv = (TextView) flag.findViewById(R.id.flag_text);
        tv.setText(countryName);

        //change countryName to lowercase with no spaces
        String countryName2 = countryName.replace(" ", "").toLowerCase();

        //get flagImageID with id "R.drawable.countryName"
        int flagImageID = getResources().getIdentifier(countryName2, "drawable", getPackageName());

        //get ImageButton and change it to the appropriate image using id
        ImageButton img = (ImageButton) flag.findViewById(R.id.flag_image);
        img.setImageResource(flagImageID);

        //respond to clicks on flag
        img.setOnClickListener(new View.OnClickListener(){ //anonymous class
            @Override
            public void onClick(View v){
                //set up an "alert" pop-up screen and initialize text
                AlertDialog.Builder builder = new AlertDialog.Builder(FlagsActivity.this);
                builder.setTitle("yay!");
                builder.setMessage("You clicked " + countryName);
                //set up the OK button with an anonymous listener class
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(FlagsActivity.this, "you clicked OK", Toast.LENGTH_SHORT).show();
                            }
                        });
                //create dialog box and show
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        //put flags onscreen now
        layout.addView(flag);
    }
}
