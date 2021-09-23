package com.example.mimiuchi.view.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimiuchi.presenter.Globals
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.CouponData
import com.example.mimiuchi.presenter.fragment.MarkCouponViewModel
import com.example.mimiuchi.view.adapter.MarkCouponAdapter
import kotlinx.android.synthetic.main.fragment_mark_coupon.*

class MarkCouponFragment : Fragment() {

    private lateinit var markCouponViewModel : MarkCouponViewModel

    var list : MutableList<CouponData> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        markCouponViewModel = ViewModelProviders.of(this).get(MarkCouponViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mark_coupon, container, false)
        var globals = activity?.application as Globals
        globals.fragment = "MarkCoupon"
        addListData()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listCreate()
        if(list.size==0){
            nonCmark.setImageResource(R.drawable.nonmark)
            text_mark_coupon.text = "\n\nまだブックマークはありません"
            text_mark_coupon.textSize = 15f
            Cmark_txt.text = "頼みたいメニューなどを\nブックマークしませんか?"
            Cmark_txt.textSize = 15f
            couponLayout.setBackgroundColor(Color.WHITE)
        }else{
            nonCmark.setImageDrawable(null)
            text_mark_coupon.text = "使用クーポン一覧"
            Cmark_txt.text =""

        }
    }

    private fun listCreate() {
        if (this.context != null) {
            markCouponRecyclerView.adapter =
                MarkCouponAdapter(this.context!!, list)
            markCouponRecyclerView.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun addListData() {
        var globals = activity!!.application as Globals
        for ((position, flag) in globals.flagCoupon) {
            if (flag == true) {
                var data = globals.Cdata.get(position)

                if (data != null) {
                    list.add(
                        CouponData(
                            data.couponID,
                            data.couponImgURL,
                            data.couponName,
                            data.limit,
                            data.permission,
                            data.response,
                            data.shopID,
                            data.userID,
                            data.value)
                    )
                }
            }
        }
    }

}
