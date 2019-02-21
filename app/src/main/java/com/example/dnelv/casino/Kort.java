package com.example.dnelv.casino;

public class Kort {

    String farge;
    String tall;
    String navn;

    public Kort(String farge, String tall){
        this.farge = farge;
        this.tall = tall;
        this.navn = farge + "_" + tall;
    }

    public String getNavn(){
        return this.navn;
    }
}
