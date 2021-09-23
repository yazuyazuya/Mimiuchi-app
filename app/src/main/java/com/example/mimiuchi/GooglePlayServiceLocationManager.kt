package com.example.mimiuchi

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import io.tangerine.beaconreceiver.android.sdk.application.LocationManager

class GooglePlayServiceLocationManager(val context: Context) : LocationManager {

    private var location : Location? = null
    private var fusedLocationProviderClient : FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var locationRequest : LocationRequest? = null

    init {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            val locationRequestInterval: Long = 750
            locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(locationRequestInterval)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    location = locationResult!!.lastLocation
//                    Log.d("Beaconlocation", location.toString())
//                    Log.d("BeaconlocationRequest", locationRequest.toString())
//                    Log.d("BeaconfusedLocation", fusedLocationProviderClient.toString())
                }
            }, null)

        }
    }

    override fun getLatitude(): Double {
        location?.let { return it.latitude } ?: return 0.0
    }

    override fun getLongitude(): Double {
        location?.let { return it.longitude } ?: return 0.0
    }

    override fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return
        }

        val locationRequestInterval : Long = 750
        locationRequest =
            LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(locationRequestInterval)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                location = locationResult!!.lastLocation
            }
        }, null)

    }

    override fun removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                location = locationResult!!.lastLocation
            }
        })

    }

}