package com.example.mimiuchi.presenter.fragment

import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.CouponAll
import com.example.mimiuchi.model.api.Response.CouponData
import com.example.mimiuchi.presenter.BasePresenter
import com.example.mimiuchi.presenter.BaseView

class MarkCouponContract {
    interface Presenter : BasePresenter {
        fun getApiService(): ApiService
        fun apireqest(apiService: ApiService)
        fun getCouponDelete(apiService: ApiService)

    }

    interface View : BaseView<Presenter> {
        fun showError()
        fun showDelete()
    }
}