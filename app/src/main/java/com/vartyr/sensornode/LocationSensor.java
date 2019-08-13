package com.vartyr.sensornode;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationSensor {

    private String TAG = getClass().getSimpleName();

//    public Location mLastLocation = new Location("?");
//    private LocationManager mLocationManager = null;
//    private static final int LOCATION_INTERVAL = 1000;
//    private static final float LOCATION_DISTANCE = 10f;
//    private Geocoder geocoder = null;                                   // GeoCoder to get address

    public LocationSensorProvider provider;

    // For service intent
    private static final int RSS_JOB_ID = 1000;


    // static variable single_instance of type Singleton
    private static LocationSensor instance = null;

    // empty private constructor restricted to this class itself
    private LocationSensor() {

    }

    // static method to create instance of Singleton class
    public static LocationSensor getInstance() {
        if (instance == null) { instance = new LocationSensor(); }
        return instance;
    }


    //GeoCoder Constants (move this somewhere else)
    public final class Constants {
        public static final int SUCCESS_RESULT = 0;
        public static final int FAILURE_RESULT = 1;
        public static final String PACKAGE_NAME =
                "com.google.android.gms.location.sample.locationaddress";
        public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
        public static final String RESULT_DATA_KEY = PACKAGE_NAME +
                ".RESULT_DATA_KEY";
        public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
                ".LOCATION_DATA_EXTRA";
    }


    public void initializeAndRequestUpdates(Context c, Activity a) {


        attemptStartLocationService(a);

//        initializeLocationManager(c);
//        initializeGeoCoder(c);
//        requestLocationUpdates(c);

    }


    private void attemptStartLocationService(Activity a) {
        Intent intent = new Intent(a, LocationSensorService.class);
        a.startService(intent);
    }

    private void attemptStopLocationService(Activity a) {
        Intent intent = new Intent(a, LocationSensorService.class);
        a.stopService(intent);
    }


    public void setListener(LocationSensorProvider listener) {
        Log.d(TAG, "setListener to: " + listener.toString());
        provider = listener;
    }

//
//    private void initializeGeoCoder(Context c){
//        geocoder  = new Geocoder(c, Locale.getDefault());
//    }



//    public void requestLocationUpdates(Context c) {
//
//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, this);
//        } catch (java.lang.SecurityException ex) {
//            Log.e(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.e(TAG, "network provider does not exist, " + ex.getMessage());
//        }
//
//    }

//    public Address getAddressWithLocationFromGeoCoder(Location loc) {
//
//        List<Address> addresses = null;
//
//        try {
//            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
//        } catch (IOException ioException) {
//            Log.e(TAG, ioException.toString());
//        }
//
//        if (addresses == null || addresses.size()  == 0) { }
//        else {
//            Address address = addresses.get(0);
//            return address;
//        }
//
//        return null;
//    }





////    LocationListener callbacks
//    @Override
//    public void onLocationChanged(Location location) {
//        Log.d(TAG, "onLocationChanged: " + location);
//        mLastLocation.set(location);
//        provider.onLocationUpdateReady(location);
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) { Log.d(TAG, "onStatusChanged: " + provider); }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        Log.d(TAG, "onProviderEnabled: " + provider);
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) { Log.d(TAG, "onProviderDisabled: " + provider); }
//


//    public void cleanup() {
//        if (mLocationManager != null) {
//            mLocationManager.removeUpdates(this);
//        }
//
//
//    }




}
