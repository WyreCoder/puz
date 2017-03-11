package com.example.puz;

import java.util.LinkedList;
import java.util.List;

class Challenge {

    public List<String> list;

    public Challenge () {
        list = new LinkedList<String>();
        list.add("answer");
        list.add("answer2");
        list.add("answer3");
    }

    public String getQuestion () {
        return "question!";
    }

    public List<String> getAnswers () {
        return list;
    }

    public boolean isCorrect (String answer) {
        return list.contains(answer.toLowerCase());
    }


}
