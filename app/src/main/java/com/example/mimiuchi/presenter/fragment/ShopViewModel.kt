package com.example.mimiuchi.presenter.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShopViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "店舗詳細"
    }
    val text: LiveData<String> = _text





}