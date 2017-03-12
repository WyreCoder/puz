package com.example.puz;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapController {

    private static int CLICKABLE_RADIUS = 60;
    private static int USER_RADIUS = 5;

    private static MapController instance = null;

    private MapsActivity activity = null;
    private GoogleMap map = null;

    private Circle myRadius = null;
    private Circle myLocation = null;

    private HashMap<String, MapMarker> markers = new HashMap<String, MapMarker>();

    public MapController (MapsActivity map) {
        this.activity = map;
        this.map = map.getMap();
        hookMap();
    }

    public static MapController getInstance (MapsActivity map) {
        if (instance == null) {
            instance = new MapController(map);
        }
        if (instance.getActivity() != map) {
            instance = new MapController(map);
        }
        return instance;
    }
    public MapsActivity getActivity () {
        return this.activity;
    }

    private void hookMap () {

        GoogleMap map = this.getActivity().getMap();

        myRadius = map.addCircle(new CircleOptions()
                .center(new LatLng(0, 0))
                .radius(CLICKABLE_RADIUS)
                .strokeColor(Color.argb(70, 255, 0, 0))
                .fillColor(Color.argb(70, 255, 0, 0)));

        myLocation = map.addCircle(new CircleOptions()
                .center(new LatLng(0, 0))
                .radius(USER_RADIUS)
                .strokeColor(Color.TRANSPARENT)
                .fillColor(Color.argb(150, 255, 0, 0)));

    }

    public void onLocationChanged (LatLng location) {
        // Take the location and do stuff on the map
        myRadius.setCenter(location);
        myLocation.setCenter(location);

        refreshMarkers(location);
    }

    public void onMarkerClick (Marker myMarker) {
        MapMarker marker = (MapMarker) myMarker.getTag();
        MapPosition position = marker.getMapPosition();

        Log.d("tag", "Clicked " + position.getId());
        Challenge challenge = position.getChallenge();

        Log.d("tag", "Received challenge: " + challenge.getQuestion());

        Intent intent = challenge.getIntent(getActivity());
        getActivity().startActivityForResult(intent, 0);
    }

    public void refreshMarkers (LatLng location) {

        List<String> deletable = new LinkedList<String>();

        for (Map.Entry<String, MapMarker> entry : markers.entrySet()) {
            String id = entry.getKey();
            MapMarker marker = entry.getValue();
            if (!marker.isInScope(null)) {
                marker.destroy();
                deletable.add(id);
            }
        }
        for (String id : deletable) {
            markers.remove(id);
        }

        //List<MapPosition> positions = new LinkedList<MapPosition>();
        //positions.add(new MapPosition("id1", 51.52491476401518, -0.1402553915977478, challengeA));
        //positions.add(new MapPosition("id2", 51.52624019025365, -0.13907320797443387, challengeB));

        API api = API.getInstance();

        api.getChallenges(location, new Response.Listener<List<MapPosition>>() {
            @Override
            public void onResponse(List<MapPosition> positions) {
                Log.d("tag", "GOT THE RESPONSE :-)");
                // LOAD TEH MARKERS
                for (MapPosition position : positions) {
                    if (markers.containsKey(position.getId())) {
                        continue;
                    }
                    MapMarker newMarker = new MapMarker(map, new LatLng(position.getLat(), position.getLng()), position);
                    markers.put(position.getId(), newMarker);
                }
            }
        });



    }

    public class MapMarker {

        private Marker marker = null;
        private MapPosition mapPosition;

        public MapMarker (GoogleMap map, LatLng position, MapPosition mapPosition) {
            marker = map.addMarker(new MarkerOptions()
                .position(position)
                .title("TREASURE"));
            boolean complete = mapPosition.getChallenge().isComplete();
            Bitmap icon = BitmapFactory.decodeResource(Application.getAppContext().getResources(), complete ? R.drawable.marker_done : R.drawable.marker);
            Bitmap resized = Bitmap.createScaledBitmap(icon, 48, 48, false);

            marker.setTag(this);
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(resized));
            this.mapPosition = mapPosition;
        }

        public Marker getMarker() {
            return this.marker;
        }

        public MapPosition getMapPosition () {
            return this.mapPosition;
        }

        public String getId () {
            return this.mapPosition.getId();
        }

        public void destroy () {
            marker.remove();
        }

        public boolean isInScope(LatLng position) {
            return true;
        }

    }

}
