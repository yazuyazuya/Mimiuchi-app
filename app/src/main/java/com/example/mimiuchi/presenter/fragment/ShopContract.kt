package com.example.mimiuchi.presenter.fragment

import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.ShopDetail
import com.example.mimiuchi.presenter.BasePresenter
import com.example.mimiuchi.presenter.BaseView

class ShopContract {

    interface Presenter : BasePresenter{
        fun getApiService(): ApiService
        fun apireqest(apiService: ApiService)
        fun getShopDetail(apiService: ApiService)
        fun addShopDetail(responseItem: ShopDetail)
    }

    interface View : BaseView<Presenter> {
        fun showError()
        fun setDataInView(responseItem: ShopDetail)
    }
}