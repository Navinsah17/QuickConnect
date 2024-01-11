package com.example.quickconnect.models

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

data class User(
    var uid: String = "",
    var displayName: String? = "",
    var imageUrl: String = "",
    var emailId:String = "",
    var phoneNo:String = "",
    var status:String = ""
){
    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: CircleImageView, imageUrl: String?) {
            imageUrl?.let {
                Glide.with(view.context).load(imageUrl).into(view)
            }
        }
    }
}