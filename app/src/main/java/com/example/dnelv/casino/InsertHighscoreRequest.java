package com.example.dnelv.casino;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertHighscoreRequest extends StringRequest {
    private static final String HIGHSCORE_REQUEST_URL = "https://itfag.usn.no/~161747/casino/insertHighscore.php";
    private Map<String, String> params;

    public InsertHighscoreRequest( String userId, String highscore, String spill, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.POST, HIGHSCORE_REQUEST_URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error:", error.toString());
            }
        });

        params = new HashMap<>();
        params.put("userId", userId);
        params.put("highscore", highscore);
        params.put("spill", spill);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
