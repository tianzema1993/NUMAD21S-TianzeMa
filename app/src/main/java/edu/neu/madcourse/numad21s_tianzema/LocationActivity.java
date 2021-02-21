package edu.neu.madcourse.numad21s_tianzema;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {
    LocationManager locationManager;
    TextView latText;
    TextView lonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        displayLocation();
    }

    @SuppressLint("MissingPermission")
    private void displayLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null)
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if (location != null) {
            onLocationChanged(location);
        } else {
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this::onLocationChanged);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this::onLocationChanged);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this::onLocationChanged);
            locationManager.removeUpdates(this::onLocationChanged);
        }
    }

    @SuppressLint("MissingPermission")
    private void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        latText = findViewById(R.id.lat);
        lonText = findViewById(R.id.lon);
        @SuppressLint("DefaultLocale") String newLat = latText.getText().toString() + String.format("%.4f", lat);;
        @SuppressLint("DefaultLocale") String newLon = lonText.getText().toString() + String.format("%.4f", lon);;
        latText.setText(newLat);
        lonText.setText(newLon);
    }
}