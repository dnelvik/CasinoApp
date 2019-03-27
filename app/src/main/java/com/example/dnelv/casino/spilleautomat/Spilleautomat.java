package com.example.dnelv.casino.spilleautomat;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dnelv.casino.MainActivity;
import com.example.dnelv.casino.R;
import com.example.dnelv.casino.SaldoRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Spilleautomat extends AppCompatActivity {
    private Button spinButton;
    private ImageView i1, i2, i3;
    public static TextView rewardText, balanceText;
    private static int balance, tempRes;
    public static SlotCollection sc;
    private RadioButton r10, r20, r50, r100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_spilleautomat);
        balance = MainActivity.getSaldo();
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

    //Setter igang maskinen og trekker fra innsats
    public void spin(View view) {
        if (r10.isChecked()) {
            updateSaldo(-10);
            sc.spin(10);
        } else if (r20.isChecked()) {
            updateSaldo(-20);
            sc.spin(20);
        } else if (r50.isChecked()) {
            updateSaldo(-50);
            sc.spin(50);
        } else if (r100.isChecked()) {
            updateSaldo(-100);
            sc.spin(100);
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateSaldo(sc.result());
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3100);
    }

    public void updateSaldo(int sum) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        int saldo = jsonResponse.getInt("saldo");
                        SharedPreferences.Editor editor = Spilleautomat.this.getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE).edit();
                        editor.putInt("Saldo", saldo);
                        editor.apply();
                        MainActivity.updateSaldo();
                        balanceText.setText("Balance: " + saldo);
                    } else {
                        String error = jsonResponse.getString("error");
                        Toast.makeText(Spilleautomat.this, error, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SharedPreferences prefs = MainActivity.getPrefs();
        String userId = prefs.getInt("UserID", 0) + "";
        SaldoRequest saldoRequest = new SaldoRequest(userId, sum + "", responseListener, null);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(saldoRequest);
    }
}
