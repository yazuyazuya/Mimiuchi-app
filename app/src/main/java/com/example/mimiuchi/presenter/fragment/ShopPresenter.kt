package com.example.mimiuchi.presenter.fragment

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mimiuchi.model.MainRepositoryImpl
import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.ShopDetail
import com.example.mimiuchi.model.api.Response.ShopDetails
import com.example.mimiuchi.presenter.Globals
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopPresenter(private val view:ShopContract.View) :ShopContract.Presenter{
    override fun getApiService(): ApiService = MainRepositoryImpl().initApiService()

    override fun apireqest(apiService: ApiService) {
        getShopDetail(apiService)
    }

    override fun getShopDetail(apiService: ApiService) {

        val globals = Globals.instance as Globals
        globals.shopID = globals.shopData[globals.tap]!!
        val call = apiService.shopDetail(globals.shopID)
        call.enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("fetchItems", "response fail")
                Log.d("fetchItems", "throwable :$t")
                view.showError()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (!response.isSuccessful) {
                    // result.name = "no response"
                    Log.d("fetchItems", "not response")
                } else {
                    val responseItem = response.body()
                    if (responseItem != null) {
                        val response = Gson().fromJson(responseItem, ShopDetails::class.java)
                        val responseData= response.data
                        if(response!=null)
                            view.setDataInView(responseData)
                    }

                }
                Log.d("finish","非同期終了")
            }
        })
    }

    override fun addShopDetail(responseItem: ShopDetail) {

    }

    override fun start() {
        apireqest(getApiService())
    }


}