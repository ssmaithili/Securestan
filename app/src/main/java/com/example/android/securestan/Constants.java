package com.example.android.securestan;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;



public class Constants {


    public static final String GEOFENCE_ID_STAN_UNI = "";
    public static final float GEOFENCE_RADIUS_IN_METERS = 100;

    /**
     * Map for storing information about UMIT.
     */
    public static final HashMap<String, LatLng> AREA_LANDMARKS = new HashMap<String, LatLng>();

    static {
        // UMIT coordinates
        AREA_LANDMARKS.put(GEOFENCE_ID_STAN_UNI, new LatLng(19.0869645, 72.8304397));
    }
}

