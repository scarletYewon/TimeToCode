package com.kmu.timetocode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    val message = MutableLiveData<String>()
    fun sendMessage(text: String) {
        message.value = text
    }
}