package com.love.kotllinpro.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.love.kotllinpro.R
import com.love.kotllinpro.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_login

    override fun initData() {

    }

    override fun initView() {
        ll_login.setOnClickListener(onClickListener)
        iv_back.setOnClickListener(onClickListener)
    }

    override fun start() {

    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.ll_login -> {
                jumpToMain()
            }
        }

    }

    private fun jumpToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
