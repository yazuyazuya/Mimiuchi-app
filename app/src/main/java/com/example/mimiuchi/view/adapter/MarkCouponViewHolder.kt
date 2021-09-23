package com.example.mimiuchi.view.adapter

import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mimiuchi.R
import kotlinx.android.synthetic.main.list_item_mark_coupon.view.*


class MarkCouponViewHolder(view : View) :  androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    val markCouponImage : ImageView = view.findViewById(R.id.markCouponImage)
    val markCouponDeadline : TextView = view.findViewById(R.id.markCouponDeadline)
    val markCouponName : TextView = view.findViewById(R.id.markCouponName)
    val markCouponPrice : TextView = view.findViewById(R.id.markCouponPrice)

    val permission = view.permission


}