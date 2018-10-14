package com.example.ethan.dictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartMenuActivity extends AppCompatActivity {
    private static final int REQ_CODE_ADD_WORD = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }

    public void playTheGameClick(View view) {
        //go to DictionaryActivity
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);
    }

    public void addANewWordClick(View view) {
        //go to AddWordActivity
        Intent intent = new Intent(this, AddWordActivity.class);
        intent.putExtra("initialtext", "");
        intent.putExtra("initaldefn", "");
        startActivityForResult(intent, REQ_CODE_ADD_WORD);

    }

    //gets executed when the called activity returns here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == REQ_CODE_ADD_WORD) {
            String newWord = intent.getStringExtra("newword");
            String newdefn = intent.getStringExtra("newdefn");

            Toast.makeText(getApplicationContext(), "New word added: " + newWord, Toast.LENGTH_SHORT).show();
        }
    }
}
