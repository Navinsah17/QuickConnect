package com.example.quickconnect.models

data class User(
    var uid: String = "",
    var displayName: String? = "",
    var imageUrl: String = "",
    var emailId:String = "",
    var phoneNo:String = "",
    var status:String = ""
)