package com.vartyr.sensornode;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationSensorService extends Service implements android.location.LocationListener {

    private String TAG = getClass().getSimpleName();
    private LocationManager mLocationManager = null;
    public Location mLastLocation = new Location("?");
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    private Geocoder geocoder = null;                                   // GeoCoder to get address

    LocationSensor locationSensorSingleton = LocationSensor.getInstance();

    public LocationSensorService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        initializeLocationManager();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand from intent: " + String.valueOf(startId));
        requestLocationUpdates(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }



    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }


    private void initializeGeoCoder(){
        geocoder  = new Geocoder(getApplicationContext(), Locale.getDefault());
    }


    public void requestLocationUpdates(Context c) {

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, this);
        } catch (java.lang.SecurityException ex) {
            Log.e(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "network provider does not exist, " + ex.getMessage());
        }

    }

    public Address getAddressWithLocationFromGeoCoder(Location loc) {

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
        } catch (IOException ioException) {
            Log.e(TAG, ioException.toString());
        }

        if (addresses == null || addresses.size()  == 0) { }
        else {
            Address address = addresses.get(0);
            return address;
        }

        return null;
    }



    //    LocationListener callbacks

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: " + location);
        mLastLocation.set(location);
        locationSensorSingleton.provider.onLocationUpdateReady(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { Log.d(TAG, "onStatusChanged: " + provider); }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) { Log.d(TAG, "onProviderDisabled: " + provider); }






}
