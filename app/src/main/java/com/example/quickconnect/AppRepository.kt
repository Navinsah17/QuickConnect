package com.example.quickconnect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quickconnect.Utils.AppUtils
import com.example.quickconnect.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.util.Util

class AppRepository {

    private var liveData : MutableLiveData<User>? = null
    private var databaseReference: DatabaseReference? = null
    private val util = AppUtils()

    object StaticFunction {
        private var instance: AppRepository? = null
        fun getInstance(): AppRepository {
            if (instance == null)
                instance = AppRepository()

            return instance!!
        }
    }



    fun getUser():LiveData<User>{
        if (liveData == null)
            liveData = MutableLiveData()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(util.getUID()!!)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userModel = snapshot.getValue(User::class.java)
                    liveData!!.postValue(userModel)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        return liveData!!

    }

    fun updateStatus(status: String) {
        val databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(util.getUID()!!)

        val map = mapOf<String, Any>("status" to status)
        databaseReference.updateChildren(map)

    }

    fun updateName(userName: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(util.getUID()!!)

        val map = mapOf<String, Any>("displayName" to userName)
        databaseReference.updateChildren(map)
    }

    fun updatImage(imagePath: String) {

        val databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(util.getUID()!!)

        val map = mapOf<String, Any>("imageUrl" to imagePath)
        databaseReference.updateChildren(map)

    }
}