package com.example.ethan.dictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.PrintStream;


public class AddWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        //create intent with word and definition to send back to StartMenuActivity
        Intent intent = getIntent();
        String word = intent.getStringExtra("initialtext");
        String defn = intent.getStringExtra("initaldefn");
        ((EditText)(findViewById(R.id.new_word))).setText(word);
        ((EditText)(findViewById(R.id.new_defn))).setText(defn);
    }

    public void addThisWordClick(View view) throws FileNotFoundException {
        //add given word/defn to dictionary
        EditText wordText = (((EditText)(findViewById(R.id.new_word))));
        EditText defnText = (((EditText)(findViewById(R.id.new_defn))));
        String newWord = wordText.getText().toString();
        String newDefn = defnText.getText().toString();

        PrintStream output = new PrintStream(openFileOutput("added_words.txt", MODE_PRIVATE | MODE_APPEND));
        output.println(newWord + "\t" + newDefn);
        output.close();

        //go back to StartMenuActivity
        //return new word/defn to StartMenuAcitivity
        Intent goBack = new Intent();
        goBack.putExtra("newword", newWord);
        goBack.putExtra("newdefn", newDefn);
        setResult(RESULT_OK, goBack);
        finish();
    }
}
