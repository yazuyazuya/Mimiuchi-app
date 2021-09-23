package com.example.mimiuchi.presenter.activity

import com.example.mimiuchi.model.User
import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.presenter.BasePresenter
import com.example.mimiuchi.presenter.BaseView

class SignupContract {
    interface Presenter : BasePresenter {
        fun getApiService(): ApiService
        fun apireqest(apiService: ApiService)
        fun getUserId(apiService: ApiService)

    }

    interface View : BaseView<Presenter> {
        fun showError()
        fun tutorial()
        fun User(): User
    }
}