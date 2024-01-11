package com.example.quickconnect.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quickconnect.R
import com.example.quickconnect.databinding.FragmentGetUserNumberBinding
import com.example.quickconnect.databinding.FragmentVerifyNumberBinding
import com.example.quickconnect.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class VerifyNumber : Fragment() {

    private var code: String? = null
    private lateinit var pin: String
    private var firebaseAuth: FirebaseAuth? = null
    private var databaseReference: DatabaseReference? = null

    private var _bindingv: FragmentVerifyNumberBinding? = null
    private val bindingv get() = _bindingv!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code = it.getString("Code")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingv = FragmentVerifyNumberBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        bindingv.btnVerify.setOnClickListener {
            if (checkPin()) {
                val credential = PhoneAuthProvider.getCredential(code!!, pin)
                signInUser(credential)
            }
        }
        return bindingv.root
    }

    private fun signInUser(credential: PhoneAuthCredential) {
        firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val userModel =
                    User(
                        "", "", "",
                        firebaseAuth!!.currentUser!!.phoneNumber!!,
                        firebaseAuth!!.uid!!
                    )

                databaseReference!!.child(firebaseAuth?.uid!!).setValue(userModel)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, GetUserData())
                    .commit()
            }
        }
    }

    


    companion object {

        @JvmStatic
        fun newInstance(code: String) =
            VerifyNumber().apply {
                arguments = Bundle().apply {
                    putString("Code", code)
                }

            }
    }

    private fun checkPin(): Boolean {
        pin = bindingv.otpTextView.text.toString()
        if (pin.isEmpty()) {
            bindingv.otpTextView.error = "Filed is required"
            return false
        } else if (pin.length < 6) {
            bindingv.otpTextView.error = "Enter valid pin"
            return false
        } else return true
    }


}