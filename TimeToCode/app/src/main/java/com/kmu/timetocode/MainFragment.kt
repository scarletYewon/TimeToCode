package com.kmu.timetocode

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kmu.timetocode.cdp.Done
import com.kmu.timetocode.cdp.Proceeding
import com.kmu.timetocode.cdp.UserCreated
import com.kmu.timetocode.certicenter.CertificationFragment
import com.kmu.timetocode.login.UserProfile
import com.prolificinteractive.materialcalendarview.*
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*


class MainFragment : Fragment() {
    val level = arrayOf("코딩 새싹", "코딩 병아리", "코린디", "전공자", "전문가", "컴퓨터")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        val rootView : View = inflater.inflate(R.layout.fragment_main, container, false)

        val userName = rootView?.findViewById<TextView>(R.id.userName)
        val userLevel = rootView?.findViewById<TextView>(R.id.userLevel)

        val ingChallenge = rootView?.findViewById<Button>(R.id.ingChallenge)
        val doneChallenge = rootView?.findViewById<Button>(R.id.doneChallenge)
        val madeByMe = rootView?.findViewById<Button>(R.id.madeByMe)

        val gotoCerti = rootView?.findViewById<ImageButton>(R.id.gotoCerti)
        val calendarView = rootView?.findViewById<MaterialCalendarView>(R.id.calenderView)
        calendarView?.state()?.edit()
            ?.setMinimumDate(CalendarDay.from(2022, 11, 1))
            ?.setMaximumDate(CalendarDay.from(Date(System.currentTimeMillis())))

        val toNoticeButton = rootView.findViewById<ImageButton>(R.id.toNotice)
        val toSupportButton = rootView.findViewById<ImageButton>(R.id.toSupport)

        userName?.text = UserProfile.getName()
        userLevel?.text = "Lv." + level[UserProfile.getLevel()]

        ingChallenge?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Proceeding()) }
        doneChallenge?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Done()) }
        madeByMe?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(UserCreated()) }

        gotoCerti?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(
            CertificationFragment()
        ) }

        toSupportButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Support.newInstance()) }
        toNoticeButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Announce.newInstance()) }

        return rootView
    }

        }
    }
}