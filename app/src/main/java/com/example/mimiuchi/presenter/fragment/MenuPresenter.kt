package com.example.mimiuchi.presenter.fragment

import android.util.Log
import android.view.Menu
import com.example.mimiuchi.model.MainRepositoryImpl
import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.MenuAll
import com.example.mimiuchi.model.api.Response.MenuData
import com.example.mimiuchi.model.api.Response.Next
import com.example.mimiuchi.presenter.Globals
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuPresenter(private val view:MenuContract.View) :MenuContract.Presenter{

    var globals = Globals.instance as Globals

    override fun apireqest(apiService: ApiService) {
        getMenuList(apiService)
    }

    override fun getMenuList(apiService: ApiService) {


        val call = apiService.menuAll(globals.userID,globals.shopID)
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("fetchItems", "response fail")
                Log.d("fetchItems", "throwable :$t")
                Log.d("MenuPresenter","エラー")

                view.showError()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (!response.isSuccessful) {
                    Log.d("fetchItems", "not response")
                } else {
                    val responseItem = response.body()
                    var list = mutableListOf<MenuData>()
                    var menuAll:MenuAll = MenuAll(list)
                    if (responseItem != null) {
                        val response = Gson().fromJson(responseItem,MenuAll::class.java)
                        if(response.data!=null)
                            addMenuList(response)
                    }
                }
            }
        })

    }

    override fun addMenuList(responseItem: MenuAll) {
        val menuList = responseItem.data

        if(menuList.isNotEmpty())
        view.showAdapter(menuList)
    }



    override fun start() {
        apireqest(getApiService())
    }

    override fun getApiService() : ApiService = MainRepositoryImpl().initApiService()

}