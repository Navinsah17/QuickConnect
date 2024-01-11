package com.example.quickconnect.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quickconnect.Fragments.GetUserNumber
import com.example.quickconnect.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, GetUserNumber())
            .commit()
    }
}