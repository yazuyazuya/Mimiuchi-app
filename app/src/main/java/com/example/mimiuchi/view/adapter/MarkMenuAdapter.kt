package com.example.mimiuchi.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.MenuData
import com.squareup.picasso.Picasso

class MarkMenuAdapter (private val context : Context,
                       private val markMenuList : List<MenuData>)
                       : androidx.recyclerview.widget.RecyclerView.Adapter<MarkMenuViewHolder>() {

    private var mMenuRecyclerView : RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkMenuViewHolder {
        val markMenuView : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_mark_menu, parent, false)
        return MarkMenuViewHolder(markMenuView)
    }

    override fun onBindViewHolder(holder: MarkMenuViewHolder, position: Int) {
        /**
         * カードにデータを設定
         * 画像
         */
        try {
            Picasso.get()
                //画像URL
                .load("http://35.189.144.179/"+markMenuList[position].menuImgURL)
                .resize(1000, 500) //表示サイズ指定
                .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                .into(holder.markMenuImage) //imageViewに流し込み

        } catch (e: IllegalArgumentException) {
            Picasso.get()
                //画像URL
                .load(R.drawable.m_e_others_501)
                .resize(1000, 500) //表示サイズ指定
                .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                .into(holder.markMenuImage) //imageViewに流し込み
        }

        holder.markMenuDeadline.text = markMenuList[position].limit
        holder.markMenuName.text = markMenuList[position].menuName
        holder.markMenuPrice.text = markMenuList[position].value.toString()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mMenuRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mMenuRecyclerView = null
    }

    override fun getItemCount(): Int {
        return markMenuList.size
    }

}