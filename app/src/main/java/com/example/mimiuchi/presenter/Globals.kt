package com.example.mimiuchi.presenter

import android.app.Application
import androidx.fragment.app.Fragment


import com.example.mimiuchi.model.api.Response.CouponData
import com.example.mimiuchi.model.api.Response.MenuData
import com.example.mimiuchi.model.api.Response.ShopData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class Globals :Application() {
    companion object {
        lateinit var instance: Application private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    //グローバルに使用する変数
    var Mdata :HashMap<Int, MenuData> = HashMap()//positionとそのメニューデータ
    var flagMenu:HashMap<Int,Boolean> = HashMap()//positionとブックマーク判定

    var Cdata :HashMap<Int, CouponData> = HashMap()//positionとそのクーポンデータ
    var flagCoupon :HashMap<Int,Boolean> = HashMap()//positionとブックマーク判定

    var shopData :HashMap<Int, Int> = HashMap()//positionとそのショップID

    var allCoupon:List<CouponData> = mutableListOf()//クーポンのデータ
    var allMenu:List<MenuData> = mutableListOf()//メニューのデータ

    var shopID = 1
    var userID = 1
    var couponID = ""
    var beaconID = ""



    var tap = 0

    var fragment = ""

    var beShopId:HashMap<String,Int> = HashMap()


    //全部初期化メソッド
    fun GlobalsAllInit(){
        //全てのメニューID、クーポンIDに対して
        flagMenu = HashMap()
        flagCoupon = HashMap()
        allCoupon = mutableListOf()
        allMenu = mutableListOf()
    }

    //メニュー
    fun setGlobalMenudata(position: Int,data: MenuData){
        Mdata.put(position,data)
    }

    fun setGlobalMenuFlag(position: Int,flag:Boolean){
        flagMenu.put(position,flag)
    }


    //クーポンデータ
    fun setGlobalCoupondata(position: Int,couponData: CouponData){
        Cdata.put(position,couponData)
    }

    //クーポンのブックマーク判定
    fun setGlobalCouponFlag(position: Int,flag:Boolean){
        flagCoupon.put(position,flag)
    }
}