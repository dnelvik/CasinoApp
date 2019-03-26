package com.example.dnelv.casino.blackjack;

import java.util.ArrayList;

public class Spiller {

    ArrayList<Kort> hånd;

    public Spiller(){
        hånd = new ArrayList<Kort>();
    }

    public int sjekkPoeng(){
        int poeng = 0;
        for (Kort k : hånd){
            String verdi = k.navn.split("_")[1];
            if (verdi.equals("knekt") || verdi.equals("dame") || verdi.equals("konge"))
                poeng += 10;
            else
                poeng += Integer.parseInt(verdi);
        }
        for (Kort k : hånd) {
            if (k.navn.split("_")[1].equals("1")){
                if (poeng < 12)
                    poeng += 10;
            }
        }
        return poeng;
    }
}
