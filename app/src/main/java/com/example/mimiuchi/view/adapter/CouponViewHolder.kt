package com.example.mimiuchi.view.adapter


import android.view.View
import android.widget.TextView
import com.example.mimiuchi.R
import kotlinx.android.synthetic.main.list_item_coupon.view.*

open class CouponViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    // 独自に作成したListener
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    var itemDeadline = view.markCouponDeadline
    var itemCouponImage = view.markCouponImage
    var itemCouponName = view.markCouponName
    var itemMark = view.couponMarkBottun
    var itemPrice = view.markCouponPrice
    var mark : Boolean = false
    var permission = view.permission

    init {
        // layoutの初期設定するときはココ
        itemDeadline.text = ""
    //   itemCouponImage.setImageResource(R.drawable.ic_home_black_24dp)
        itemMark.setImageResource(R.drawable.ic_bookmark_border_24dp)
        itemCouponName.text = ""
        itemPrice.text = ""
        mark = false
    }
}