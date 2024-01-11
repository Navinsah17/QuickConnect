package com.example.quickconnect.Fragments

import android.R
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.quickconnect.Activities.DashboardActivity
import com.example.quickconnect.Activities.SplashScreen
import com.example.quickconnect.Constants.AppConstants
import com.example.quickconnect.Permission.AppPermission
import com.example.quickconnect.databinding.FragmentGetUserDataBinding
import com.example.quickconnect.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class GetUserData : Fragment() {

    private var _bindingud: FragmentGetUserDataBinding? = null
    private val bindingud get() = _bindingud!!

    private var image: Uri? = null
    private lateinit var username: String
    private lateinit var status: String
    private lateinit var imageUrl: String
    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var storageReference: StorageReference? = null

    private val READ_EXTERNAL_STORAGE_REQUEST = 100

    private lateinit var appPermission: AppPermission


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingud = FragmentGetUserDataBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        storageReference = FirebaseStorage.getInstance().reference



        bindingud.imgPickImage.setOnClickListener {
            pickImage()

        }
        bindingud.btnDataDone.setOnClickListener {
            if (checkData())
                uploadData(username,status,image!!)
            //Toast.makeText(context,"Data Uploaded",Toast.LENGTH_SHORT).show()
        }


        return bindingud.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                image = result.uri
                bindingud.imgUser.setImageURI(image)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //val error = result?.error
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun checkData(): Boolean {
        username = bindingud.edtUserName.text.toString().trim()
        status = bindingud.edtUserStatus.text.toString().trim()

        if (username.isEmpty()) {
            bindingud.edtUserName.error = "Filed is required"
            return false

        } else if (status.isEmpty()) {
            bindingud.edtUserStatus.error = "Filed is required"
            return false

        } else if (image == null) {
            Toast.makeText(context, "Image required", Toast.LENGTH_SHORT).show()
            return false

        } else return true
    }
//    private fun uploadData(name: String, status: String, image: Uri) = kotlin.run {
//        storageReference!!.child(firebaseAuth!!.uid + AppConstants.PATH).putFile(image)
//            .addOnSuccessListener {
//                val task = it.storage.downloadUrl
//                task.addOnCompleteListener { uri ->
//                    imageUrl = uri.result.toString()
//                    val map = mapOf(
//                        "name" to name,
//                        "status" to status,
//                        "image" to imageUrl
//                    )
//                    databaseReference!!.child(firebaseAuth!!.uid!!).updateChildren(map)
//                    Toast.makeText(context,"Data Uploade",Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(context, SplashScreen::class.java))
//                    requireActivity().finish()
////
//                }
//            }
//    }
    private fun uploadData(name: String, status: String, image: Uri) = kotlin.run {
        storageReference!!.child(firebaseAuth!!.uid + AppConstants.PATH).putFile(image)
            .addOnSuccessListener {
                val task = it.storage.downloadUrl
                task.addOnCompleteListener { uri ->
                    imageUrl = uri.result.toString()
                    val currentUser = firebaseAuth!!.currentUser
                    /*val map = mapOf(
                        "name" to name,
                        "status" to status,
                        "image" to imageUrl
                    )
                    databaseReference!!.child(firebaseAuth!!.uid!!).updateChildren(map)
                    Toast.makeText(context,"Data Uploaded",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, DashboardActivity::class.java))
                    requireActivity().finish()*/
                    val user = User(
                        uid = firebaseAuth!!.uid!!,
                        displayName = name,
                        imageUrl = imageUrl,
                        emailId = "",
                        phoneNo = currentUser?.phoneNumber.orEmpty(),
                        status = status
                    )


                    databaseReference!!.child(firebaseAuth!!.uid!!).setValue(user)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Data Uploaded", Toast.LENGTH_SHORT).show()

                            startActivity(Intent(context, DashboardActivity::class.java))
                            requireActivity().finish()
                        }
                        .addOnFailureListener { exception ->
                            // Handle the failure and log or display the error message
                            //Log.e("UploadData", "Upload failed: ${exception.message}")
                            Toast.makeText(context, "Uploading Failed...", Toast.LENGTH_SHORT).show()
                        }

                }
            }
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


}
