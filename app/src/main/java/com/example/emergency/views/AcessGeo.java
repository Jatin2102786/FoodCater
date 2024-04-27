package com.example.emergency.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class AcessGeo {

    FusedLocationProviderClient client;

    public AcessGeo(Context context) {
        client = LocationServices.getFusedLocationProviderClient(context);
    }

    public Task<Location> getMylocation() {

        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        return task;
    }
}
