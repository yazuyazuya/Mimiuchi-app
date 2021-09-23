package com.example.mimiuchi.presenter.activity



import android.util.Log
import com.example.mimiuchi.model.MainRepositoryImpl
import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.CouponAll
import com.example.mimiuchi.model.api.Response.Next

import com.example.mimiuchi.model.api.Response.ShopAll
import com.example.mimiuchi.model.api.Response.ShopData
import com.example.mimiuchi.presenter.Globals
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    override fun next(list: List<ShopData>) {
        next(getApiService(),list)
    }


    val globals = Globals.instance as Globals

    override fun start() {
        apireqest(getApiService())
    }

    override fun startVisit(){
        visit(getApiService())
    }

    fun next(apiService: ApiService,list: List<ShopData>){
        val shop = list
        var i =0
        for(item in list) {
            var shopId = item.shopID!!

            val call = apiService.nextMenu(globals.userID, shopId)

            call.enqueue(object : Callback<Next> {
                override fun onFailure(call: Call<Next>, t: Throwable) {
                    Log.d("fetchItems", "response fail")
                    Log.d("fetchItems", "throwable :$t")
                    Log.d("MenuPresenter", "エラー")

                    view.showError()
                }

                override fun onResponse(call: Call<Next>, response: Response<Next>) {
                    if (!response.isSuccessful) {
                        Log.d("fetchItems", "not response")
                    } else {
                        val responseItem = response.body()

                        if (responseItem != null) {

                            val count = responseItem.count
                            var next = responseItem.next
                            if (next < 0) {
                                next = 0
                            }
                            try {
                                shop[i].visitCount = count
                                shop[i].LastVisitedHours = next
                            }catch(e: java.lang.IndexOutOfBoundsException){

                            }

                            Log.d("i=",i.toString())
                            if(i==list.size-1) {
                                view.show(shop)
                                Log.d("終わり", "GET終了")
                            }


                        }
                    }
                }
            })
            i++
        }
    }

    override fun apireqest(apiService: ApiService) {
        getShopAll(apiService)
    }

    override fun getShopAll(apiService: ApiService) {

        val call = apiService.shopAll(globals.userID)

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
                        val res = Gson().fromJson(responseItem, ShopAll::class.java)
                        var i = 0
                        if(res != null) {
                            for(item in res.data) {
                                var shopId = item.shopID!!
                            }

                                addShopList(res)
                        }
                    }
                }
            }
        })

    }

    override fun addShopList(responsItem: ShopAll) {
        var list: MutableList<ShopData> = mutableListOf()
        val shopList = responsItem.data
        var i =0
        if(shopList!=null) {
            for (item in shopList) {
                val id = item.shopID
                val name = item.shopName
                val category = item.category
                val imageUrl = item.pictureID
                val count = item.visitCount
                val nexttime = item.LastVisitedHours
                val beaconId = item.beaconID
                val res = item.response

                globals.beShopId.put(beaconId!!,id!!)


                if(res!="店の情報がありません。") {
                    list.add(
                        ShopData(id, name,  imageUrl, category, count, nexttime, beaconId, res)
                    )
                }else{

                    list.add(
                        ShopData(id, res,  imageUrl, category, count, nexttime, beaconId, res)
                    )
                }
                //   editor.putString("shop_id_"+i.toString(),id)
                i++
            }
        }else{
            var categoryList = mutableListOf<String>()
            list.add(
                ShopData(0, "name", "img", categoryList, 0, 0,"0","")
            )
        }
        view.showAdapter(list)

    }
    override fun getApiService(): ApiService = MainRepositoryImpl().initApiService()

    override fun visit(apiService: ApiService) {


        val shopID = globals.beShopId.get(globals.beaconID)!!
        val call = apiService.shopComming(globals.userID,shopID)
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
                        Log.d("fetchItems", "来店回数増加")
                    }
                }
            }
        })
    }
}