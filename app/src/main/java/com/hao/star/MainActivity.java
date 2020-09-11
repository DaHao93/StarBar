package com.hao.star;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hao.starbar.StarBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StarBarView starBar = findViewById(R.id.starBar);
        starBar.setOnScoreChangeListener(new StarBarView.OnScoreChangeListener() {
            @Override
            public void onScoreChange(int score) {

            }
        });
    }
}