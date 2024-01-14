package com.example.quickconnect.Utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AppUtils {
    public fun getUID():String?{
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.uid
    }

    fun updateOnlineStatus(status: String) {
        val databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(getUID()!!)
        val map = HashMap<String, Any>()
        map["online"] = status
        databaseReference.updateChildren(map)

    }
}