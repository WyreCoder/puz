package com.example.puz;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard<E> {

    public class Entry {
        public int id;
        public String name;
        public int score;
        public Entry (int id, String name, int score) {
            this.id = id;
            this.name = name;
            this.score = score;
        }
    }

    List<Entry> list = new ArrayList<Entry>();

    public void add(int id, String name, int score) {
        list.add(new Entry(id, name, score));
    }

    public List<Entry> getScores() {
        return this.list;
    }

}
