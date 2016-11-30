package com.example.tomxin.txes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(IndexActivity.this,LoginActivity.class);
                startActivity(intent);
                IndexActivity.this.finish();
            }
        };
        timer.schedule(timerTask, 1000 * 3);
    }
}
