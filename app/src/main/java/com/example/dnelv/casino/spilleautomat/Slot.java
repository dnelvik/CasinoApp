package com.example.dnelv.casino.spilleautomat;

import android.util.Log;
import android.widget.ImageView;

import com.example.dnelv.casino.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Slot {
    private final int FREQUENZY = 100;
    private int counter, id;
    public int maxSpin, symbol;
    public ImageView img;
    private Timer timer;
    private static boolean running = false;
    private TimerTask task;


    public Slot(int maxSpin, ImageView img, int id) {
        this.maxSpin = maxSpin;
        this.img = img;
        this.symbol = 1;
        this.id = id;
    }

    public void start() {
        if (running)
            return;
        if (id == 3)
            running = true;
        counter = 0;
        task = new TimerTask() {
            @Override
            public void run() {
                changeImg();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, FREQUENZY);
    }

    private void changeImg() {
        if (counter >= maxSpin) {
            timer.cancel();
            if (id == 3) {
                running = false;

            }
            return;
        }
        switch (symbol = randomSymbol(symbol)) {
            case 1:
                img.setImageResource(R.drawable.firklover);
                break;
            case 2:
                img.setImageResource(R.drawable.hestesko);
                break;
            case 3:
                img.setImageResource(R.drawable.chip);
                break;
            case 4:
                img.setImageResource(R.drawable.ess);
                break;
            case 5:
                img.setImageResource(R.drawable.mynter);
                break;
            case 6:
                img.setImageResource(R.drawable.pengesekk);
                break;
            case 7:
                img.setImageResource(R.drawable.diamant);
                break;
        }
        counter++;
    }

    private int randomSymbol(int lastSymbol) {
        Random rand = new Random();
        int n = rand.nextInt(100);
        int newSymbol;

        if (n < 30) {
            newSymbol = 1;
        } else if (n < 51) {
            newSymbol = 2;
        } else if (n < 67) {
            newSymbol = 3;
        } else if (n < 80) {
            newSymbol = 4;
        } else if (n < 90) {
            newSymbol = 5;
        } else if (n < 96) {
            newSymbol = 6;
        } else {
            newSymbol = 7;
        }
        if (newSymbol == lastSymbol)
            newSymbol = randomSymbol(newSymbol);
        return newSymbol;
    }
}
