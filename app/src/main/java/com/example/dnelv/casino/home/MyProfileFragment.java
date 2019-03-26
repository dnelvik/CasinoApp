package com.example.dnelv.casino.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dnelv.casino.MainActivity;
import com.example.dnelv.casino.R;

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

    private void addCash() {
        int add = Integer.parseInt(adding.getText().toString());
        int before = MainActivity.getSaldo();
        int after = before + add;
        MainActivity.updateSaldo(after);
        thanks.setText("Takk for ditt innskudd på: "+add);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                addCash();
                break;
        }
    }
}
