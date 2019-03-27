package com.example.dnelv.casino;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class HighscoreActivity extends AppCompatActivity {

    ListView highscoreListe;
    String[] userTabell = {"Danay", "Åsmund", "Nazinøff", "Helge Hitler"};
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        layout = findViewById(R.id.mainLayout);
        highscoreListe = findViewById(R.id.highscoreListID);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, userTabell);
        highscoreListe.setAdapter(arrayAdapter);

        highscoreListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user = arrayAdapter.getItem(position);
                Intent popupIntent = new Intent(HighscoreActivity.this, Pop.class);
                layout.setAlpha((float) .5);
                popupIntent.putExtra("User", "Helge Hitler");
                popupIntent.putExtra("Game", "Blackjack");
                popupIntent.putExtra("Score", 200);
                startActivity(popupIntent);
            }
        });
    }

    @Override
    public void onResume() {
        layout.setAlpha(1);
        super.onResume();
    }

    public void VisSpilleAutomatHighscores(View view) {
    }

    public void VisBlackjackHighscores(View view) {
    }
}
