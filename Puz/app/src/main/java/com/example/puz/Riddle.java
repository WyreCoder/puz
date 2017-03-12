package com.example.puz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Riddle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riddle);
    }


    /** Called when the user taps Hint 1 button*/
    public void checkAnswer(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText answer = (EditText) findViewById(R.id.editText);
        if(answer.getText().toString().equals("choc")){
            Toast toast = Toast.makeText(Riddle.this, "GOOD ANSWER", Toast.LENGTH_SHORT);
            toast.show();
            startActivity(intent);
        }
        else if(answer.getText().toString().length()==0){
            Toast toast = Toast.makeText(Riddle.this, "TYPE AN ANSWER", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(Riddle.this, "BAD ANSWER", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
