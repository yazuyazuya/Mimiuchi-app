package com.example.mimiuchi

import android.Manifest
import android.content.pm.PackageManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_beacon_test.*
import org.altbeacon.beacon.*
import org.altbeacon.beacon.Beacon
import kotlin.collections.ArrayList

class BeaconTestActivity : AppCompatActivity(), BeaconConsumer {

    // BeaconManager型変数の宣言
    private var beaconManager: BeaconManager? = null

    // ビーコンのフォーマット設定
    private val IBEACON_FORMAT: String = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"

    // Handlerクラスの変数の宣言(追加)
    private val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beacon_test)
        supportActionBar?.title = "ビーコン受信テスト"

        // デバイスのBLE対応チェック
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            finish()
        }

        // API 23以上かのチェック
        if (Build.VERSION.SDK_INT >= 23) {
            // パーミッションの要求
            checkPermission()
        }

        // ビーコンマネージャのインスタンスを生成
        beaconManager = BeaconManager.getInstanceForApplication(this)

        // BeaconManagerの設定
        beaconManager!!.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))

        returnButton.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        // ビーコンサービスの開始
        beaconManager!!.bind(this)
        Log.d("Beacon", "ビーコンサービスの開始")
    }

    override fun onPause() {
        super.onPause()
        // ビーコンサービスの停止
        beaconManager!!.unbind(this)
        Log.d("Beacon", "ビーコンサービスの停止")
    }

    override fun onBeaconServiceConnect() {

        try {
            beaconManager!!.startMonitoringBeaconsInRegion(Region("Beacon_FUN_2019", null, null, null))
            Log.d("Beacon", "onBeaconServiceConnect_try")
        }
        catch (e: RemoteException) {
            e.printStackTrace()
        }

        // モニタリングの通知受取り処理
        beaconManager!!.addMonitorNotifier(object : MonitorNotifier{

            // 領域内に侵入した時に呼ばれる
            override fun didEnterRegion(region: Region) {
                // レンジングの開始
                beaconManager!!.startRangingBeaconsInRegion(region)
                Log.d("Beacon", "ビーコン受信内に侵入")
            }

            // 領域外に退出した時に呼ばれる
            override fun didExitRegion(region: Region) {
                // レンジングの停止
                beaconManager!!.stopRangingBeaconsInRegion(region)
                Log.d("Beacon", "ビーコン受信外に退出")
            }

            override fun didDetermineStateForRegion(i: Int, region: Region) {
                //
            }

        })

        // レンジングの通知受け取り処理
        beaconManager!!.addRangeNotifier(object : RangeNotifier{

            // 範囲内のビーコン情報を受け取る
            override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>, region: Region) {

                Log.d("Beacon", "範囲内のビーコン情報を受け取っている")

                var maxUuid : String?
                var maxMajor : Int?
                var maxMinor : Int?

                // 範囲内の複数のビーコン情報を保持させる変数
                var getUuidList : ArrayList<String> = ArrayList()
                var getMajorList : ArrayList<Int> = ArrayList()
                var getMinorList : ArrayList<Int> = ArrayList()
                var getRssiList : ArrayList<Int> = ArrayList()

                // 範囲内にビーコンがある時の処理
                if (beacons.size > 0) {
                    // 範囲内のビーコンの数だけ繰り返す
                    for (beacon in beacons) {
                        // 複数のビーコン情報をArrayListに分割
                        getUuidList.add(beacon.id1.toString())
                        getMajorList.add(beacon.id2.toInt())
                        getMinorList.add(beacon.id3.toInt())
                        getRssiList.add(beacon.rssi)
                    }

                    // RSSIが最も大きい(最も近くにある)インデックスを取得
                    var indexRssi : Int = getRssiList.indexOf(getRssiList.max())

                    // 取得したインデックスのUUID・major値・minor値を取得
                    maxUuid = getUuidList[indexRssi]
                    maxMajor = getMajorList[indexRssi]
                    maxMinor = getMinorList[indexRssi]

                    Log.d("Beacon_UUID", maxUuid)
                    Log.d("Beacon_Major", maxMajor.toString())
                    Log.d("Beacon_Minor", maxMinor.toString())

//                    if (maxMinor.toString() == "7954") {
//                        finish()
//                    }

                    // Viewの更新
                    handler.post {
                        viewUpdate(maxUuid, maxMajor, maxMinor)
                    }

                }

            }

        })

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

    fun viewUpdate(uuid: String?, major: Int?, minor: Int?) {

        uuidTextView.text = "UUID : " + uuid
        majorTextView.text = "Major : " + major.toString()
        minorTextView.text = "Minor : " + minor.toString()

    }

}
