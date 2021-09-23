package com.example.mimiuchi.presenter.fragment

import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.MenuAll
import com.example.mimiuchi.model.api.Response.MenuData
import com.example.mimiuchi.presenter.BasePresenter
import com.example.mimiuchi.presenter.BaseView

class MenuContract {

    interface Presenter : BasePresenter {
        fun getApiService(): ApiService
        fun apireqest(apiService: ApiService)
        fun getMenuList(apiService: ApiService)
        fun addMenuList(responseItem:MenuAll)
    }

    interface View : BaseView<Presenter> {
        fun showError()
        fun showAdapter(couponList: List<MenuData>)
    }
}