package com.example.puz;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private MapController controller;

    private static Location location = null;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.d("tag", "Created");

    }

    public GoogleMap getMap() {
        return this.mMap;
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady (GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        controller = MapController.getInstance(this);
        disableMapFeatures();
        requestLocation();

    }

    public void requestLocation () {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.wtf("tag", "FAIL - NO LOCATION PERMISSIONS");
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            ActivityCompat.requestPermissions(MapsActivity.this, permissions, 1);
            return;
        }
        mMap.setMyLocationEnabled(false);
        LocationManager lMan = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria c = new Criteria();
        String best = lMan.getBestProvider(c, true);
        final Location location = MapsActivity.location != null ? MapsActivity.location : lMan.getLastKnownLocation(best);

        if (location != null) {
            onLocationChanged(location, false);
        }
        lMan.requestLocationUpdates(best, 500, 0, this);

    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResults) {

        if (requestCode != 1) {
            return;
        }
        if (grantResults.length == 0) {
            Toast.makeText(MapsActivity.this, "I can't check your location. Boo.", Toast.LENGTH_SHORT).show();
            return;
        }
        // We have permission!
        Log.d("tag", "I have permissions to view locations! :-)");
        requestLocation();

    }

    @Override
    public void onLocationChanged(Location location) {
        onLocationChanged(location, true);
    }
    public void onLocationChanged(Location location, boolean animate) {

        MapsActivity.location = location;
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        LatLng ll = new LatLng(lat, lng);
        if (animate) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        } else {
            mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
        Log.d("tag", "Lat: " + lat + ", Lng:" + lng);

        controller.onLocationChanged(ll);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO
    }

    public void disableMapFeatures() {
        UiSettings settings = mMap.getUiSettings();
        settings.setScrollGesturesEnabled(false);
        settings.setRotateGesturesEnabled(false);
        settings.setZoomControlsEnabled(false);
        settings.setMapToolbarEnabled(false);
        settings.setAllGesturesEnabled(false);

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style_json));
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("tag", "Clicked marker " + marker);
                controller.onMarkerClick(marker);
                return true;
            }
        });
        Log.d("tag", "test");
    }

    @Override
    public void onMapClick(LatLng latlng) {
        Log.d("tag", "clicked " + latlng.latitude + ", " + latlng.longitude);
    }



}
