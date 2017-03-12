package com.example.puz;

import android.util.Log;

import java.io.Serializable;

public class MapPosition implements Serializable {

    private String id;
    private double lat, lng;
    private Challenge challenge;

    public MapPosition (String name, double lat, double lng, Challenge challenge) {
        this.id = name;
        this.lat = lat;
        this.lng = lng;
        this.challenge = challenge;
        this.challenge.setPosition(this);
        Log.d("tag", "Creating new challenge: " + id + ", " + lat + ", " + lng);
    }

    public String getId () {
        return this.id;
    }

    public double getLat () {
        return this.lat;
    }

    public double getLng () {
        return this.lng;
    }

    public Challenge getChallenge () {
        return this.challenge;
    }

}
