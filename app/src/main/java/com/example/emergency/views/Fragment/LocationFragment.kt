package com.example.emergency.views.Fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.emergency.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : Fragment(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    var client: FusedLocationProviderClient? = null
    private var lat = 0.0
    private var lng = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_location, container, false)

        // Set map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)


        client = LocationServices.getFusedLocationProviderClient(requireActivity())


        return v
    }

    override fun onMapReady(map: GoogleMap) {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        client!!.lastLocation.addOnSuccessListener { location ->
            Log.d("TAG", "onCreateView: " + location.latitude)
            lat = location.latitude
            lng = location.longitude


            val sydney = LatLng(location.latitude, location.longitude)
            map!!.animateCamera(CameraUpdateFactory.newLatLng(sydney))
            map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0F))
            map.addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("My location")
            )
        }
    }
}