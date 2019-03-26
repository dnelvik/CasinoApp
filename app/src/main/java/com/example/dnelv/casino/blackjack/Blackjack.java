package com.example.dnelv.casino.blackjack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dnelv.casino.MainActivity;
import com.example.dnelv.casino.R;

import java.util.ArrayList;

public class Blackjack extends AppCompatActivity {

    // Høyde/bredde i dp for kort
    private int cardWidth;
    private int cardHeight;

    // Referanser til GUI objekter
    private LinearLayout spillerKort, dealerKort;
    private TextView saldo, log, spillerInfo, dealerInfo;
    private ImageButton hit, stand, start, bet5, bet25, bet100, doubledown;

    //Deklarerer kortstokken og spillere
    private Kortstokk kortstokk;
    private Spiller spiller, dealer;

    ArrayList<ImageView> bilder; // Liste med imageviews

    // Spilleregel variabler
    boolean slutt, isBetting;
    int bet, penger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_blackjack);

        //Høyde/bredde på kort
        cardWidth = (int) getResources().getDimension(R.dimen.cardWidth);
        cardHeight = (int) getResources().getDimension(R.dimen.cardHeight);

        ///////////////////// Oppretter alle GUI elementene
        // Imagebuttons
        hit = (ImageButton) findViewById(R.id.hit);
        stand = (ImageButton) findViewById(R.id.stand);
        start = (ImageButton) findViewById(R.id.start);
        bet5 = (ImageButton) findViewById(R.id.bet5);
        bet25 = (ImageButton) findViewById(R.id.bet25);
        bet100 = (ImageButton) findViewById(R.id.bet100);
        doubledown = (ImageButton) findViewById(R.id.doubledown);

        // Layout og textviews
        spillerInfo = (TextView) findViewById(R.id.spillerInfo);
        dealerInfo = (TextView) findViewById(R.id.dealerInfo);
        spillerKort = (LinearLayout) findViewById(R.id.kort2);
        dealerKort = (LinearLayout) findViewById(R.id.kort1);
        saldo = (TextView) findViewById(R.id.saldo);
        log = (TextView) findViewById(R.id.log);
        log.setMovementMethod(new ScrollingMovementMethod());

        // Arraylist for bilder som skal legges inn grafisk
        bilder = new ArrayList<ImageView>();    // Arraylist med kortene
        bilder.add(new ImageView(this));
        bilder.get(0).setImageResource(R.drawable.back);

        startSpill();

    }

    private void startSpill(){
        //Resetter
        removeViews();
        dealerInfo.setText("Poeng: ");

        //Instansierer spillere og kortstokk
        kortstokk = new Kortstokk();
        spiller = new Spiller();
        dealer = new Spiller();

        // Setter regelvariabler
        penger = MainActivity.getSaldo();
        slutt = true;
        bet = 0;
        isBetting = true;
        saldo.setText("Saldo: " + penger + " kr");

        //Dealer ut kort
        deal(dealer, true);
        deal(dealer, true);
        deal(spiller, false);
        deal(spiller, false);

        spillerInfo.setText("Poeng: ");
        updateButtons();
    }

    private void deal(Spiller spilleren, boolean dealer){
        spilleren.hånd.add(kortstokk.deal()); // Deler ut et kort
        if (!isBetting)
            cardGraphics(spilleren.hånd.get(spilleren.hånd.size() - 1).getNavn(), dealer);
        else
            cardGraphics("back", dealer);
    }

    // Legger til kort i grafikken
    private void cardGraphics(String kortNavn, boolean dealer){
        bilder.add(new ImageView(this)); //legger et imageview til i en arraylist
        int siste = bilder.size() - 1;  // Lagrer verdi for siste plass i listen
        int resId = this.getResources().getIdentifier(kortNavn, "drawable", this.getPackageName()); //ID for bildet
        bilder.get(siste).setImageResource(resId);  // Setter bildet til imageview

        if (dealer)
            dealerKort.addView(bilder.get(siste), cardWidth, cardHeight);
        else
            spillerKort.addView(bilder.get(siste), cardWidth, cardHeight);
        spillerInfo.setText("Poeng: " + spiller.sjekkPoeng());
    }

    // Kjøres når hitknappen trykkes
    public void hit(View view) {
        if (!slutt) {
            deal(spiller, false);
            if (spiller.sjekkPoeng() > 21)
                end(1, false);
            updateButtons();
        }

    }

    // Kjøres når stand knappen trykkes
    public void stand(View view){
        if (!slutt){
            dealeren(false);
            slutt=true;
            if (dealer.sjekkPoeng() == spiller.sjekkPoeng())
                end(2, false);
            else if (dealer.sjekkPoeng() > 21 || dealer.sjekkPoeng() < spiller.sjekkPoeng())
                end(0, false);
            else
                end(1, false);
        }
    }

    // Kjøres når startknappen trykkes
    public void flip(View view) {
        if (isBetting == false) return;
        if (bet == 0){
            log.append("\n You must place a bet");
            return;
        }
        slutt = false;  // Spillet er i gang
        isBetting = false;  // Ikke lenger i bettingfasen
        log.append("\n You started the game with " + bet + " kr in the pot.");

        // Snur kortene
        removeViews();
        for (int i=0; i<2; i++){
            if (i == 0)
                cardGraphics("back", true);
            else
                cardGraphics(dealer.hånd.get(i).getNavn(), true);
            cardGraphics(spiller.hånd.get(i).getNavn(), false);
        }

        // Sjekker etter blackjack
        if (spiller.sjekkPoeng() == 21 && dealer.sjekkPoeng() != 21) {
            end(0, true);
            dealeren(true);
        }
        else if (spiller.sjekkPoeng() == 21 && dealer.sjekkPoeng() == 21) {
            end(2, false);
            dealeren(true);
        }
        updateButtons();
    }

    // Kjøres ved spill slutt
    private void end(int vinn, boolean blackjack){
        slutt = true;
        if (vinn == 0){
            if (!blackjack){    // Vinn uten blackjack
                penger += bet*2;
                log.append("\n You won!");
            }
            else {              // Vinn med blackjack
                log.append("\n BLACKJACK!");
                bet = (bet*2) + (bet/2);
                penger += bet;
            }
        } else if (vinn == 1)
            log.append("\n You lost!");
        else {
            penger += bet;
            log.append("\n It's a tie!");
        }
        saldo.setText("Saldo: " + penger + " kr");
        dealerInfo.setText("Poeng: " + dealer.sjekkPoeng());
        updateButtons();
        MainActivity.setSaldo(penger);
    }

    //Kjøres når en av bet knappene trykkes
    public void bet(View view) {
            int innsats = Integer.parseInt(view.getTag().toString());
            if (!isBetting) return;
            if (penger < innsats){
                // Oppdatere tekst med for lite saldo
                return;
            }
            bet += innsats;
            penger -= innsats;
            log.append("\n You bet " + innsats + " kr");
            saldo.setText("Saldo: " + penger + " kr");
    }

    // Kjøres ved doubledown knappen
    public void doubleDown(View view){
        if (slutt) return;
        if (bet > penger) return;
        if (spiller.hånd.size() != 2) return;
        penger -= bet;
        bet = bet*2;
        hit.performClick();
        stand.performClick();
    }

    // Dealeren sin runde
    private void dealeren(boolean blackjack){
        // Snur dealeren sine kort
        dealerKort.removeAllViews();
        for (int i=0; i<dealer.hånd.size(); i++)
            cardGraphics(dealer.hånd.get(i).getNavn(), true);

        // Dealeren spiller nye kort
        if (!blackjack) {
            while (dealer.sjekkPoeng() < 17) {
                deal(dealer, true);
            }
        }
    }

    // Kjøres ved new game knappen
    public void newGame(View view) {
        startSpill();
    }

    // Resetter views og kort
    private void removeViews(){
        bilder.clear();
        spillerKort.removeAllViews();
        dealerKort.removeAllViews();
    }


    // Oppdaterer transpancy på knappene
    private void updateButtons(){
        if (!slutt){
            start.getBackground().setAlpha(100);
            bet5.getBackground().setAlpha(100);
            bet25.getBackground().setAlpha(100);
            bet100.getBackground().setAlpha(100);
            hit.getBackground().setAlpha(255);
            stand.getBackground().setAlpha(255);
            if (spiller.hånd.size() == 2)
                doubledown.getBackground().setAlpha(255);
            else
                doubledown.getBackground().setAlpha(100);
        }
        else if (slutt && isBetting){
            start.getBackground().setAlpha(255);
            bet5.getBackground().setAlpha(255);
            bet25.getBackground().setAlpha(255);
            bet100.getBackground().setAlpha(255);
            hit.getBackground().setAlpha(100);
            stand.getBackground().setAlpha(100);
            doubledown.getBackground().setAlpha(100);
        }
        else {
            start.getBackground().setAlpha(100);
            bet5.getBackground().setAlpha(100);
            bet25.getBackground().setAlpha(100);
            bet100.getBackground().setAlpha(100);
            hit.getBackground().setAlpha(100);
            stand.getBackground().setAlpha(100);
            doubledown.getBackground().setAlpha(100);
        }
    }
}
