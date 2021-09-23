package com.example.mimiuchi.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.mimiuchi.R

class RecyclerViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemTextView : TextView = view.findViewById(R.id.itemTextView)
    val itemTextView2 : TextView = view.findViewById(R.id.itemTextView2)
    val itemTextView3 : TextView = view.findViewById(R.id.itemTextView3)
    val itemTextView4 : TextView = view.findViewById(R.id.itemTextView4)
    val itemImageView : ImageView = view.findViewById(R.id.itemImageView)


    init {

    }

}