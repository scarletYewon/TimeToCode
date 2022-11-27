package com.kmu.timetocode.list

import android.net.Uri

data class ChallengeItemModel(
    val image : Uri,
    val title: String,
    val tag: String,
    val tag2: String,
    val whoMade:String
)
