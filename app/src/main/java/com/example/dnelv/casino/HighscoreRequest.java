package com.example.dnelv.casino;

import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HighscoreRequest extends StringRequest {
    private static final String HIGHSCORE_REQUEST_URL = "https://itfag.usn.no/~161741/casinoapp/getHighscore.php";
    private Map<String, String> params;

    public HighscoreRequest(String game, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.POST, HIGHSCORE_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("game", game);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
