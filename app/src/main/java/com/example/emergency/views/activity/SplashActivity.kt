package com.example.emergency.views.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.emergency.R

class SplashActivity : BaseActivity() {
    override fun getContentId(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handler for open main activity
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            gotoActivity(intent)
            finishAffinity()
        }, 2000)
    }

}