package com.example.dnelv.casino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Pop extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        TextView showText = findViewById(R.id.HighscoreText);
        TextView gameText = findViewById(R.id.gameText);
        TextView userText = findViewById(R.id.userText);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.6), (int) (height*.3));

        String user = getIntent().getStringExtra("User");
        String game = getIntent().getStringExtra("Game");
        int score = getIntent().getIntExtra("Score", 0);

        userText.setText("Username: " + user);
        showText.setText("Score: " + score);
        gameText.setText(game);

    }
}
