package com.example.mimiuchi.presenter.fragment

import android.util.Log
import com.example.mimiuchi.model.MainRepositoryImpl
import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.CouponAll
import com.example.mimiuchi.model.api.Response.CouponData
import com.example.mimiuchi.model.api.Response.ShopAll
import com.example.mimiuchi.model.api.Response.ShopDetail
import com.example.mimiuchi.presenter.Globals
import com.example.mimiuchi.presenter.activity.MainContract
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CouponPresenter(private val view:CouponContract.View) :CouponContract.Presenter{

    val globals = Globals.instance as Globals

    override fun getApiService() : ApiService = MainRepositoryImpl().initApiService()

    override fun apireqest(apiService: ApiService) {
        getCouponList(apiService)
    }

    override fun getCouponList(apiService: ApiService) {
        val call = apiService.couponAll(globals.userID,globals.shopID)
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("fetchItems", "response fail")
                Log.d("fetchItems", "throwable :$t")
                Log.d("CouponPresenter","エラー")

                view.showError()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (!response.isSuccessful) {
                    Log.d("fetchItems", "not response")
                } else {
                    val responseItem = response.body()
                    if (responseItem != null) {
                        val couponAll = Gson().fromJson(responseItem,CouponAll::class.java)
                        if(couponAll!=null)
                        addCouponList(couponAll)
                    }
                }
            }
        })
    }

    override fun addCouponList(responseItem: CouponAll) {

        val couponList = responseItem.data

        Log.d("クーポン",couponList[0].response)
        if(couponList.isNotEmpty()&&couponList[0].response=="ヒットしました。") {
            view.showAdapter(couponList)
        }else{

        }
    }

    override fun start() {
        apireqest(getApiService())
    }
}