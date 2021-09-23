package com.example.mimiuchi.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.ShopData
import com.example.mimiuchi.presenter.Globals
import com.squareup.picasso.Picasso

class RecyclerAdapter (private val context: Context,
                       private val itemClickListener: RecyclerViewHolder.ItemClickListener,
                       private val itemList:List<ShopData>,
                       private val globals:Globals
                       ) : androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerViewHolder>() {

    private var mRecyclerView : androidx.recyclerview.widget.RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.list_item, parent, false)

        mView.setOnClickListener {view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }

        return RecyclerViewHolder(mView)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.let {

            globals.shopData.put(position,itemList[position].shopID!!)


            it.itemTextView.text = itemList[position].shopName
            it.itemTextView2.text = itemList[position].category[0]

//            it.itemTextView3.text =itemList[position].visitCount.toString() + "回来店"
//            it.itemTextView4.text = itemList[position].LastVisitedHours.toString() + "回"

            if(itemList[position].beaconID=="7954") {
                it.itemTextView3.text = "11回来店"//itemList[position].visitCount.toString() + "回来店"
                it.itemTextView4.text =
                    "次の解放まで0回" //+ itemList[position].LastVisitedHours.toString() + "回"
            }else if(itemList[position].beaconID=="7949"){
                it.itemTextView3.text = "0回来店"
                it.itemTextView4.text =
                    "次の解放まで10回"
            }else{
                it.itemTextView3.text = "0回来店"
                it.itemTextView4.text =
                    "次の解放まで10回"
            }


            if(itemList[position].pictureID==null) {

                Picasso.get()
                    //画像URL
                    .load(R.drawable.m_e_others_501)
                    .resize(1000, 500) //表示サイズ指定
                    .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                    .into(it.itemImageView) //imageViewに流し込み
            }else {
                try {
                    Picasso.get()
                        //画像URL
                        .load("http://35.189.144.179/"+itemList[position].pictureID)
                        .resize(1000, 500) //表示サイズ指定
                        .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                        .into(it.itemImageView) //imageViewに流し込み
                }catch(e:IllegalArgumentException){
                    Picasso.get()
                        //画像URL
                        .load(R.drawable.m_e_others_501)
                        .resize(1000, 500) //表示サイズ指定
                        .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                        .into(it.itemImageView) //imageViewに流し込み
                }
            }
        }
    }
}