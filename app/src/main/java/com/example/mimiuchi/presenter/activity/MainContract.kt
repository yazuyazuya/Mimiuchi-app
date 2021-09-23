package com.example.mimiuchi.presenter.activity



import com.example.mimiuchi.model.api.ApiService
import com.example.mimiuchi.model.api.Response.ShopAll
import com.example.mimiuchi.model.api.Response.ShopData
import com.example.mimiuchi.presenter.BasePresenter
import com.example.mimiuchi.presenter.BaseView

/**
 * Contractは、Activity/FragmentとPresenterのやり取りを定義したインターフェースで、
 * Activity/FragmentとPresenterで使うメソッドを宣言するだけです
 */
class MainContract {

    interface Presenter : BasePresenter{
        fun getApiService(): ApiService
        fun apireqest(apiService: ApiService)
        fun getShopAll(apiService: ApiService)
        fun addShopList(responsItem:ShopAll)
        fun visit(apiService: ApiService)
        fun next(list: List<ShopData>)
        fun startVisit()
    }

    /**
     * Activity/FragmentなどのViewモジュールが実装すべきインタフェースで、
     * Presenterから呼ばれることが想定される関数が定義されています。
     * 関数の役割は主にUIの操作です。
     */
    interface View : BaseView<Presenter> {
        fun createActionBar()
        fun showAdapter(list: List<ShopData>)
        fun showError()
        fun show(list: List<ShopData>)
    }

}
