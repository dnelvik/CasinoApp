package com.example.dnelv.casino;

import android.os.Looper;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

public class SlotCollection {
    private Slot[] slots;
    private int stake, profit;

    public SlotCollection(ImageView i1, ImageView i2, ImageView i3){
        slots = new Slot[]{new Slot(10, i1, 1), new Slot(20, i2, 2), new Slot(30, i3, 3)};
    }

    public void spin(int stake) {
        this.stake = stake;
        for (Slot slot: slots) {
            slot.start();
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                result();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,3100);
    }

    private void result() {
        profit = 0;
        if(slots[0].symbol == slots[1].symbol && slots[1].symbol == slots[2].symbol){
            profit = threeOfAKind(slots[0].symbol);
        } else if(slots[0].symbol == slots[1].symbol || slots[1].symbol == slots[2].symbol){
            profit = toOfAKind(slots[1].symbol);
        } else if(slots[2].symbol == 7){
            profit = stake*2;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Spilleautomat.showResult(profit);
            }
        });
    }
    private int threeOfAKind(int symbol){
        int x = 0;
        switch(symbol){
            case 1: x = stake*3; break;
            case 2: x = stake*6; break;
            case 3: x = stake*12; break;
            case 4: x = stake*24; break;
            case 5: x = stake*48; break;
            case 6: x = stake*96; break;
            case 7: x = stake*192; break;
        }
        return x;
    }
    private int toOfAKind(int symbol){
        int x = 0;
        switch(symbol){
            case 1: x = stake*2; break;
            case 2: x = stake*3; break;
            case 3: x = stake*6; break;
            case 4: x = stake*12; break;
            case 5: x = stake*24; break;
            case 6: x = stake*48; break;
            case 7: x = stake*96; break;
        }
        return x;
    }

}
