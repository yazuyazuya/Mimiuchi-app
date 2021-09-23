package com.example.mimiuchi.view.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.mimiuchi.R

class MenuViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    // 独自に作成したListener
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
    val menuImage : ImageView = view.findViewById(R.id.markMenuImage)
    val menuDeadline : TextView = view.findViewById(R.id.markMenuDeadline)
    val menuName : TextView = view.findViewById(R.id.markMenuName)
    val menuMarkButton : ImageButton = view.findViewById(R.id.menuMarkButton)
    val menuPrice : TextView = view.findViewById(R.id.markMenuPrice)

    //初期値設定
    init{

    }
}