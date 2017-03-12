package com.example.puz;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;


class Challenge2 extends Challenge {

    private String url;

    public Challenge2(String question, String imageURL, ArrayList<String> answers) {
        super(question, answers);
        this.url = imageURL;
    }


    public Intent getIntent (Activity activity) {

        Intent intent = new Intent(activity, Riddle.class);
        intent.putExtra("question", this.getQuestion());
        intent.putExtra("image_url", this.getURL());
        intent.putExtra("answers", this.getAnswers());
        intent.putExtra("challenge_id", Integer.parseInt(this.getPosition().getId()));
        intent.putExtra("challenge", (Serializable) this);

        return intent;

    }

    public String getURL() {
        return this.url;
    }
}
