package com.example.quickconnect.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quickconnect.R
import com.example.quickconnect.Utils.AppUtils
import com.example.quickconnect.databinding.ActivityEditNameBinding

class EditNameActivity : AppCompatActivity() {
    lateinit var bindingen: ActivityEditNameBinding
    private lateinit var fName: String
    private lateinit var utils: AppUtils

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingen = ActivityEditNameBinding.inflate(layoutInflater)
        setContentView(bindingen.root)



        if (intent.hasExtra("displayName")) {
            val name = intent.getStringExtra("displayName")
            bindingen.edtFName.setText(name)

        }

        bindingen.btnEditName.setOnClickListener {
            if(areFieldEmpty()){
                val intent = Intent()
                intent.putExtra("displayName","$fName")
                setResult(100,intent)
                finish()
            }
        }
    }
    private fun areFieldEmpty(): Boolean {
        fName = bindingen.edtFName.text.toString()

        var required: Boolean = false
        var view: View? = null

        if (fName.isEmpty()) {
            bindingen.edtFName.error = "Field is required"
            required = true
            view = bindingen.edtFName

        }
        return if (required) {
            view?.requestFocus()
            false
        } else true
    }

//    override fun onPause() {
//        super.onPause()
//        utils. updateOnlineStatus("offline")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        utils.updateOnlineStatus("online")
//    }
}