package com.example.tinybeansassignment

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.Fragment
import com.example.tinybeansassignment.fragments.ScreenOneFragment

class MainActivity : AppCompatActivity() {
    private lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingDialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.loading_layout)
        }
        changeFragment(ScreenOneFragment())
    }

    fun changeFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment, fragment.javaClass.simpleName)
        transaction.commit()
    }

    fun showLoading(isVisible: Boolean = true){
        this.runOnUiThread {
            if(isVisible)
                loadingDialog.show()
            else
                loadingDialog.hide()
        }
    }
}