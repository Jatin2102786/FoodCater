
package com.example.emergency.views.Fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.emergency.R
import com.example.emergency.views.AcessGeo

class HomeFragment : Fragment(), View.OnClickListener {
lateinit var fireStation: CardView
lateinit var policeStation: CardView
lateinit var hopital: CardView
    var acessGeo: AcessGeo? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val v  =  inflater.inflate(R.layout.fragment_home, container, false)

        acessGeo = AcessGeo(activity)

        fireStation = v.findViewById(R.id.cvFire)
        policeStation = v.findViewById(R.id.cvPolice)
        hopital = v.findViewById(R.id.cvPharmacy)

        // Set click listener
        fireStation.setOnClickListener(this)
        policeStation.setOnClickListener(this)
        hopital.setOnClickListener(this)
        return v
    }

    override fun onClick(p0: View?) {
       when(p0?.id)
       {
           R.id.cvFire->
           {
               val task = acessGeo!!.mylocation
               task.addOnSuccessListener { location ->
                   val geoIntentUri =
                       Uri.parse("geo:" + location.latitude + "," + location.longitude + "?q=Fire Station")
                   val mapIntent = Intent(Intent.ACTION_VIEW, geoIntentUri)
                   startActivity(mapIntent)
               }
           }

           R.id.cvPolice->
           {
               val task = acessGeo!!.mylocation
               task.addOnSuccessListener { location ->
                   val geoIntentUri =
                       Uri.parse("geo:" + location.latitude + "," + location.longitude + "?q=Police Station")
                   val mapIntent = Intent(Intent.ACTION_VIEW, geoIntentUri)
                   startActivity(mapIntent)
               }
           }

           R.id.cvPharmacy->
           {
               val task = acessGeo!!.mylocation
               task.addOnSuccessListener { location ->
                   val geoIntentUri =
                       Uri.parse("geo:" + location.latitude + "," + location.longitude + "?q=Hospitals")
                   val mapIntent = Intent(Intent.ACTION_VIEW, geoIntentUri)
                   startActivity(mapIntent)
               }
           }
       }
    }

}