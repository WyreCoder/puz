package com.example.puz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by wyre on 12/03/17.
 */

public class LeaderBoard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_leader_board);

        // Load xml components:
        final TextView leader1Txt = (TextView) findViewById(R.id.leader1Txt);
        final TextView leader2Txt = (TextView) findViewById(R.id.leader2Txt);
        final TextView leader3Txt = (TextView) findViewById(R.id.leader3Txt);
        final TextView leader4Txt = (TextView) findViewById(R.id.leader4Txt);
        final TextView leader5Txt = (TextView) findViewById(R.id.leader5Txt);
        final TextView [] txtViews = new TextView [] {leader1Txt, leader2Txt, leader3Txt, leader4Txt, leader5Txt};

        // Load the leaders from server:
        API.getInstance().loadLeaders(new Response.Listener<LeaderboardData>(){
            @Override
            public void onResponse(LeaderboardData response) {
                List<LeaderboardData.Entry> scores = response.getScores();
                for (int view = 0; view < 5; view++) {
                    if (scores.top()) {
                        txtViews[view].setText(scores.pop().name);
                    }
                }
            }
        });
    }
}
