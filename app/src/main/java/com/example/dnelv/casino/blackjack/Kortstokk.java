package com.example.dnelv.casino.blackjack;

import android.util.Log;

import java.util.ArrayList;

public class Kortstokk {

    ArrayList<Kort> kortstokk;
    String tall[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "knekt", "dame", "konge"};
    String farge[] = {"k", "s", "h", "r"};

    // Konstruktør for oppretting av kortstokk
    public Kortstokk() {
        this.kortstokk = new ArrayList(52);
        lagKortstokk();
        stokk();
    }

    // Oppretter en kortstokk
    public void lagKortstokk() {
        for (int f=0; f<farge.length; f++){
            for (int t=0; t<tall.length; t++){
                this.kortstokk.add(new Kort(farge[f], tall[t]));
            }
        }
    }

    // Print metode for testing
    public void printKortstokk(){
        for (Kort k : kortstokk) Log.d("Sjekk", k.getNavn());
    }

    // Stokker kortstokken
    private void stokk() {
        for (int i=0; i<kortstokk.size(); i++){
            double rand = Math.floor(Math.random() * kortstokk.size());
            Kort temp = kortstokk.get(i);
            kortstokk.set(i, kortstokk.get((int) rand));
            kortstokk.set((int) rand, temp);
        }
    }

    // Dealer ut et kort fra kortstokken
    public Kort deal() {
        return kortstokk.remove(0);
    }
}
