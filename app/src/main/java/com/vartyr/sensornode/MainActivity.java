package com.vartyr.sensornode;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements android.location.LocationListener {

    private String TAG = getClass().getSimpleName();


    // Get location:
    public Location mLastLocation = new Location("?");
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;

    // GeoCoder to get address
    Geocoder geocoder;


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLocationManager();                    // Init location manager
        requestLocationUpdates();                       // Request location updates

        geocoder  = new Geocoder(this, Locale.getDefault());


    }


    // UI methods:

    public void updateLocationStringWithLocation(Location loc){

        String lat = String.valueOf(loc.getLatitude());
        String lon= String.valueOf(loc.getLongitude());
        String provider = loc.getProvider();
        ((TextView)findViewById(R.id.locationData)).setText("Lat/Lon: " + lat + ", " + lon + " | " + provider);
    }

    public void updateLocationStringWithGeoLocation(Location loc) {

        String locality = ""; // City
        String zipCode = "";    // Zip
        String country = ""; // Country

        // Use the geoCoder to get a detailed look

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    loc.getLatitude(),
                    loc.getLongitude(),
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.e(TAG, ioException.toString());
        }

        if (addresses == null || addresses.size()  == 0) {

        } else {

            Address address = addresses.get(0);


            locality = address.getLocality();
            zipCode = address.getCountryName();
            country = address.getPostalCode();


        }

        ((TextView)findViewById(R.id.granularLocationData)).setText(locality + " " + zipCode + " " + country);


    }





    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }


    private void requestLocationUpdates() {

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, this);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
            requestLocationPermissions();
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

    }


    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }



    private void requestLocationPermissions() {

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



//    LocationListener callbacks

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: " + location);
        mLastLocation.set(location);
        updateLocationStringWithLocation(location);
        updateLocationStringWithGeoLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged: " + provider);

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled: " + provider);

    }













    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
    }
}
