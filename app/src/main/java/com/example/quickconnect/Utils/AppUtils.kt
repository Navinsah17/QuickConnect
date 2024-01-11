package com.example.quickconnect.Utils

import com.google.firebase.auth.FirebaseAuth

class AppUtils {
    public fun getUID():String?{
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.uid
    }
}