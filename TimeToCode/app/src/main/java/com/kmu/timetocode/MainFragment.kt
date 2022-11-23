package com.kmu.timetocode

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kmu.timetocode.cdp.Done
import com.kmu.timetocode.cdp.Proceeding
import com.kmu.timetocode.cdp.UserCreated
import com.kmu.timetocode.certicenter.CertificationFragment
import com.kmu.timetocode.login.LoginActivity
import com.kmu.timetocode.login.UserProfile
import com.prolificinteractive.materialcalendarview.*
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import org.json.JSONObject
import java.util.*


class MainFragment : Fragment() {
    val level = arrayOf("코딩 새싹", "코딩 병아리", "코린이", "전공자", "전문가", "컴퓨터")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val rootView : View = inflater.inflate(R.layout.fragment_main, container, false)

        val userName = rootView.findViewById<TextView>(R.id.userName)
        val userLevel = rootView.findViewById<TextView>(R.id.userLevel)
        userName?.text = UserProfile.getName()
        userLevel?.text = "Lv. " + level[UserProfile.getLevel()-1]

        val ingChallenge = rootView.findViewById<Button>(R.id.ingChallenge)
        val doneChallenge = rootView.findViewById<Button>(R.id.doneChallenge)
        val madeByMe = rootView.findViewById<Button>(R.id.madeByMe)

        val gotoCerti = rootView.findViewById<ImageButton>(R.id.gotoCerti)
        val calendarView = rootView.findViewById<MaterialCalendarView>(R.id.calenderView)
        calendarView?.state()?.edit()
            ?.setMinimumDate(CalendarDay.from(2022, 11, 1))
            ?.setMaximumDate(CalendarDay.from(Date(System.currentTimeMillis())))

        calendarView?.addDecorator(EventDecorator(Color.parseColor("#645EFF"), Collections.singleton(CalendarDay.today())));

        val toNoticeButton = rootView.findViewById<ImageButton>(R.id.toNotice)
        val toSupportButton = rootView.findViewById<ImageButton>(R.id.toSupport)



        ingChallenge?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Proceeding()) }
        doneChallenge?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Done()) }
        madeByMe?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(UserCreated()) }

        gotoCerti?.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(CertificationFragment()) }

        toSupportButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Support.newInstance()) }
        toNoticeButton.setOnClickListener { (activity as NavActivity?)!!.replaceFragment(Announce.newInstance()) }

        return rootView
    }

    class EventDecorator(private val color: Int, dates: Collection<CalendarDay>?) : DayViewDecorator {
        private val dates: HashSet<CalendarDay>

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return dates.contains(day)
        }
        override fun decorate(view: DayViewFacade) {
            view.addSpan(DotSpan(9F, color))
        }
        init{
            this.dates = HashSet(dates)
        }
    }
}