package com.example.ethan.dictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import stanford.androidlib.SimpleActivity;

public class DictionaryActivity extends SimpleActivity {

    //dictionary for words
    private Map<String, String> dictionary;
    private List<String> words;

    //media player for sound
    private MediaPlayer mp;

    private int points;
    private int highScore;

    //pick word, pick ~5 rand defns for the word(1 is correct), display on screen
    private void chooseWords(){
        Random rand = new Random();
        int randIndex = rand.nextInt(words.size());
        //get the word and correct defn
        String theWord = words.get(randIndex);
        String theDefn = dictionary.get(theWord);

        //pick 4 other wrong defns at random and shuffle correct defn in
        List<String> defns = new ArrayList<>(dictionary.values());
        defns.remove(theDefn);
        Collections.shuffle(defns);
        defns = defns.subList(0,3);
        defns.add(theDefn);
        Collections.shuffle(defns);

        //set adapter for list of defns
        ((TextView)findViewById(R.id.the_word)).setText(theWord);
        ListView list = findViewById(R.id.word_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>(defns));
        list.setAdapter(adapter);

    }

    //create scanners for each text file
    private void readFileData(){
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.dictionaryapptest));
        readFileHelper(scan);
        try {
            Scanner scan2 = new Scanner(openFileInput("added_words.txt"));
            readFileHelper(scan2);
        } catch(Exception e){
            //do nothing
        }
    }

    //set the dictionary of words and defns
    private void readFileHelper(Scanner scan){
        while(scan.hasNextLine()){
            //scan each line, split words and dfns at the tab and enter into a dictionary
            String line = scan.nextLine();
            String[] parts = line.split("\t");
            if(parts.length < 2) continue;
            dictionary.put(parts[0], parts[1]);
            words.add(parts[0]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        TextView defnDisplay = findViewById(R.id.def_display);

        points = 0;
        highScore = 0;

        dictionary = new HashMap<>();
        words = new ArrayList<>();
        //set up scanner with text file as input
        readFileData();

        chooseWords();

        //ListView and adapter to add words to list
        ListView list = (ListView) findViewById(R.id.word_list);

        //anonymous function(lambda) when user taps on a word
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get defn, word and correct defn
                //check user's choice
                String defn = parent.getItemAtPosition(position).toString();
                String theWord = ((TextView)findById(R.id.the_word)).getText().toString();
                String correctDefn = dictionary.get(theWord);
                if(defn.equals(correctDefn)){
                    points++;
                    if(points > highScore){
                       highScore = points;

                        SharedPreferences prefs = getSharedPreferences("myprefs", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor = prefs.edit();
                        prefsEditor.putInt("highScore", highScore);
                        prefsEditor.apply();
                    }
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    points--;
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
                defnDisplay.setText("Points:" + points +", High: " + highScore);
                chooseWords();
            }
        });
        SharedPreferences prefs = getSharedPreferences("myprefs", MODE_PRIVATE);
        highScore = prefs.getInt("highScore",/*default*/ 0);

        mp = MediaPlayer.create(this, R.raw.kahoot);
        mp.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        //play music
        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }

    public void addWordClick(View view) {
        //go to add word activity
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the points and current word when exiting state
        outState.putInt("points", points);
        outState.putString("the_word", ((TextView)findViewById(R.id.the_word)).getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //bring points back when resuming state
        points = savedInstanceState.getInt("points", /*default*/ 0);
        ((TextView)findViewById(R.id.the_word)).setText(savedInstanceState.getString("the_word", ""));
    }
}
