package com.example.puz;

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

import java.io.InputStream;
import java.net.URL;

import com.example.puz.R;

public class FindTheObject extends AppCompatActivity {

    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_challenge);

        // Obtain server data:
        String questionText = "Find the coffee cup";
        // Obtain URL form server:
        final String url = "";

        // Obtain object's dims and pos:
        int objW = 23;
        int objH = 16;
        int objPosX = 283;
        int objPosY = 304;

        // Object to find:
        final Button objectBtn = (Button) findViewById(R.id.objectBtn);
        objectBtn.setOnClickListener(itemFound());

        // Give up button:
        final Button giveUpBtn = (Button) findViewById(R.id.giveUpBtn);
        giveUpBtn.setOnClickListener(giveUp());

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

    @Nullable
    private View.OnClickListener giveUp() {
        System.out.print("give up clicked\n");
        return null;
    }

    @Nullable
    private View.OnClickListener itemFound() {
        System.out.print("item found\n");
        return null;
    }

}

