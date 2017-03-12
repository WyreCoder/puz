package com.example.puz;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Riddle extends AppCompatActivity {

    private ArrayList<String> answers;
    private Bitmap bmp;
    private Challenge challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riddle);

        Intent intent = getIntent();
        String message = intent.getStringExtra("question");
        answers = intent.getStringArrayListExtra("answers");
        final String url = intent.getStringExtra("image_url");
        challenge = (Challenge) intent.getSerializableExtra("challenge");

        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);

        textView.post(new Runnable() {
            @Override
            public void run() {
                int height = textView.getLineCount() * textView.getLineHeight() + 10;
                ViewGroup.LayoutParams layout = textView.getLayoutParams();
                layout.height = (int) height;
                textView.setLayoutParams(layout);
            }
        });

        final ImageView bgImg = (ImageView) findViewById(R.id.bgImg);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL(url).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    // log error
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null)
                    bgImg.setImageBitmap(bmp);
            }

        }.execute();
    }


    /** Called when the user taps Hint 1 button*/
    public void checkAnswer(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText answer = (EditText) findViewById(R.id.editText);

        if(answers.contains(answer.getText().toString())){
            Toast toast = Toast.makeText(Riddle.this, "GOOD ANSWER", Toast.LENGTH_SHORT);
            toast.show();
            final Riddle that = this;
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
            Toast toast = Toast.makeText(Riddle.this, "TYPE AN ANSWER", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(Riddle.this, "BAD ANSWER", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
