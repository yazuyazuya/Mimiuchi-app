package com.example.mimiuchi.presenter.activity

import android.util.Log
import com.example.mimiuchi.model.MainRepositoryImpl
import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.Login
import com.example.mimiuchi.model.api.Response.New
import com.example.mimiuchi.presenter.Globals
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupPresenter(private val view:SignupContract.View):SignupContract.Presenter {

    val globals = Globals.instance as Globals

    override fun getApiService(): ApiService = MainRepositoryImpl().initApiService()

    override fun apireqest(apiService: ApiService) {
        getUserId(apiService)
    }

    override fun getUserId(apiService: ApiService) {
        val user = view.User()
        val call = apiService.createAcaunt(user.userName,user.password,user.userSex,user.userAge)
        call.enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {

                Log.d("fetchItems", "response fail")
                Log.d("fetchItems", "throwable :$t")

                view.showError()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
               // globals.userID = 2
                if (!response.isSuccessful) {
                    Log.d("fetchItems", "not response")


                } else {
                    val responseItem = response.body()
                    if (responseItem != null) {
                        val createResponse = Gson().fromJson(responseItem, New::class.java)
                        if(createResponse != null) {
                           // globals.userID = 2
                            view.tutorial()
                             globals.userID = createResponse.userID

                        }
                    }
                }
            }
        })
    }

    override fun start() {
        apireqest(getApiService())
    }


}