package com.example.mimiuchi.presenter

import android.util.Log
import com.example.mimiuchi.model.MainRepositoryImpl
import com.example.mimiuchi.model.api.ApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InitPresenter(private val view:InitContract.View):InitContract.Presenter{
    override fun getApiService(): ApiService = MainRepositoryImpl().initApiService()

    override fun apireqest(apiService: ApiService) {
        initDb(apiService)
    }

    override fun initDb(apiService: ApiService) {
        val call = apiService.initDb("")
        call.enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {

                Log.d("fetchItems", "response fail")
                Log.d("fetchItems", "throwable :$t")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (!response.isSuccessful) {
                    Log.d("fetchItems", "not response")
                } else {
                    val responseItem = response.body()

                       // val loginResponse = Gson().fromJson(responseItem, Login::class.java)

                }
            }
        })
    }



    override fun start() {
        apireqest(getApiService())
    }

}