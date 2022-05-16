package com.example.stuffy.presentation.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {
    private val _count  = MutableLiveData<Int>().apply {
        value = 1
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is share Fragment"
    }
    val count: LiveData<Int> = _count
   fun inc (){
       _count.value?.let {

           _count.value = it  + 1
       }
   }
    fun dec (){
        _count.value?.let {
            if(_count.value!=1){
                _count.value = it  - 1
            }

        }
    }
    val text: LiveData<String> = _text
}