package com.example.puz;


import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class API {

    public static final String BASE_URL = "http://31.220.43.150:4567/api/v1";
    private RequestQueue mRequestQueue;
    private String authToken = "thisisatesttoken";

    private static API instance;

    public static API getInstance () {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    public API () {
        Cache cache = new DiskBasedCache(Application.getAppContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
    }


    public void getChallenges (LatLng location, Response.Listener<List<MapPosition>> callback) {

        final Response.Listener<List<MapPosition>> calls = callback;
        get("/search?latitude=" + location.latitude + "&longitude=" + location.longitude, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<MapPosition> positions = new LinkedList<MapPosition>();

                try {
                    Log.d("tag", response);
                    JSONObject json = new JSONObject(response);
                    JSONArray items = json.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = (JSONObject) items.get(i);


                        if (!item.getString("type").equals("RIDDLE")) {
                            Log.d("tag", "Received something strange");
                            continue;
                        }

                        Log.d("tag", "herro there");
                        JSONArray answerJSON = item.getJSONArray("answers");
                        ArrayList<String> answers = new ArrayList<String>();
                        for (int j = 0; j < answerJSON.length(); j++) {
                            answers.add(answerJSON.getString(j));
                        }

                        Log.d("tag", item.getString("question"));
                        Challenge challenge = new Challenge(item.getString("question"), answers);
                        positions.add(new MapPosition(String.valueOf(item.getInt("challenge_id")), item.getDouble("latitude"), item.getDouble("longitude"), challenge));

                    }
                } catch (JSONException e) {
                    Log.e("tag", e.getMessage());
                }

                calls.onResponse(positions);
            }
        });

    }

    // get2 doesn't send the Authorization header, get does
    protected void get2 (String url, Response.Listener<String> callback) {
        url = BASE_URL + url;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Application.getAppContext(), "Network Error. :(", Toast.LENGTH_SHORT).show();
                    }
                });

        mRequestQueue.add(stringRequest);
    }

    protected void get (String url, Response.Listener<String> callback) {
        url = BASE_URL + url;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            callback,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.wtf("tag", "I have a big error :( " + error.toString());
                    Toast.makeText(Application.getAppContext(), "Network Error. :(", Toast.LENGTH_SHORT).show();
                }
            }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "token " + authToken);
                return params;
            }
        };

        Log.d("tag", "Adding to queue :-)");
        mRequestQueue.add(stringRequest);
    }



    protected void post2 (String url, Map<String, String> params, Response.Listener<String> callback) {
        url = BASE_URL + url;

        final Map<String, String> data = params;
        Request request = new StringRequest(Request.Method.POST, url,
            callback,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Application.getAppContext(), "Network Error. :(", Toast.LENGTH_SHORT).show();
                }
            }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               return data;
            }

        };

        mRequestQueue.add(request);
    }

    protected void post (String url, Map<String, String> params, Response.Listener<String> callback) {
        url = BASE_URL + url;
        final Map<String, String> data = params;
        Request request = new StringRequest(Request.Method.POST, url,
                callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Application.getAppContext(), "Network Error. :(", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return data;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "token " + authToken);
                return params;
            }
        };

        mRequestQueue.add(request);
    }

}
