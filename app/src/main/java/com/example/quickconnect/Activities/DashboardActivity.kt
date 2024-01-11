package com.example.quickconnect.Activities

import android.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quickconnect.Fragments.ChatFragment
import com.example.quickconnect.Fragments.ContactFragment
import com.example.quickconnect.Fragments.ProfileFragment
import com.example.quickconnect.R
import com.example.quickconnect.databinding.ActivityDashboardBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class DashboardActivity : AppCompatActivity() {

    lateinit var bindingd: ActivityDashboardBinding
    lateinit var chipNavigationBar: ChipNavigationBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingd = ActivityDashboardBinding.inflate(layoutInflater)

        setContentView(bindingd.root)


        chipNavigationBar = bindingd.bottomChip

        supportFragmentManager.beginTransaction()
            .replace(R.id.dashboardContainer, ChatFragment())
            .commit()

        chipNavigationBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.btnChat -> replaceFragment(ChatFragment())
                R.id.btnProfile -> replaceFragment(ProfileFragment())
                R.id.btnContact -> replaceFragment(ContactFragment())
            }
        }
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.dashboardContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}