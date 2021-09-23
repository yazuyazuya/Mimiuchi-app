package com.example.mimiuchi.view.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.CouponData
import com.example.mimiuchi.model.api.Response.MenuData
import com.example.mimiuchi.presenter.Globals
import com.example.mimiuchi.presenter.fragment.MarkCouponContract
import com.example.mimiuchi.presenter.fragment.MarkCouponPresenter
import com.example.mimiuchi.view.activity.ShopDetailsActivity.Companion.context
import com.example.mimiuchi.view.adapter.MarkCouponAdapter
import com.example.mimiuchi.view.adapter.MarkMenuAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_mark_coupon.*
import kotlinx.android.synthetic.main.fragment_mark_menu.*

 class MarkContentsActivity : AppCompatActivity(), MarkCouponContract.View {



     override fun showError() {

     }

     override fun showDelete() {

     }

     override lateinit var presenter: MarkCouponContract.Presenter



     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_contents)

       presenter = MarkCouponPresenter(this)

        //一つ前の画面に戻るボタンを表示
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //アクションバーのタイトル文字を変更
        supportActionBar?.title = "選択一覧"

        val navView : BottomNavigationView = findViewById(R.id.nav_view_mark)
        val navController = findNavController(R.id.nav_host_fragment_mark)

        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        var globals = application as Globals

             finish()

            val intent = Intent(this, ShopDetailsActivity::class.java)
            startActivity(intent)
            globals.fragment =""
            globals.fragment ="MarkCoupon"//クーポン使用ボタン

        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.actionbar_mark_content_delete, menu)
        // オプションメニュー表示する場合はtrue
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var globals = application as Globals
        val id = item?.itemId
        if (id == R.id.mark_content_delete) {
            // 選択しているリストを解除する

            if( globals.fragment == "MarkMenu") {
                // メニューとクーポンのブックマークのバグをここで直す
               // globals.GlobalsAllInit()

                /**
                 * メニュー注文
                 */
                AlertDialog.Builder(this)
                    .setMessage("メニューを注文済みにしますか")
                    .setPositiveButton("OK"){ dialog, which ->
                        globals.flagMenu = HashMap()
                        for ((position, flag) in globals.flagMenu) {
                            if (flag == true) {
                                globals.flagMenu.put(position, false)
                            }
                        }
                        val list = mutableListOf<MenuData>()
                        markMenuRecyclerView.adapter =
                            MarkMenuAdapter(this, list)
                        markMenuRecyclerView.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                    }
                    .setNegativeButton("キャンセル"){dialog, which ->

                    }
                    .show()


            }
            if(globals.fragment == "MarkCoupon"){
               // globals.GlobalsAllInit()

                /**
                 * クーポン使用
                 */
                presenter = MarkCouponPresenter(this)

                AlertDialog.Builder(this)
                    .setMessage("クーポンを使用済みにしますか")
                    .setPositiveButton("OK"){ dialog, which ->

                        presenter.start()
                        if(globals.fragment == "MarkCoupon"){
                            // globals.GlobalsAllInit()
                            globals.flagCoupon = HashMap()
                            for ((position, flag) in globals.flagCoupon) {
                                if (flag) {

                                    globals.flagCoupon[position] = false
                                }
                            }


                        }
                        globals.couponID=""
                        val list = mutableListOf<CouponData>()
                        markCouponRecyclerView.adapter =
                            MarkCouponAdapter(this, list)
                        markCouponRecyclerView.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    }
                    .setNegativeButton("キャンセル"){dialog, which ->

                    }
                    .show()
            }

        }else{
            return super.onOptionsItemSelected(item)
        }

        return true
    }

}