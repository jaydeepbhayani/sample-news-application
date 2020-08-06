package anetos.software.byjuszyoin.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import anetos.software.byjuszyoin.R
import anetos.software.byjuszyoin.core.BaseActivity
import anetos.software.byjuszyoin.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    //val TAG = javaClass.simpleName

    //private lateinit var viewModel: DataViewModel

    val ANIMATION_DURATION: Long = 1000
    lateinit var slideLeftToRight: Animation
    var slideDownAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )*/

        setContentView(R.layout.activity_splash)
        //viewModel = dataViewModelProvider()

        slideDownAnimation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.item_animation_fall_down
        )
        slideLeftToRight = AnimationUtils.loadAnimation(applicationContext, R.anim.item_animation_from_bottom)
        logoContainer.startAnimation(slideDownAnimation)
        tvCredit.startAnimation(slideLeftToRight)
        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 2000)
    }
}