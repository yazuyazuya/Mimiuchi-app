package com.example.mimiuchi.view.fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.util.Log
import com.example.mimiuchi.*
import kotlinx.android.synthetic.main.fragment_shop.*
import com.squareup.picasso.Picasso
import android.text.style.UnderlineSpan
import android.text.SpannableString
import com.example.mimiuchi.model.api.Response.ShopDetail
import com.example.mimiuchi.presenter.fragment.ShopContract
import com.example.mimiuchi.presenter.fragment.ShopPresenter
import com.example.mimiuchi.presenter.fragment.ShopViewModel
import com.example.mimiuchi.view.activity.NetworkError


 class ShopFragment  : androidx.fragment.app.Fragment(),ShopContract.View {

     override lateinit var presenter: ShopContract.Presenter

     private lateinit var shopViewModel: ShopViewModel


    var position = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         shopViewModel= ViewModelProviders.of(this).get(ShopViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_shop, container, false)
        val textView: TextView = root.findViewById(R.id.text_shop)
        shopViewModel.text.observe(this, Observer {
            textView.text = it
            remain.text = "次まで"
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dataStore = activity!!.getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        //var editor = dataStore.edit()
        position = dataStore.getInt("tap_position",0)
       // var id = dataStore.getString("shop_id_"+position.toString(),"no")
        Log.d("エディタ",dataStore.getString("shop_id_"+position.toString(),"no"))

        presenter = ShopPresenter(this)
        presenter.start()


    }

     override fun showError() {
         val intent = Intent(context, NetworkError::class.java)
         intent.putExtra("key","ShopFragment")
         startActivity(intent)
     }

     override fun setDataInView(responseItem: ShopDetail) {

             shopName.text = responseItem.shopName

             shopCategory.text = responseItem.category[0]

         if(responseItem.shopID==1) {
             visits.text = "11回来店"//responseItem.visitCount.toString()+"回来店"
             remain.text = "次まで0回"
         }else if(responseItem.shopID==2){
             visits.text = "0回来店"//responseItem.visitCount.toString()+"回来店"
             remain.text = "次まで10回"
         }else{
             visits.text = "0回来店"//responseItem.visitCount.toString()+"回来店"
             remain.text = "次まで10回"
         }
             phone.text = responseItem.phoneNumber
             open.text = responseItem.openHours
             adress.text = responseItem.streetAddress
             if(responseItem.streetAddress!=null) {
                 var ad = responseItem.streetAddress.replace("　","").replace(" ","")

                 //var shop = responseItem.shopName?.replace("　","")?.replace(" ","")

                 val spanStr = SpannableString(adress.text)
                 spanStr.setSpan(UnderlineSpan(), 0, adress.text.length, 0)
                 adress.setText(spanStr)
                 adress.setOnClickListener{
                     var uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + ad)
                     var i =  Intent(Intent.ACTION_VIEW,uri);
                     startActivity(i)
                 }

             }
             try {
                     Picasso.get()
                         //画像URL
                         .load("http://35.189.144.179/"+responseItem.pictureID)
                         .resize(1000, 500) //表示サイズ指定
                         .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                         .into(shopImage) //imageViewに流し込み

             }catch(e:IllegalArgumentException) {

                     Picasso.get()
                         //画像URL
                         .load(R.drawable.m_e_others_501)
                         .resize(1000, 500) //表示サイズ指定
                         .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                         .into(shopImage) //imageViewに流し込み

             }

     }

 }