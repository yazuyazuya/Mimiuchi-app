package com.example.mimiuchi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import android.util.Log
import android.view.View
import com.example.mimiuchi.presenter.Globals
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.CouponData
import com.example.mimiuchi.presenter.fragment.CouponContract
import com.example.mimiuchi.presenter.fragment.CouponPresenter
import com.example.mimiuchi.view.adapter.CouponViewHolder
import com.example.mimiuchi.presenter.fragment.CouponViewModel
import com.example.mimiuchi.view.adapter.CouponAdapter
import kotlinx.android.synthetic.main.fragment_coupon.*


class CouponFragment : androidx.fragment.app.Fragment(),
    CouponContract.View,
    CouponViewHolder.ItemClickListener {
    override lateinit var presenter: CouponContract.Presenter


    private lateinit var couponViewModel: CouponViewModel

    var list: MutableList<CouponData> = mutableListOf()
   // var globals= activity?.application as Globals//グローバル変数

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        couponViewModel = ViewModelProviders.of(this).get(CouponViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_coupon, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("lifeCycle", "onViewCreated")

        text_coupon.text = "クーポン一覧\n使用するときはブックマークしてね\n"
        presenter = CouponPresenter(this)
        presenter.start()

    }

    override fun onItemClick(view: View, position: Int) {

    }

    override fun showError() {

    }

    override fun showAdapter(couponList: List<CouponData>) {
        if (this.context != null) {
            couponRecyclerView.adapter = CouponAdapter(
                this.context!!,
                this,
                couponList,
                globals = activity?.application as Globals
            )
            couponRecyclerView.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(
                    this.context,
                    androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                    false
                )
            Log.d("CouponFragment", "adapter作成")
        } else {
            Log.d("CouponFragment", "context=null")
        }
    }
}
