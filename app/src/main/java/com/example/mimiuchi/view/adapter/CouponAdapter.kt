package com.example.mimiuchi.view.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mimiuchi.presenter.Globals
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.CouponData
import com.squareup.picasso.Picasso


class CouponAdapter (private val context: Context,
                     private val itemClickListener: CouponViewHolder.ItemClickListener,
                     private val list:List<CouponData>,
                     val globals: Globals
) : androidx.recyclerview.widget.RecyclerView.Adapter<CouponViewHolder>() {

    private var cRecyclerView: androidx.recyclerview.widget.RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        Log.d("Adapter", "onCreateViewHolder")
        var layoutInflater = LayoutInflater.from(context)
        var cView = layoutInflater.inflate(R.layout.list_item_coupon, parent, false)
        cView.setOnClickListener { view ->
            cRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return CouponViewHolder(cView)
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        Log.d("Adapter", "onBindViewHolder")

        holder.let {


            //値をセット

            /**
             * 画像
             */
            try {
                Picasso.get()
                    //画像URL
                    .load("http://35.189.144.179/"+list[position].couponImgURL)
                    .resize(1000, 500) //表示サイズ指定
                    .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                    .into( it.itemCouponImage) //imageViewに流し込み

            } catch (e: IllegalArgumentException) {
                Picasso.get()
                    //画像URL
                    .load(R.drawable.m_e_others_501)
                    .resize(1000, 500) //表示サイズ指定
                    .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                    .into( it.itemCouponImage) //imageViewに流し込み
            }
            /**
             * クーポン名
             */
            it.itemCouponName.text = list[position].couponName

            /**
             * 有効期限
             */
            it.itemDeadline.text = "有効期限："+list[position].limit

            /**
             * 価格
             */
            it.itemPrice.text = "¥"+list[position].value.toString()+"  "

            /**
             * クーポンの説明（このメニューと一緒し使用できます、など）
             */
            it.permission.text = list[position].permission

            /**
             * ブックマーク
             */
            it.itemMark.setImageResource(R.drawable.ic_bookmark_border_24dp)
            it.itemMark.setOnClickListener {
                if (globals.flagCoupon.get(position)==true) {
                    holder.itemMark.setImageResource(R.drawable.ic_bookmark_border_24dp)
                    globals.setGlobalCouponFlag(position, false)
                } else {
                    holder.itemMark.setImageResource(R.drawable.ic_bookmark_24dp)
                    globals.setGlobalCouponFlag(position, true)
                }
            }
            globals.setGlobalCoupondata(position,list.get(position))
            if (globals.flagCoupon.get(position)==false) {
                holder.itemMark.setImageResource(R.drawable.ic_bookmark_border_24dp)
            }
            if(globals.flagCoupon.get(position)==true){
                holder.itemMark.setImageResource(R.drawable.ic_bookmark_24dp)
            }
            globals.setGlobalCoupondata(position, list[position])
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        cRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        cRecyclerView = null
    }

    override fun getItemCount(): Int {
        Log.d("Adapter", "getItemCount")
        return list.size//itemList.size 引数の
    }

}