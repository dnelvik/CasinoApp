package com.example.dnelv.casino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class Spilleautomat extends AppCompatActivity {
    private Button spinButton;
    private ImageView i1, i2, i3;
    public static TextView rewardText,balanceText;
    private static int balance;
    public static SlotCollection sc;
    private RadioButton r10, r20, r50, r100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spilleautomat);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//hide the title
        getSupportActionBar().hide(); //hide the title bar
        balance = 10000;
        balanceText = findViewById(R.id.saldo);
        balanceText.setText("Balance: " + balance);

        spinButton = findViewById(R.id.spin);

        i1 = findViewById(R.id.slot1);
        i2 = findViewById(R.id.slot2);
        i3 = findViewById(R.id.slot3);
        sc = new SlotCollection(i1, i2, i3);

        rewardText = findViewById(R.id.reward);

        r10 = findViewById(R.id.bet10);
        r20 = findViewById(R.id.bet20);
        r50 = findViewById(R.id.bet50);
        r100 = findViewById(R.id.bet100);

    }

    public void spin(View view) {
        if(r10.isChecked()){
            balance-=10;
            sc.spin(10);
        } else if(r20.isChecked()){
            balance-=20;
            sc.spin(20);
        } else if(r50.isChecked()){
            balance-=50;
            sc.spin(50);
        } else if(r100.isChecked()){
            balance -=100;
            sc.spin(100);
        }
        balanceText.setText("Balance: " + balance);
    }
    public static void showResult(int reward){
        rewardText.setText(""+reward);
        balance+=reward;
        balanceText.setText("Balance: "+balance);
    }
}
