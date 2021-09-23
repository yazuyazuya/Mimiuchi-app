package com.example.mimiuchi.presenter

import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.CouponAll

class InitContract {
    interface Presenter : BasePresenter {
        fun getApiService(): ApiService
        fun apireqest(apiService: ApiService)
        fun initDb(apiService: ApiService)

    }

    interface View : BaseView<Presenter> {

    }
}