package com.example.puz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.ArrayList;

public class TextRiddle extends AppCompatActivity {

    private ArrayList<String> answers;
    private Challenge challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_riddle);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        answers = intent.getStringArrayListExtra(MainActivity.EXTRA_MESSAGE2);
        challenge = (Challenge) intent.getSerializableExtra("challenge");

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    /** Called when the user taps Hint 1 button*/
    public void checkAnswer(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        EditText answer = (EditText) findViewById(R.id.editText);

        if(answers.contains(answer.getText().toString())){
            Toast toast = Toast.makeText(TextRiddle.this, "GOOD ANSWER", Toast.LENGTH_SHORT);
            toast.show();
            final TextRiddle that = this;
            API api = API.getInstance();
            api.completeChallenge(this.challenge, new Response.Listener<Integer>() {
                @Override
                public void onResponse(Integer score) {
                    Log.d("tag", "GOT THE RESPONSE :-)");
                    Log.d("tag", "My score is " + score);
                    // LOAD TEH MARKERS
                    that.finish();
                }
            });
        }
        else if(answer.getText().toString().length()==0){
            Toast toast = Toast.makeText(TextRiddle.this, "TYPE AN ANSWER", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(TextRiddle.this, "BAD ANSWER", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
