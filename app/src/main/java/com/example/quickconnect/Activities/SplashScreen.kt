package com.example.quickconnect.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.quickconnect.R
import com.example.quickconnect.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    lateinit var bindingss: ActivitySplashScreenBinding
    private var firebaseAuth: FirebaseAuth? = null
    //private lateinit var appUtil: AppUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingss = ActivitySplashScreenBinding.inflate(layoutInflater)


        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(bindingss.root)

        //animation for splash screen
        applyAnimationToViews(
            bindingss.img,
            translationY = -1600f,
            duration = 1000,
            startDelay = 4000
        )
        applyAnimationToViews(
            bindingss.logo,
            translationY = 1400f,
            duration = 1000,
            startDelay = 4000
        )
        applyAnimationToViews(
            bindingss.appName,
            translationY = 1400f,
            duration = 1000,
            startDelay = 4000
        )
        applyAnimationToViews(
            bindingss.animationlottie,
            translationY = 1400f,
            duration = 1000,
            startDelay = 4000
        )

        firebaseAuth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            firebaseAuth!!.currentUser?.let {
                startActivity(Intent(this,DashboardActivity::class.java))
                finish()
            }
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        },5000)


    }


    private fun applyAnimationToViews(vararg views: View, translationY: Float, duration: Long, startDelay: Long) {
        views.forEach { view ->
            view.animate()
                .translationY(translationY)
                .setDuration(duration)
                .setStartDelay(startDelay)
                .start()
        }
    }
}