package com.example.puz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class FindTheObjectTestData extends AppCompatActivity {

    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_challenge);
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
        Toast toast = Toast.makeText(FindTheObjectTestData.this, msg, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }
}

