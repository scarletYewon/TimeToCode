package com.kmu.timetocode.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddChallengeViewModel:ViewModel() {

    private val _name = MutableLiveData<String>()
    private val _tag1 = MutableLiveData<String>()
    private val _tag2 = MutableLiveData<String>()
    private val _introduce = MutableLiveData<String>()

    val nameData: LiveData<String> get() = _name
    val tag1Data: LiveData<String> get() = _tag1
    val tag2Data: LiveData<String> get() = _tag2
    val introduceData: LiveData<String> get() = _introduce

    private val _challengeImg = MutableLiveData<String>()

    val chImgData: LiveData<String> get() =_challengeImg

    private val _freq = MutableLiveData<Int>()
    private val _count = MutableLiveData<Int>()
    private val _interval = MutableLiveData<String>()
    private val _startTime = MutableLiveData<Int>()
    private val _endTime = MutableLiveData<Int>()
    private val _challengePeriod = MutableLiveData<Int>()

    val freqData: LiveData<Int> get() = _freq
    val countData: LiveData<Int> get() = _count
    val intervalData: LiveData<String> get() = _interval
    val startTimeData: LiveData<Int> get() = _startTime
    val endTimeData: LiveData<Int> get() = _endTime
    val chPeriodData: LiveData<Int> get() = _challengePeriod

    private val _how = MutableLiveData<String>()
    private val _howImg = MutableLiveData<String>()

    val howData: LiveData<String> get() = _how
    val howImgData: LiveData<String> get() = _howImg

    fun addData1( challengeName:String, challengeTag1:String, challengeTag2:String, challengeIntroduce:String ){
        _name.value = challengeName
        _tag1.value = challengeTag1
        _tag2.value = challengeTag2
        _introduce.value = challengeIntroduce
    }

    fun addData2(challengeImg:String){
        _challengeImg.value=challengeImg
    }

    fun addData3(freq:Int, count:Int, interval:String, startTime:Int, endTime:Int, challengePeriod:Int){
        _freq.value = freq
        _count.value = count
        _interval.value = interval
        _startTime.value = startTime
        _endTime.value = endTime
        _challengePeriod.value = challengePeriod
    }
}