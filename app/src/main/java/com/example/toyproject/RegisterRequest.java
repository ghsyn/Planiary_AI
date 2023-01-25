package com.example.toyproject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://http://ftpcdw3521.dothome.co.kr/register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userAddress, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userAddress", userAddress);
    }

    @Override
    public Map<String, String> getParams()throws AuthFailureError {
        return parameters;
    }
}
