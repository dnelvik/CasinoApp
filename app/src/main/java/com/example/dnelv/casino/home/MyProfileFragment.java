package com.example.dnelv.casino.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dnelv.casino.MainActivity;
import com.example.dnelv.casino.R;
import com.example.dnelv.casino.SaldoRequest;
import com.example.dnelv.casino.login.LoginActivity;
import com.example.dnelv.casino.login.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MyProfileFragment extends Fragment implements View.OnClickListener {
    private EditText adding;
    private TextView thanks;
    private Button addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);
        adding = v.findViewById(R.id.adding);
        thanks = v.findViewById(R.id.thanks);
        addButton = v.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        return v;
    }

    private void addCash(String sum) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        int sum = jsonResponse.getInt("sum");
                        int saldo = jsonResponse.getInt("saldo");
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE).edit();
                        editor.putInt("Saldo", saldo);
                        editor.apply();
                        Toast.makeText(getActivity(), "Takk for ditt inskudd på: " + sum+"kr", Toast.LENGTH_SHORT).show();
                    } else {
                        String error = jsonResponse.getString("error");
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String userId = prefs.getInt("UserID", 0) + "";
        SaldoRequest saldoRequest = new SaldoRequest(userId, sum, responseListener, null);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(saldoRequest);
        MainActivity.updateSaldo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                addCash(adding.getText().toString());
                break;
        }
    }
}
