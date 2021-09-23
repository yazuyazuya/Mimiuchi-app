package com.example.mimiuchi.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mimiuchi.R
import kotlinx.android.synthetic.main.network_error.*

class NetworkError  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.network_error)
        supportActionBar?.title = "エラー"
        val errActivity = intent.getStringExtra("key")

        button.setOnClickListener {
            when(errActivity){
                "MainActivity" ->{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                "ShopFragment" ->{
                    val intent = Intent(this,
                        ShopDetailsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}