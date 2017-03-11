package com.example.puz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_MESSAGE2 = "message2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /** Called when the user taps Hint 1 button*/
    public void showRiddle(View view) {
        Intent intent = new Intent(this, Riddle.class);
        startActivity(intent);

    }

    public void showTextRiddle(View view) {
        Intent intent = new Intent(this, TextRiddle.class);
        String message = "Get the question form the server here";
        ArrayList<String>  answers = new ArrayList();
        answers.add("choc");
        answers.add("chocolate");
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putStringArrayListExtra(EXTRA_MESSAGE2, answers);
        startActivity(intent);

    }

}
