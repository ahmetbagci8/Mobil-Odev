package com.example.mobilodev;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

public class LocationActivity extends AppCompatActivity {
    FusedLocationProviderClient mFusedLocationClient;
    TextView txtLocation;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    Button getLocationButton,sendLocationButton;
    double wayLatitude;
    double wayLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        txtLocation = findViewById(R.id.location_text);
        txtLocation.setText("Lokasyon İsteği Yapınız");
        getLocationButton = findViewById(R.id.getLocation_Button);
        sendLocationButton = findViewById(R.id.sendLocation_Button);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);
        locationRequest.setInterval(20 * 1000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        txtLocation.setText("Latitude: " +String.valueOf(wayLatitude) + "\n" + "Longitude: " +String.valueOf(wayLongitude));
                    }
                    Toast.makeText(getApplicationContext(), String.valueOf(wayLatitude) + " " + String.valueOf(wayLongitude), Toast.LENGTH_SHORT).show();
                }
            }
        };
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            }
        });
        sendLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String whatsAppMessage = "http://maps.google.com/maps?saddr=" + wayLatitude + "," + wayLongitude;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });
    }


}


