package com.example.mimiuchi.presenter.fragment

import android.util.Log
import com.example.mimiuchi.model.MainRepositoryImpl
import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.MenuAll
import com.example.mimiuchi.presenter.Globals
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarkCouponPresenter(private val view:MarkCouponContract.View):MarkCouponContract.Presenter {
    override fun getCouponDelete(apiService: ApiService) {
        val globals = Globals.instance as Globals
        var shopId = globals.shopData[globals.tap]!!
        var userId = globals.userID
        var couponId = globals.couponID


        /**
         * クーポンIDは1 2 3(数字 半角スペース 数字)で送信
         */
        val call = apiService.couponDelete(userId,shopId,couponId)
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("fetchItems", "response fail")
                Log.d("fetchItems", "throwable :$t")
                Log.d("MarkCouponPresenter","エラー")

                view.showError()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (!response.isSuccessful) {
                    Log.d("fetchItems", "not response")
                } else {
                    val responseItem = response.body()
                    if (responseItem != null) {
                        /**
                         * クーポン削除できたか取得
                         */
                    }
                }
            }
        })
    }

    override fun getApiService(): ApiService = MainRepositoryImpl().initApiService()

    override fun apireqest(apiService: ApiService) {
        getCouponDelete(apiService)
    }


    override fun start() {
        apireqest(getApiService())
    }


}