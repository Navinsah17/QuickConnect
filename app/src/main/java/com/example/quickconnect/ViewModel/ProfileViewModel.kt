package com.example.quickconnect.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quickconnect.AppRepository
import com.example.quickconnect.models.User

class ProfileViewModel : ViewModel(){
    private var appRepository = AppRepository.StaticFunction.getInstance()

    public fun getUser():LiveData<User>{
        return appRepository.getUser()
    }


}