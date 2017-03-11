package com.example.puz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.puz.R;

public class FindTheObject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_challenge);

        // Obtain server data:
        String questionText = "Find the coffee cup";
        // Obtain URL form server:
        String imgURL = "http://www.amsterdam.info/photos/content/presentation/1383388835-amsterdam-coffeeshop-baba-inside.jpg";
        Bitmap img = null;
        try {
            InputStream in = new java.net.URL(imgURL).openStream();
            img = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Obtain dims and pos':
        int objW = 23;
        int objH = 16;
        int objPosX = 283;
        int objPosY = 304;
        //int imgW = 50;
        //int imgH = 50;
        int imgPosX = 0;
        int imgPosY = 16;

        // Object to find:
        final Button objectBtn = (Button) findViewById(R.id.objectBtn);
        objectBtn.setOnClickListener(itemFound());
        ConstraintLayout.LayoutParams objLP = new ConstraintLayout.LayoutParams(
                objW,
                objH);
        objLP.setMargins(objPosX, objPosY, 0, 0); // Set position
        objectBtn.setLayoutParams(objLP);

        // Give up button:
        final Button giveUpBtn = (Button) findViewById(R.id.giveUpBtn);
        ConstraintLayout.LayoutParams guLP = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        guLP.setMargins(148, 163, 0, 0); // Set position
        giveUpBtn.setLayoutParams(guLP);
        giveUpBtn.setOnClickListener(giveUp());

        // Question text field:
        final TextView questionTxt = (TextView) findViewById(R.id.questionTxt);
        ConstraintLayout.LayoutParams txtLP = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        guLP.setMargins(38, 405, 0, 0); // Set position
        questionTxt.setLayoutParams(txtLP);
        questionTxt.setText(questionText);

        // Background image:
        final ImageView bgImg = (ImageView) findViewById(R.id.bgImg);
        bgImg.setImageBitmap(img);
         ConstraintLayout.LayoutParams imgLP = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                 ConstraintLayout.LayoutParams.WRAP_CONTENT);
         imgLP.setMargins(imgPosX, imgPosY, 0, 0); // Set position
         bgImg.setLayoutParams(imgLP);
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

