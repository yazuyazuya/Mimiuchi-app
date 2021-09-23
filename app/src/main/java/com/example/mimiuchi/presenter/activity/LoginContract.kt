package com.example.mimiuchi.presenter.activity

import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.ShopAll
import com.example.mimiuchi.presenter.BasePresenter
import com.example.mimiuchi.presenter.BaseView

class LoginContract {
    interface Presenter : BasePresenter {
        fun getApiService(): ApiService
        fun apireqest(apiService: ApiService)
        fun getUserId(apiService: ApiService)
        fun User(name:String?,pass:String?)
    }

    interface View : BaseView<Presenter> {
        fun showError()

        fun loginSuccess()
    }
}