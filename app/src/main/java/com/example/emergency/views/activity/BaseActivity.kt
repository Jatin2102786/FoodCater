package com.example.emergency.views.activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emergency.R


abstract class BaseActivity : AppCompatActivity() {
    lateinit var self: Context
    protected abstract fun getContentId(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (getContentId() != 0) {
            setContentView(getContentId())
        }
        self = this@BaseActivity
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    fun gotoActivity(intent: Intent) {
        intent.putExtra("classFrom", self.javaClass.simpleName)
        startActivity(intent)
    }

    // Hide keyboard
    protected fun hideKeyboard(view: View?) {
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
