package com.example.mimiuchi.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.RemoteException
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.annotation.RequiresApi
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.ShopData
import com.example.mimiuchi.view.activity.TutorialActivity.Companion.showForcibly
import com.example.mimiuchi.presenter.Globals
import com.example.mimiuchi.presenter.activity.MainContract
import com.example.mimiuchi.presenter.activity.MainPresenter
import com.example.mimiuchi.view.adapter.RecyclerAdapter
import com.example.mimiuchi.view.adapter.RecyclerViewHolder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.altbeacon.beacon.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),
    MainContract.View,
    RecyclerViewHolder.ItemClickListener,
    BeaconConsumer {


    override lateinit var presenter: MainContract.Presenter


    // BeaconManager型変数の宣言
    private var beaconManager : BeaconManager? = null

    // ビーコンのフォーマット設定
    private val IBEACON_FORMAT : String = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"
    private val beacon : Beacon? = null

    private val region = Region("unique-id-001", null, null, null)

    // Handlerクラスの変数の宣言(追加)
    private val handler: Handler = Handler()

    lateinit var dataStore: SharedPreferences
    val context = this
    val itemClickListener: RecyclerViewHolder.ItemClickListener = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var global = application as Globals

        setContentView(R.layout.activity_main)

        createActionBar()

        presenter = MainPresenter(this)

        /**
         * API通信開始、リスト作成
         */
        presenter.start()


        val preAct : String? = intent.getStringExtra("ACT_KEY")
        if (preAct == "SignUp") {
            showForcibly(this)
        }

        dataStore = getSharedPreferences("DataStore", Context.MODE_PRIVATE)


        global.GlobalsAllInit()

        // デバイスのBLE対応チェック
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            val snackbar = Snackbar.make(this.mainRecyclerView, "このデバイスはBLE未対応です", Snackbar.LENGTH_LONG)
            snackbar.show()
        }

        // API 23以上かのチェック
        if (Build.VERSION.SDK_INT >= 23) {
            // パーミッションの要求
            checkPermission()
        }

        // ビーコンマネージャのインスタンスを生成
        beaconManager = BeaconManager.getInstanceForApplication(applicationContext)

        // BeaconManagerの設定
        beaconManager?.beaconParsers?.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))


    }

    override fun onResume() {
        super.onResume()
        // ビーコンサービスの開始
        beaconManager?.bind(this)
        Log.d("Beacon", "ビーコンサービスの開始")
    }

    override fun onPause() {
        super.onPause()
        // ビーコンサービスの停止
        beaconManager?.unbind(this)
        Log.d("Beacon", "ビーコンサービスの停止")
    }

    override fun onBeaconServiceConnect() {
        Log.d("Beacon", "onBeaconServiceConnect")
        // 2重登録されるので一旦削除
        beaconManager?.removeAllMonitorNotifiers()
        beaconManager?.removeAllRangeNotifiers()
        beaconManager?.rangedRegions?.forEach{region ->
            beaconManager?.stopRangingBeaconsInRegion(region)
        }
        // BeaconManagerクラスのモニタリング設定
        beaconManager?.addMonitorNotifier(monitorNotifier)
        // BeaconManagerクラスのレンジング設定
        beaconManager?.addRangeNotifier(rangeNotifier)
        startMonitoringBeacons()

    }

    /**
     * ビーコンのモニタリング開始
     */
    private fun startMonitoringBeacons() {
        try {
            beaconManager?.startMonitoringBeaconsInRegion(region)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * ビーコンモニター
     */
    private val monitorNotifier = object : MonitorNotifier {
        override fun didEnterRegion(region: Region) {
            // 領域進入時に実行
            Log.d("Beacon", "didEnterRegion")
        }

        override fun didExitRegion(region: Region) {
            // 領域退出時に実行
            Log.d("Beacon", "didExitRegion")
        }

        override fun didDetermineStateForRegion(i: Int, region: Region) {
            // 領域への侵入/退出のステータスが変化したときに実行
            Log.d("Beacon", "didDetermineStateForRegion i = " + i)

            try {
                if (i == 1) {
                    beaconManager?.startRangingBeaconsInRegion(region)
                } else {
//                    beaconManager?.stopRangingBeaconsInRegion(region)
                    beaconManager?.startRangingBeaconsInRegion(region)
                }
            } catch (e: RemoteException) {
                Log.d("Beacon", "didDetermineStateForRegion e = " + e.message)
            }

        }

    }

    /**
     * ビーコンレンジ
     */
    private val rangeNotifier = RangeNotifier {beacons, region ->
        // 検出したビーコンの情報を全部Logに書き出す
        for (beacon in beacons) {
            Log.d("Beacon", "UUID:" + beacon.id1 + ", major:" + beacon.id2 +
                    ", minor:" + beacon.id3 + ", Distance:" + beacon.distance +
                    ",RSSI" + beacon.rssi + ", TxPower" + beacon.txPower)

            // beacon.id3がMinor値なので、ここで店の分岐できれば良さげかも
            if ( (beacon.id3.toString() == "7954") && (beacon.rssi >= -50) ) {
                // 今はとりあえず、画面遷移するようにしてます
                val globals = application as Globals
                globals.beaconID = beacon.id3.toString()
                globals.shopID = globals.beShopId[beacon.id3.toString()]!!
                globals.shopData[globals.tap]=globals.beShopId[beacon.id3.toString()]!!
                val intent = Intent(this, ShopDetailsActivity::class.java)
                Log.d("ビーコン",beacon.id3.toString())
                startActivity(intent)
            } else if ( (beacon.id3.toString() == "7949") && (beacon.rssi >= -50) ) {
                val globals = application as Globals
                globals.beaconID = "7777"//beacon.id3.toString()
                globals.shopID = globals.beShopId["7777"]!!//.get(beacon.id3.toString())
                globals.shopData[globals.tap]=globals.beShopId["7777"]!!
                // こちらも同じく
                val intent = Intent(this, ShopDetailsActivity::class.java)
                Log.d("ビーコン",beacon.id3.toString())
                startActivity(intent)
            }




        }
    }

    // パーミッションの許可チェック
    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermission() {
        // パーミッション未許可の時
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // パーミッションの許可ダイアログの表示
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        }
    }

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(this, ShopDetailsActivity::class.java)
        var dataStore = getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        var editor = dataStore.edit()
        editor.putInt("tap_position",position)
        editor.apply()

        val globals= application as Globals
        globals.tap = position

       // Log.d("shopID",dataStore.getString("shop_id_"+position.toString(),null))
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu)
        // オプションメニュー表示する場合はtrue
        return false
    }



    override fun createActionBar() {
        supportActionBar?.title = "みみうち"
    }

    override fun showAdapter(list: List<ShopData>) {
        var globals = application as Globals
        //globals.shopData.put()

      //  presenter.next(list)

        mainRecyclerView.adapter =
            RecyclerAdapter(context, itemClickListener, list, globals)
        mainRecyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
    }
    override fun show(list: List<ShopData>) {
//        var globals = application as Globals
//        mainRecyclerView.adapter =
//            RecyclerAdapter(context, itemClickListener, list, globals)
//        mainRecyclerView.layoutManager =
//            androidx.recyclerview.widget.LinearLayoutManager(
//                context,
//                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
//                false
//            )
    }

    override fun showError() {
        val intent = Intent(context, NetworkError::class.java)
        intent.putExtra("key","MainActivity")
        startActivity(intent)
    }


}
