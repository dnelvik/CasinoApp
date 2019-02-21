package com.example.dnelv.casino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void blackJack(View view) {
        Intent intent = new Intent(getBaseContext(), Blackjack.class);
        this.startActivity(intent);
    }

    public void spilleAutomat(View view) {
        Intent intent = new Intent(getBaseContext(), Spilleautomat.class);
        this.startActivity(intent);
    }
}
