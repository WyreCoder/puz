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

        // Load the leaders from server:
        String leader1 = null;
        String leader2 = null;
        String leader3 = null;
        String leader4 = null;
        String leader5 = null;

        // Change interface text:
        leader1Txt.setText(leader1);
        leader1Txt.setText(leader2);
        leader1Txt.setText(leader3);
        leader1Txt.setText(leader4);
        leader1Txt.setText(leader5);

    }
}
