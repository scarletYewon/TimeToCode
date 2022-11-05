package com.kmu.timetocode

class ChallengeList(num: Int, title: String, explain: String="1일 1커밋을 해보자!", maker: String="생성자", resId: Int) {
    var num: Int = num
    var title: String? = title
    var explain: String? = explain
    var maker: String? = maker
    var resId: Int? = resId

    fun setNum() { num = num }
    fun setTitle() { title = title }
    fun setExplain() { explain = explain }
    fun setMaker() { maker = maker }
    fun setResId() { resId = resId }

}