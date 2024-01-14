package com.example.quickconnect.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quickconnect.Activities.EditNameActivity
import com.example.quickconnect.Constants.AppConstants
import com.example.quickconnect.Permission.AppPermission
import com.example.quickconnect.R
import com.example.quickconnect.ViewModel.ProfileViewModel
import com.example.quickconnect.databinding.FragmentGetUserNumberBinding
import com.example.quickconnect.databinding.FragmentProfileBinding
import com.example.quickconnect.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class ProfileFragment : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var dialog: AlertDialog
    private lateinit var appPermission: AppPermission
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var currentStatus:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        profileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        appPermission = AppPermission()
        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)

        profileViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(ProfileViewModel::class.java)

        profileViewModel.getUser().observe(viewLifecycleOwner, Observer {userModel ->
            profileBinding.userModel = userModel



            profileBinding.name.text = userModel.displayName

            currentStatus = userModel.status


            profileBinding.cardName.setOnClickListener {
                    val intent = Intent(context,EditNameActivity::class.java)
                    intent.putExtra("displayName",userModel.displayName)
                    startActivityForResult(intent,100)
            }
        })

        profileBinding.imgPickImage.setOnClickListener {
            pickImage()

        }
        profileBinding.imgEditStatus.setOnClickListener {
            getStatusDialog(currentStatus)
        }



       return profileBinding.root
    }

    private fun pickImage() {
        CropImage.activity()
            .setActivityTitle("Crop Image")
            .setActivityMenuIconColor(Color.WHITE)
            .setAllowFlipping(false)
            .setAllowRotation(false)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setGuidelinesThickness(12.0f)
            .setGuidelinesColor(Color.BLACK)
            .setAutoZoomEnabled(false)
            .setBorderLineThickness(12.0f)
            .setBorderLineColor(Color.BLACK)
            .setBorderCornerColor(Color.RED)
            .setBorderCornerThickness(12.0f)
            .setCropMenuCropButtonIcon(com.example.quickconnect.R.drawable.ic_crop)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(requireContext(),this)
    }

    private fun getStatusDialog(currentStatus: String){
        val alertDialog = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dailog_layout,null,false)
        alertDialog.setView(view)

        val edtUserStatus = view.findViewById<EditText>(R.id.edtUserStatus)
        val btnEditStatus = view.findViewById<Button>(R.id.btnEditStatus)
        edtUserStatus.setText(currentStatus)



        btnEditStatus.setOnClickListener {
            val status = edtUserStatus.text.toString()
            if (status.isNotEmpty()) {
                profileViewModel.updateStatus(status)
                dialog.dismiss()
            }
        }

        dialog = alertDialog.create()
        dialog.show()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            100 -> {
                if (data != null) {
                    val userName = data.getStringExtra("displayName")
                    if (userName != null) {
                        profileViewModel.updateName(userName)
                        val editor = sharedPreferences.edit()
                        editor.putString("myName", userName).apply()
                    }

                }
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    uploadImage(result.uri)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    //val error = result?.error
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when(requestCode){
//            100 ->{
//                if (data != null) {
//                    val userName = data.getStringExtra("name")
//                    profileViewModel.updateName(userName!!)
////                    val editor = sharedPreferences.edit()
////                    editor.putString("myName", userName).apply()
//                }
//            }
//            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
//                if (data != null){
//                    val result = CropImage.getActivityResult(data)
//                    if (resultCode == Activity.RESULT_OK){
//                        uploadImage(result.uri)
//                    }
//                }
//            }
//        }
//    }


    private fun uploadImage(imageUri: Uri) {
        storageReference = FirebaseStorage.getInstance().reference
        storageReference.child(firebaseAuth.uid + AppConstants.PATH).putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                val task = taskSnapshot.storage.downloadUrl
                task.addOnCompleteListener {
                    if (it.isSuccessful) {
                        val imagePath = it.result.toString()

                        val editor = sharedPreferences.edit()
                        editor.putString("myImage", imagePath).apply()

                        profileViewModel.updateImage(imagePath)
                    }
                }
            }
    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            AppConstants.STORAGE_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickImage()
                else
                    Toast.makeText(context,"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }*/




}