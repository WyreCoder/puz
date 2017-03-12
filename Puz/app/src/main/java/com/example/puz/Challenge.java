package com.example.puz;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

class Challenge implements Serializable {

    public String question;
    public ArrayList<String> list;
    public MapPosition position;

    public boolean complete = false;

    public Challenge (String question, ArrayList<String> answers) {
        this.question = question;
        list = answers;
    }

    public String getQuestion () {
        return question;
    }

    public void setPosition (MapPosition position) {
        this.position = position;
    }
    public MapPosition getPosition () {
        return this.position;
    }

    public ArrayList<String> getAnswers () {
        return list;
    }

    public boolean isCorrect (String answer) {
        return list.contains(answer.toLowerCase());
    }

    public void setIsComplete (boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete () {
        return this.complete;
    }

    public Intent getIntent (Activity activity) {

        Intent intent = new Intent(activity, TextRiddle.class);
        intent.putExtra("message", this.getQuestion());
        intent.putStringArrayListExtra("message2", this.getAnswers());
        intent.putExtra("challenge", (Serializable) this);

        return intent;

    }


}
