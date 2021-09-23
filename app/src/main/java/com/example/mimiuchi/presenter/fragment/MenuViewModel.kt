package com.example.mimiuchi.presenter.fragment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "メニュー一覧"
    }
    val text: LiveData<String> = _text
}