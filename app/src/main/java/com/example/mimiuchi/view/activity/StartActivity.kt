package com.example.mimiuchi.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mimiuchi.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        //スプラッシュ画面
//        Thread.sleep(3000)
//        setTheme(R.style.AppTheme)

        loginTextView.text = "このアプリを利用することで、\nいろんな飲食店の常連客になりましょう！"

        startSignupButton.setOnClickListener {
            // 新規登録画面へと遷移する
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        startLoginButton.setOnClickListener {
            // ログイン画面へと遷移する
            val intent = Intent(this, LoginActivity::class.java)
//            intent.putExtra("LogIn", true)
            startActivity(intent)
        }

    }
}
