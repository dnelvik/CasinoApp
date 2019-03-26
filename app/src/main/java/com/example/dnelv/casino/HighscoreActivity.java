package com.example.dnelv.casino;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class HighscoreActivity extends AppCompatActivity {

    ListView highscoreListe;
    String[] userTabell = {"Danay", "Åsmund", "Nazinøff", "Helge Hitler"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highscoreListe = findViewById(R.id.highscoreListID);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, userTabell);
        highscoreListe.setAdapter(arrayAdapter);

        highscoreListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user = arrayAdapter.getItem(position);
            }
        });
    }

    public void VisSpilleAutomatHighscores(View view) {
    }

    public void VisBlackjackHighscores(View view) {
    }

}
