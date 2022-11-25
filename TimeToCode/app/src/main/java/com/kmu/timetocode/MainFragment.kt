package com.kmu.timetocode

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kmu.timetocode.cdp.Done
import com.kmu.timetocode.cdp.Proceeding
import com.kmu.timetocode.cdp.UserCreated
import com.kmu.timetocode.certicenter.CertificationFragment
import com.kmu.timetocode.login.UserProfile
import com.prolificinteractive.materialcalendarview.*
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import org.json.JSONObject
import java.util.*


class MainFragment : Fragment() {
    var queue: RequestQueue? = null
    var proceeding:TextView?=null
    var complete:TextView?=null
    var upload:TextView?=null
    var calendarView:MaterialCalendarView?=null
    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val rootView : View = inflater.inflate(R.layout.fragment_main, container, false)

        val userName = rootView.findViewById<TextView>(R.id.userName)
        userName?.text = UserProfile.getName()

        val ingChallenge = rootView.findViewById<ViewGroup>(R.id.ingChallenge)
        val doneChallenge = rootView.findViewById<ViewGroup>(R.id.doneChallenge)
        val madeByMe = rootView.findViewById<ViewGroup>(R.id.madeByMe)
        proceeding = rootView.findViewById(R.id.proceeding)
        complete = rootView.findViewById(R.id.complete)
        upload = rootView.findViewById(R.id.upload)
        calendarView = rootView.findViewById(R.id.calenderView)
        calendarView?.state()?.edit()
            ?.setMinimumDate(CalendarDay.from(2022, 11, 1))
            ?.setMaximumDate(CalendarDay.from(Date(System.currentTimeMillis())))

        val gotoCerti = rootView.findViewById<ViewGroup>(R.id.gotoCerti)
        val toNoticeButton = rootView.findViewById<ViewGroup>(R.id.toNotice)
        val toSupportButton = rootView.findViewById<ViewGroup>(R.id.toSupport)

        showCount()
        showDate()

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

    private fun showCount() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/userChallenge/getRef?idUser=" + myId
        val sr: StringRequest = object : StringRequest(Method.GET, url,
            Response.Listener { response: String? ->
                try {
                    val jsonObject = JSONObject(response)
                    val progressCount = jsonObject.getInt("progress")
                    val completeCount = jsonObject.getInt("complete")
                    val uploadCount = jsonObject.getInt("upload")
                    proceeding?.text = progressCount.toString()
                    complete?.text = completeCount.toString()
                    upload?.text = uploadCount.toString()
                } catch (e: Exception) {
                    Log.e("Challenge Counting JSON", response!!)
                }
            },
            Response.ErrorListener { error: VolleyError ->
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String,String>? {
                val params: MutableMap<String, String> = HashMap()
//                    params["idUser"] = myId.toString()
//                    Log.e(params.toString(),"params")
                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(requireContext())
        queue!!.add(sr)
    }

    private fun showDate() {
        val myId = UserProfile.getId()
        val url = "https://android-pkfbl.run.goorm.io/userChallenge/allGetDate?idUser=" + myId
        val sr: StringRequest = object : StringRequest(Method.GET, url,
            Response.Listener { response: String? ->
                try {
                    val jsonObject = response!!
                    var arr = jsonObject.split(",", "[", "]")
                    arr = arr.filter { x: String? -> x != "" }
                    for (i in 0 until arr.size) {
                        val date = arr[i]
                        val yyyymmdd = date.split("-", "\"")
                        val yyyy = yyyymmdd[1].toInt()
                        val mm = yyyymmdd[2].toInt()
                        val dd = yyyymmdd[3].toInt()
                        calendarView?.addDecorator(EventDecorator(Color.parseColor("#645EFF"), Collections.singleton(CalendarDay.from(yyyy, mm-1, dd))))
                    }
                } catch (e: Exception) {
                    Log.e("Calendar JSON", response!!)
                }
            },
            Response.ErrorListener { error: VolleyError ->
            }) {
            @Throws(java.lang.Error::class)
            override fun getParams(): MutableMap<String,String>? {
                val params: MutableMap<String, String> = HashMap()
//                    params["idUser"] = myId.toString()
//                    Log.e(params.toString(),"params")
                return params
            }
        }
        sr.setShouldCache(false)
        queue = Volley.newRequestQueue(requireContext())
        queue!!.add(sr)
    }

}