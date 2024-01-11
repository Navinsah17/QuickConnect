package com.example.quickconnect.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.quickconnect.R
import com.example.quickconnect.databinding.FragmentGetUserNumberBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import java.util.concurrent.TimeUnit

class GetUserNumber : Fragment() {

    private var number: String? = null
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var code: String? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var databaseReference: DatabaseReference? = null

    private var _binding: FragmentGetUserNumberBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGetUserNumberBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")


        binding.btnGenerateOTP.setOnClickListener {
            if (checkNumber()){
                val phoneNumber = binding.countryCodePicker.selectedCountryCodeWithPlus + number
                sendCode(phoneNumber)
            }
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userModel =
                            com.example.quickconnect.models.User(
                                "", "", "",
                                firebaseAuth!!.currentUser!!.phoneNumber!!,
                                firebaseAuth!!.uid!!
                            )

                        databaseReference!!.child(firebaseAuth!!.uid!!).setValue(userModel)
                        activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(R.id.main_container, GetUserData())
                            ?.commit()
                    }
                }

            }

            override fun onVerificationFailed(e: FirebaseException) {
                if(e is FirebaseAuthInvalidCredentialsException)
                    Toast.makeText(context,""+e.message,Toast.LENGTH_SHORT).show()
                else if(e is FirebaseTooManyRequestsException)
                    showToast(requireContext(),""+e.message)

                else
                    showToast(requireContext(),""+e.message)


            }

            override fun onCodeSent(
                verificationCode: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                code = verificationCode
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container,VerifyNumber.newInstance(code!!))
                    .commit()

            }
        }
       return binding.root
    }

    private fun sendCode(phoneNumber: String) {
        callbacks?.let {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                requireActivity(),
                it
            )
        }

    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    private fun checkNumber(): Boolean{
        number = binding.edtNumber.text.toString().trim()

        if(number!!.isEmpty()){
            binding.edtNumber.error= "Field is required"
            return false

        }else if(number!!.length < 10){
            binding.edtNumber.error = "Enter Valid 10 digit Number"
            return false

        }else
            return true


    }

}