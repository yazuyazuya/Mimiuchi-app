package com.example.mimiuchi.presenter.fragment

import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.CouponAll
import com.example.mimiuchi.model.api.Response.CouponData
import com.example.mimiuchi.presenter.BasePresenter
import com.example.mimiuchi.presenter.BaseView
import com.example.mimiuchi.presenter.activity.MainContract

class CouponContract {

    interface Presenter : BasePresenter {
        fun getApiService(): ApiService
        fun apireqest(apiService: ApiService)
        fun getCouponList(apiService: ApiService)
        fun addCouponList(responseItem:CouponAll)
    }

    interface View : BaseView<Presenter> {
        fun showError()
        fun showAdapter(couponList: List<CouponData>)
    }
}