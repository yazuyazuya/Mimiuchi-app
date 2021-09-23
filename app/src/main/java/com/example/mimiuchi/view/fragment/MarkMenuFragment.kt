package com.example.mimiuchi.view.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mimiuchi.presenter.Globals
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.MenuData
import com.example.mimiuchi.presenter.fragment.MarkMenuViewModel
import com.example.mimiuchi.view.adapter.MarkMenuAdapter
import kotlinx.android.synthetic.main.fragment_mark_menu.*
import kotlinx.android.synthetic.main.fragment_mark_menu.menuLayout

class MarkMenuFragment : Fragment() {

    private lateinit var markMenuViewModel : MarkMenuViewModel

    var list: MutableList<MenuData> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        markMenuViewModel = ViewModelProviders.of(this).get(MarkMenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mark_menu, container, false)
        var globals = activity?.application as Globals
        globals.fragment = "MarkMenu"
        addListData()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listCreate()

       if(list.size==0){
            nonMmark.setImageResource(R.drawable.nonmark)
            text_mark_menu.text = "\n\nまだブックマークはありません"
            text_mark_menu.textSize = 15f
            Mmark_txt.text = "頼みたいメニューなどを\nブックマークしませんか?"
            Mmark_txt.textSize = 15f

            menuLayout.setBackgroundColor(Color.WHITE)
        }else{
            nonMmark.setImageDrawable(null)
            text_mark_menu.text = "注文メニュー一覧"
            Mmark_txt.text =""//
        }
    }

    private fun listCreate() {
        if (this.context != null) {
            markMenuRecyclerView.adapter =
                MarkMenuAdapter(this.context!!, list)
            markMenuRecyclerView.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun addListData(){
        var globals=activity!!.application as Globals
    for ((position,flag) in globals.flagMenu){
        if(flag==true){
            var data = globals.Mdata[position]
            if(data!=null) {
                list.add(
                   MenuData(data.couponID, data.limit, data.menuImgURL, data.menuName, data.releaseCount, data.response, data.shopID, data.value)
                )
            }
        }
    }
    }


}
