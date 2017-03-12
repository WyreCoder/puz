package com.example.puz;


import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

class CoffeeChallenge extends Challenge {

    private String url;
    private int[] data;

    public CoffeeChallenge(String question, String url, int[] data) {
        super(question, null);
        this.url = url;
        this.data = data;
    }

    public Intent getIntent (Activity activity) {

        Intent intent = new Intent(activity, FindTheObject.class);
        intent.putExtra("question", this.getQuestion());
        intent.putExtra("image_url", this.getURL());
        intent.putExtra("object", this.data);
        intent.putExtra("challenge_id", Integer.parseInt(this.getPosition().getId()));
        intent.putExtra("challenge", (Serializable) this);

        return intent;

    }

    public String getURL() {
        return this.url;
    }

}
