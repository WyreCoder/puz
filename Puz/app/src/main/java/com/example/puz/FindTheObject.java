package com.example.puz;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.android.volley.Response;
import com.example.puz.R;
import com.google.android.gms.maps.model.LatLng;

public class FindTheObject extends AppCompatActivity {

    private Bitmap bmp;
    private Challenge challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        challenge = (Challenge) intent.getSerializableExtra("challenge");

        switch (intent.getIntExtra("challenge_id", 1)) {
            /* case 5:
                setContentView(R.layout.activity_click_test_1);
                break;
            case 4:
                setContentView(R.layout.activity_click_test_2);
                break;
            case 6:
                setContentView(R.layout.activity_click_test_3);
                break; */
            default:
            case 3:
                setContentView(R.layout.activity_click_challenge);
                break;
        }

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

        Resources r = getResources();
        float pxW = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objW, r.getDisplayMetrics());
        float pxH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objH, r.getDisplayMetrics());

        Log.d("tag", "w " + pxW + "," + objW);
        Log.d("tag", "h " + pxH + "," + objH);

        ViewGroup.LayoutParams params = objectBtn.getLayoutParams();
        params.width = (int) pxW;
        params.height = (int) pxH;
        objectBtn.setLayoutParams(params);
        objectBtn.setX(objPosX - 283);
        objectBtn.setY(objPosY - 304);

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
        returnToMap(true);
    }

    public void giveUp(View view) {
        // Send response to server-  negative
        returnToMap(false);
    }

    private void returnToMap(boolean success) {
        Intent intent = new Intent(this, MainActivity.class);
        Toast toast = Toast.makeText(FindTheObject.this, success ? "You found it!" : "Too bad.", Toast.LENGTH_SHORT);
        toast.show();

        Log.d("tag", "Done? " + (success ? "1":"0"));
        final FindTheObject that = this;
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
}

