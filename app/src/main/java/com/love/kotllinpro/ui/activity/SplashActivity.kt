package com.love.kotllinpro.ui.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.love.kotllinpro.R
import com.love.kotllinpro.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.litepal.util.LogUtil

class SplashActivity : BaseActivity() {
    private var countDownTimer: CountDownTimer? = null
    private val TAG = "Splash";
    override fun attachLayoutRes(): Int = R.layout.activity_splash
    override fun initData() {

    }

    override fun initView() {
        initCountDown()
    }

    override fun start() {

    }

    private fun initCountDown() {
        countDownTimer = object : CountDownTimer(3 * 1000, 1000) {
            override fun onFinish() {
                jumpToMain()
            }

            override fun onTick(millisUntilFinished: Long) {
                tv_time.text = "跳过" + millisUntilFinished/1000 + "s"
            }

        }
        countDownTimer?.start()

    }

    fun jumpToMain() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }


}
