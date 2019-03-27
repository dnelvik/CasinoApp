package com.example.dnelv.casino;

import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SaldoRequest extends StringRequest {
    private static final String SALDO_REQUEST_URL = "https://itfag.usn.no/~161747/casino/updateSaldo.php";
    private Map<String, String> params;

    public SaldoRequest(String userId, String sum, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.POST, SALDO_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("userId", userId);
        params.put("sum", sum);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
