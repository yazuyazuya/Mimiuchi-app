package com.example.mimiuchi.view.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import com.example.mimiuchi.R
import retrofit2.Retrofit


open class ShopDetailsActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit

    companion object {
        var context = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_details)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)



        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        supportActionBar?.title = "店舗詳細"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//戻る

    }


    //アクションバーの[戻る]処理
    override fun onSupportNavigateUp(): Boolean {
        finish()
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu)
        // オプションメニュー表示する場合はtrue
        return true
    }

    // メニューをマークされた時の処理 ここでは右上のアイコン
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_tv) {
            finish()
            val intent = Intent(this, MarkContentsActivity::class.java)
            //intent.putExtra("hoge", "hoge")
            startActivity(intent)

        }else{
            return super.onOptionsItemSelected(item)
        }
        return true
    }

    public override fun onSaveInstanceState(outState: Bundle) {
//        var position = intent.getStringExtra ("position").toInt()
        // outState.putInt("KEY", position)
        super.onSaveInstanceState(outState)
    }



}

