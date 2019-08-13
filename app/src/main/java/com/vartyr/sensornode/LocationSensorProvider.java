package com.vartyr.sensornode;

import android.location.Location;

public interface LocationSensorProvider {

        void onLocationUpdateReady(Location location);


}
