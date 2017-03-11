package com.example.puz;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

class Challenge implements Serializable {

    public ArrayList<String> list;

    public Challenge () {
        list = new ArrayList<String>();
        list.add("answer");
        list.add("answer2");
        list.add("answer3");
    }

    public String getQuestion () {
        return "question!";
    }

    public ArrayList<String> getAnswers () {
        return list;
    }

    public boolean isCorrect (String answer) {
        return list.contains(answer.toLowerCase());
    }

    public Intent getIntent (Activity activity) {

        Intent intent = new Intent(activity, TextRiddle.class);
        intent.putExtra("message", this.getQuestion());
        intent.putStringArrayListExtra("message2", this.getAnswers());
        intent.putExtra("challenge", (Serializable) this);

        return intent;

    }


}
