package com.example.mimiuchi.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.mimiuchi.R

class MarkMenuViewHolder(view: View) :  androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    val markMenuImage : ImageView = view.findViewById(R.id.markMenuImage)
    val markMenuDeadline : TextView = view.findViewById(R.id.markMenuDeadline)
    val markMenuName : TextView = view.findViewById(R.id.markMenuName)
    val markMenuPrice : TextView = view.findViewById(R.id.markMenuPrice)

}