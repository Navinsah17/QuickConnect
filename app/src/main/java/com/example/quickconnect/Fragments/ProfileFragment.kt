package com.example.quickconnect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quickconnect.R
import com.example.quickconnect.ViewModel.ProfileViewModel
import com.example.quickconnect.databinding.FragmentGetUserNumberBinding
import com.example.quickconnect.databinding.FragmentProfileBinding
import com.example.quickconnect.models.User


class ProfileFragment : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        profileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        profileViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(ProfileViewModel::class.java)

        profileViewModel.getUser().observe(viewLifecycleOwner, Observer {
            profileBinding.userModel = it

                profileBinding.name.text = it.displayName

        })


       return profileBinding.root
    }

}