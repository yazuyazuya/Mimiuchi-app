package com.example.mimiuchi.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mimiuchi.R
import com.example.mimiuchi.presenter.activity.LoginContract
import com.example.mimiuchi.presenter.activity.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() ,LoginContract.View{
    override fun loginSuccess() {
        val loginIntent = Intent(this, MainActivity::class.java)
        loginIntent.putExtra("ACT_KEY", "LogIn")
        startActivity(loginIntent)
    }

    override fun showError() {

        err.text = "ユーザー名またはパスワードが違います"

    }

    override lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "ログイン"
        presenter = LoginPresenter(this)

        // 左上の戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var username = useridEditText.text
        var password = passwordEditText.text

        loginButton.setOnClickListener {
            presenter.User(username.toString(),password.toString())
            presenter.start()
        }
    }

    //アクションバーの[戻る]処理
    override fun onSupportNavigateUp(): Boolean {
        finish()
//        val intent = Intent(this, StartActivity::class.java)
//        startActivity(intent)
        return super.onSupportNavigateUp()
    }

}
