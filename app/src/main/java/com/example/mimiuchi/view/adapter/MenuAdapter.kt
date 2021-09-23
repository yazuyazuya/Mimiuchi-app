package com.example.mimiuchi.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mimiuchi.presenter.Globals
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.MenuData
import com.squareup.picasso.Picasso


class MenuAdapter(private val context: Context,
                  private val itemClickListener: MenuViewHolder.ItemClickListener,
                  private val list: List<MenuData>,
                  val globals: Globals
) : androidx.recyclerview.widget.RecyclerView.Adapter<MenuViewHolder>() {

    private var mRecyclerView: androidx.recyclerview.widget.RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        var layoutInflater = LayoutInflater.from(context)
        val menuView : View = layoutInflater.inflate(R.layout.list_item_menu, parent, false)
        menuView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return MenuViewHolder(menuView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        holder.menuDeadline.text = "有効期限："+list[position].limit


        holder.menuName.text = list[position].menuName
        holder.menuPrice.text = "¥"+list[position].value.toString()+"  "

        try {
            Picasso.get()
                //画像URL
                .load("http://35.189.144.179/"+list[position].menuImgURL)
                .resize(1000, 500) //表示サイズ指定
                .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                .into(holder.menuImage) //imageViewに流し込み

        } catch (e: IllegalArgumentException) {
            Picasso.get()
                //画像URL
                .load(R.drawable.m_e_others_501)
                .resize(1000, 500) //表示サイズ指定
                .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                .into(holder.menuImage) //imageViewに流し込み
        }



        holder.menuMarkButton.setImageResource(R.drawable.ic_bookmark_border_24dp)
        holder.menuMarkButton.setOnClickListener {
            if (globals.flagMenu.get(position)==true) {
                holder.menuMarkButton.setImageResource(R.drawable.ic_bookmark_border_24dp)
                globals.setGlobalMenuFlag(position, false)
            } else {
                holder.menuMarkButton.setImageResource(R.drawable.ic_bookmark_24dp)
                globals.setGlobalMenuFlag(position, true)
            }
        }
       /* holder.itemView.setOnClickListener {
            listener.onClickMenu(it, list[position])
        }*/
        if (globals.flagMenu.get(position)==false) {
            holder.menuMarkButton.setImageResource(R.drawable.ic_bookmark_border_24dp)
        }
        if(globals.flagMenu.get(position)==true){
            holder.menuMarkButton.setImageResource(R.drawable.ic_bookmark_24dp)
        }
        globals.setGlobalMenudata(position,list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ListListener {
        fun onClickMenu(tappedView: View, menuModel: MenuData)
    }

    override fun onAttachedToRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }

}
