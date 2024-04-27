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
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, sentPI, null)
    }

    private fun sendSMS2(phoneNumber: String, message: String) {
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
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

            Log.d("TAG", "Trouble:" + location.latitude + location.longitude)

            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(1000)
            }
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            if (preferences.getString("phone1", "")!!.isNotEmpty() && preferences.getString(
                    "phone1",
                    ""
                ) != null
            ) {
                sendSMS(
                    preferences.getString("phone1", "")!!, "Hi, I need help. My live location is " +
                            "http://maps.google.com/maps?daddr=" + location.latitude + "," + location.longitude
                )
            }

            if (preferences.getString("phone2", "")!!.isNotEmpty() && preferences.getString(
                    "phone2",
                    ""
                ) != null
            ) {
                sendSMS2(
                    preferences.getString("phone2", "")!!,
                    "Hi, I need help. My live location is " +
                            "http://maps.google.com/maps?daddr=" + location.latitude + "," + location.longitude
                )
            }
        }
    }

}