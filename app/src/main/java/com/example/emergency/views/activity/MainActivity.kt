@file:Suppress("DEPRECATION")

package com.example.emergency.views.activity

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.preference.PreferenceManager
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.emergency.R
import com.example.emergency.views.Fragment.ContactFragment
import com.example.emergency.views.Fragment.HomeFragment
import com.example.emergency.views.Fragment.LocationFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.squareup.seismic.ShakeDetector


class MainActivity : BaseActivity(), View.OnClickListener, ShakeDetector.Listener {
    lateinit var home: ImageView
    lateinit var location: ImageView
    lateinit var contact: ImageView

    var client: FusedLocationProviderClient? = null

    override fun getContentId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !==
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !==
            PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 2
            )
        }
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.SEND_SMS
            ) !==
            PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.SEND_SMS), 3
            )
        }

        val h = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl, h, "d")
            .commit()

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)

        var sensorDelay = SensorManager.SENSOR_DELAY_GAME
        sd.start(sensorManager, sensorDelay)
        client = FusedLocationProviderClient(this)
        // Click listener
        home = findViewById(R.id.ivHome)
        location = findViewById(R.id.ivLocation)
        contact = findViewById(R.id.ivContact)
        home.setOnClickListener(this)
        location.setOnClickListener(this)
        contact.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                    }
                } else {
                }
                return
            }
            2 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                    }
                } else {
                }
                return
            }
            3 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.SEND_SMS
                        ) ===
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                    }
                } else {
                }
                return
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ivHome -> {
                val home = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl, home, "d")
                    .commit()
            }

            R.id.ivLocation -> {
                val location = LocationFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl, location, "d")
                    .commit()
            }

            R.id.ivContact -> {
                val contact = ContactFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl, contact, "d")
                    .commit()
            }
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"),  PendingIntent.FLAG_UPDATE_CURRENT)
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)
    }

    private fun sendSMS2(phoneNumber: String, message: String) {
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), PendingIntent.FLAG_UPDATE_CURRENT)
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)
    }

    override fun hearShake() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permissions not granted, handle accordingly
            Log.e("TAG", "Location permissions not granted")
            return
        }

        // Check if client is null or permissions are not granted
        if (client == null) {
            Log.e("TAG", "FusedLocationProviderClient is null")
            return
        }

        // Get last known location
        client!!.lastLocation.addOnSuccessListener { location ->

            if (location != null) {
                // Use the last known location
                Log.d("TAG", "Last known location: ${location.latitude}, ${location.longitude}")

                // Send alert message with the last known location
                val message =
                    "Hi, I need help. My live location is http://maps.google.com/maps?daddr=${location.latitude},${location.longitude}"
                val preferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
                preferences.getString("phone1", null)?.takeIf { it.isNotEmpty() }?.let { phone1 ->
                    sendSMS(phone1, message)
                }
                preferences.getString("phone2", null)?.takeIf { it.isNotEmpty() }?.let { phone2 ->
                    sendSMS2(phone2, message)
                }
            } else {
                // Last known location is null, handle accordingly (e.g., request location updates)
                Log.e("TAG", "Error getting last known location")
            }
        }.addOnFailureListener { e ->
            // Handle failure to retrieve last known location
            Log.e("TAG", "Error getting last known location", e)
            // You may prompt the user to enable location services or request location updates here
            // For example:
            // requestLocationUpdates()
        }
        }
    }
