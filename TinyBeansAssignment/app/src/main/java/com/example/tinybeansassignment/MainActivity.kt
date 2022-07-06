package com.example.tinybeansassignment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tinybeansassignment.fragments.ScreenOneFragment
import com.example.tinybeansassignment.fragments.ScreenTwoFragment


class MainActivity : AppCompatActivity() {
    private lateinit var loadingDialog: Dialog
    private lateinit var currentFragment: Fragment
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
        transaction.addToBackStack(null)
        transaction.commit()
        currentFragment = fragment
        val displayBackButton = fragment is ScreenTwoFragment
        supportActionBar?.setDisplayHomeAsUpEnabled(displayBackButton)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(currentFragment is ScreenTwoFragment)
            changeFragment(ScreenOneFragment())
        else
            super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    fun showLoading(isVisible: Boolean = true){
        this.runOnUiThread {
            if(isVisible)
                loadingDialog.show()
            else
                loadingDialog.hide()
        }
    }

    fun displayAlert(title: String, message: String){
        val dialogBuilder = AlertDialog.Builder(this)
        val alert = dialogBuilder.apply {
            setMessage(message)
            setCancelable(false)
            setPositiveButton("Ok") { _, _ -> }
        }.create()
        alert.setTitle(title)
        alert.show()
    }
}