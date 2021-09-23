package com.example.mimiuchi.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mimiuchi.presenter.Globals
import com.google.android.material.snackbar.Snackbar
import com.example.mimiuchi.R
import com.example.mimiuchi.model.api.Response.MenuData
import com.example.mimiuchi.presenter.fragment.MenuContract
import com.example.mimiuchi.presenter.fragment.MenuPresenter
import com.example.mimiuchi.presenter.fragment.MenuViewModel
import com.example.mimiuchi.view.adapter.MenuAdapter
import com.example.mimiuchi.view.adapter.MenuViewHolder
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : androidx.fragment.app.Fragment(),MenuViewHolder.ItemClickListener,MenuContract.View {


    override lateinit var presenter: MenuContract.Presenter

    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuViewModel =
            ViewModelProviders.of(this).get(MenuViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_menu, container, false)
        val textView: TextView = root.findViewById(R.id.text_menu)

        menuViewModel.text.observe(this, Observer {
            textView.text = it
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MenuPresenter(this)
        presenter.start()

        /*val adapter = MenuAdapter(
           // createDataList(),
            object : MenuAdapter.ListListener {
                override fun onClickMenu(tappedView: View, menuModel: MenuData) {
                    this@MenuFragment.onClickMenu(tappedView, menuModel)
                }
            },
            globals = activity?.application as Globals
        )*/

        var dataStore = activity!!.getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        var editor = dataStore.edit()

    }



    fun onClickMenu(tappedView: View, menuModel: MenuData) {
        Snackbar.make(tappedView, "you tapped ${menuModel.menuName}.", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onItemClick(view: View, position: Int) {

    }

    override fun showError() {
    }

    override fun showAdapter(menuList: List<MenuData>) {
        val recyclerView = menuRecyclerView
        if (this.context != null) {
            val adapter = MenuAdapter(
                this.context!!,
                this,
                menuList,
                globals = activity?.application as Globals
            )
            menuRecyclerView.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(
                    this.context,
                    androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                    false
                )
            Log.d("MenuFragment", "adapter作成")
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
            recyclerView.adapter = adapter
        } else {
            Log.d("MenuFragment", "context=null")
        }
    }
}