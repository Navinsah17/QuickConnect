package com.example.quickconnect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quickconnect.R
import com.example.quickconnect.databinding.FragmentGetUserNumberBinding
import com.example.quickconnect.databinding.FragmentProfileBinding
import com.example.quickconnect.models.User


class ProfileFragment : Fragment() {
    private var _bindingp: FragmentProfileBinding? = null
    private val bindingp get() = _bindingp!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _bindingp = FragmentProfileBinding.inflate(inflater, container, false)


       return bindingp.root
    }

}