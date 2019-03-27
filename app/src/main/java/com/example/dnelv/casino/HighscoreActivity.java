package com.example.dnelv.casino;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HighscoreActivity extends AppCompatActivity {

    ListView highscoreListe;
    private String game;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        layout = findViewById(R.id.mainLayout);
        highscoreListe = findViewById(R.id.highscoreListID);
        game = "Blackjack";
        getHighscores();

    }

    private void defineList(final String[] userTabell, final int[] scoreTabell){
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, userTabell);
        highscoreListe.setAdapter(arrayAdapter);

        highscoreListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Test", ""+position);
                String user = arrayAdapter.getItem(position);
                Intent popupIntent = new Intent(HighscoreActivity.this, Pop.class);
                layout.setAlpha((float) .5);
                popupIntent.putExtra("User", userTabell[position]);
                popupIntent.putExtra("Game", game);
                popupIntent.putExtra("Score", scoreTabell[position]);
                startActivity(popupIntent);
            }
        });
    }

    private void getHighscores() {
        String[][] returTabell = null;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String[] userTabell;
                    int[] scoreTabell;

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        JSONObject jsonObject = new JSONObject(jsonResponse.getString("array"));
                        scoreTabell = new int[jsonObject.length()];
                        userTabell = new String[jsonObject.length()];
                        for (int i=0; i<jsonObject.length(); i++){
                            JSONArray highscoreArray = jsonObject.getJSONArray(""+(i+1));
                            userTabell[i] = highscoreArray.getString(0);
                            scoreTabell[i] = highscoreArray.getInt(1);
                        }
                        defineList(userTabell, scoreTabell);
                    } else {
                        String error = jsonResponse.getString("error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        HighscoreRequest highscoreRequest = new HighscoreRequest("blackjack", responseListener, null);
        RequestQueue queue = Volley.newRequestQueue(HighscoreActivity.this);
        queue.add(highscoreRequest);
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
