package com.example.puz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import com.example.puz.R;

public class FindTheObject extends AppCompatActivity {

    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_challenge);

        Intent intent = getIntent();

        // Obtain server data:
        String questionText = intent.getStringExtra("question");
        // Obtain URL form server:
        final String url = intent.getStringExtra("image_url");

        int[] integer = intent.getIntArrayExtra("object");

        // Obtain object's dims and pos:
        int objW = integer[0];
        int objH = integer[1];
        int objPosX = integer[2];
        int objPosY = integer[3];

        // Object to find:
        final Button objectBtn = (Button) findViewById(R.id.objectBtn);

        // Give up button:
        final Button giveUpBtn = (Button) findViewById(R.id.giveUpBtn);

        // Question text field:
        final TextView questionTxt = (TextView) findViewById(R.id.questionTxt);
        questionTxt.setText(questionText);

        // Background image:
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

    public void itemFound(View view) {
        // Send response to server-  positive
        returnToMap("You found it!");
    }

    public void giveUp(View view) {
        // Send response to server-  negative
        returnToMap("Too bad.");
    }

    private void returnToMap(String msg) {
        Intent intent = new Intent(this, MainActivity.class);
        Toast toast = Toast.makeText(FindTheObject.this, msg, Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}

