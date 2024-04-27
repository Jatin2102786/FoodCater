package com.example.emergency.views.Fragment

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.emergency.R
import com.squareup.seismic.ShakeDetector


class ContactFragment : Fragment(),ShakeDetector.Listener{
    lateinit var et1: EditText
    lateinit var et2: EditText
    lateinit var btn: Button
    lateinit var tv1: TextView
    lateinit var tv2: TextView
    var pattern =
        "^\\s*(?:\\+?(\\d{1,3}))?[-. (](\\d{3})[-. )](\\d{3})[-. ](\\d{4})(?: *x(\\d+))?\\s$"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val v = inflater.inflate(R.layout.fragment_contact, container, false)
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        et1 = v.findViewById(R.id.etPhone1)
        et2 = v.findViewById(R.id.etPhone2)
        tv1 = v.findViewById(R.id.tvPhone1)
        tv2 = v.findViewById(R.id.tvPhone2)
        btn = v.findViewById(R.id.btnSubmit)

        if(preferences.getString("phone1","")!!.isNotEmpty() && preferences.getString("phone1", "") != null)
        {
            tv1.text =preferences.getString("phone1", "")
        }

        if(preferences.getString("phone2","")!!.isNotEmpty() && preferences.getString("phone2", "") != null)
        {
            tv2.text = preferences.getString("phone2", "")
        }


        btn.setOnClickListener {

            val editor = preferences.edit()
            editor.putString("phone1", et1.text.toString().trim())
            editor.putString("phone2", et2.text.toString().trim())
            editor.apply()

            if (et1.text.toString().isValidPhoneNumber()&& et1.text.toString().length>8){
                tv1.text = preferences.getString("phone1", "")
            }else{
                et1.error = "Invalid phone number."
            }
            if (et2.text.toString().isValidPhoneNumber()&& et2.text.toString().length>6){
                tv2.text = preferences.getString("phone2", "")
            }else{

                et2.error = "Invalid phone number."
            }


        }
        return v
    }

    override fun hearShake() {
        Toast.makeText(requireContext(), "shake", Toast.LENGTH_SHORT).show()
    }
    fun CharSequence?.isValidPhoneNumber():Boolean{
        return !isNullOrEmpty() && Patterns.PHONE.matcher(this).matches()
    }
}