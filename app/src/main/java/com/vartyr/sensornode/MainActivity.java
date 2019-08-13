package com.vartyr.sensornode;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationSensorProvider {


    private String TAG = getClass().getSimpleName();
    private LocationSensor locationSensorSingleton = LocationSensor.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestLocationPermissions(MainActivity.this);
        locationSensorSingleton.initializeAndRequestUpdates(this, MainActivity.this);                    // Init and begin stuff with
        locationSensorSingleton.setListener(this);

    }




    // Permission methods
    private void requestLocationPermissions(Activity a) {
        ActivityCompat.requestPermissions(a,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    //    public boolean checkLocationPermission()
//    {
//        String permission = "android.permission.ACCESS_FINE_LOCATION";
//        int res = this.checkCallingOrSelfPermission(permission);
//        return (res == PackageManager.PERMISSION_GRANTED);
//    }


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


//    Location Sensor callbacks

    @Override
    public void onLocationUpdateReady(Location location) {

        // Update UI methods when we get this callback.
        updateLocationStringWithLocation(location);
//        updateLocationStringWithGeoLocation(locationSensorSingleton.getAddressWithLocationFromGeoCoder(location));

    }


    // UI methods:

    public void updateLocationStringWithLocation(Location loc){

        String lat = String.valueOf(loc.getLatitude());
        String lon= String.valueOf(loc.getLongitude());
        String provider = loc.getProvider();
        ((TextView)findViewById(R.id.locationData)).setText("Lat/Lon: " + lat + ", " + lon + " | " + provider);
    }

    public void updateLocationStringWithGeoLocation(Address address) {

        if (address == null) {return;}

        String locality = address.getLocality();
        String zipCode = address.getCountryName();
        String country = address.getPostalCode();

        ((TextView)findViewById(R.id.granularLocationData)).setText(locality + " " + zipCode + " " + country);

    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }
}
