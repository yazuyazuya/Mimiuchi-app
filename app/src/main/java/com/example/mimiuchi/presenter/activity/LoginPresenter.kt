package com.example.mimiuchi.presenter.activity

import android.util.Log
import com.example.mimiuchi.model.MainRepositoryImpl
import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.Login
import com.example.mimiuchi.presenter.Globals
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val view: LoginContract.View) :LoginContract.Presenter{

    val globals = Globals.instance as Globals
    var userName :String? = ""
    var password :String? = ""

    override fun User(name: String?, pass: String?) {
        userName = name
        password = pass
    }

    override fun getApiService(): ApiService = MainRepositoryImpl().initApiService()

    override fun apireqest(apiService: ApiService) {
        getUserId(apiService)
    }

    override fun getUserId(apiService: ApiService) {

        val call = apiService.login(userName, password)
        call.enqueue(object : Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {

                Log.d("fetchItems", "response fail")
                Log.d("fetchItems", "throwable :$t")

                view.showError()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (!response.isSuccessful) {
                    Log.d("fetchItems", "not response")
                } else {
                    val responseItem = response.body()
                    if (responseItem != null) {
                        val loginResponse = Gson().fromJson(responseItem, Login::class.java)
                        if(loginResponse != null) {
                            val res = loginResponse.res
                            if(res=="not found account") {
                                view.showError()
                            }else {
                                globals.userID = loginResponse.userID
                                view.loginSuccess()
                            }
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