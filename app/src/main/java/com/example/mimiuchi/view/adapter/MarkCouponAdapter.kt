package com.example.mimiuchi.view.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.CouponData
import com.example.mimiuchi.presenter.Globals
import com.example.mimiuchi.presenter.fragment.MarkCouponContract
import com.example.mimiuchi.presenter.fragment.MarkCouponPresenter
import com.example.mimiuchi.view.activity.MarkContentsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_mark_coupon.*

class MarkCouponAdapter(private val context : Context,
                        private val markCouponList : List<CouponData>
                        )
                        : androidx.recyclerview.widget.RecyclerView.Adapter<MarkCouponViewHolder>()
   {

    private var mCouponRecyclerView : RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkCouponViewHolder {
        val markCouponView : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_mark_coupon, parent, false)
        return MarkCouponViewHolder(markCouponView)
    }

    override fun onBindViewHolder(holder: MarkCouponViewHolder, position: Int) {
       // holder.markCouponImage.setImageResource(R.mipmap.ic_launcher)
        try {
            Picasso.get()
                //画像URL
                .load("http://35.189.144.179/"+markCouponList[position].couponImgURL)
                .resize(1000, 500) //表示サイズ指定
                .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                .into( holder.markCouponImage) //imageViewに流し込み

        } catch (e: IllegalArgumentException) {
            Picasso.get()
                //画像URL
                .load(R.drawable.m_e_others_501)
                .resize(1000, 500) //表示サイズ指定
                .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                .into( holder.markCouponImage) //imageViewに流し込み
        }
        holder.markCouponDeadline.text = markCouponList[position].limit
        holder.markCouponName.text = markCouponList[position].couponName
        holder.markCouponPrice.text = markCouponList[position].value.toString()
        holder.permission.text = markCouponList[position].permission


            val globals = Globals.instance as Globals
        if(globals.couponID ==""){
            globals.couponID = markCouponList[position].couponID.toString()
        }else{
            globals.couponID += ""+markCouponList[position].couponID.toString()
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mCouponRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mCouponRecyclerView = null
    }

    override fun getItemCount(): Int {
        return markCouponList.size
    }

}