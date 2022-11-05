package com.kmu.timetocode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecordActivity: AppCompatActivity() {
    var gridview: GridView? = null
    var adapter: GridViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val toolbar = findViewById<Toolbar>(R.id.toolbar3)
        val backRecord = findViewById<ImageButton>(R.id.backRecord)
        val recordList = findViewById<GridView>(R.id.recordList)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigationView3)

        adapter = GridViewAdapter(this, recordList)
        adapter!!.addItem(RecordList(1, "제목", R.drawable.ic_launcher_background))
        adapter!!.addItem(RecordList(2, "제목", R.drawable.ic_launcher_background))
        adapter!!.addItem(RecordList(3, "제목", R.drawable.ic_launcher_background))
        adapter!!.addItem(RecordList(4, "제목", R.drawable.ic_launcher_background))

        recordList.setAdapter(adapter);

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 왼쪽 버튼 사용 설정
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_favorite_24)
        backRecord.setOnClickListener {
            Toast.makeText(applicationContext, "뒤로가기", Toast.LENGTH_SHORT).show()
            finish()
            //            startActivity(intent)
        }

        bottomNavigation!!.setOnItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.certificationFragment -> {
                    Toast.makeText(applicationContext, "인증센터", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, CertificationActivity::class.java)
                    startActivity(intent)

//                    setFragment(TAG_CERTIFICATION, CertificationFragment())
                }
                R.id.searchFragment -> {
                    Toast.makeText(applicationContext, "검색", Toast.LENGTH_SHORT).show()
//                    setFragment(TAG_SEARCH, SearchFragment())
                }
                R.id.challengeFragment -> {
                    Toast.makeText(applicationContext, "챌린지 버튼이지만 임시로 기록센터로 이동", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RecordActivity::class.java) // 챌린지 페이지로 이동
                    startActivity(intent)
//                    setFragment(TAG_CHALLENGE, ChallengeFragment())
                }
            }
            true
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    // 툴바의 버튼 눌렀을 때 화면전환
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                Toast.makeText(applicationContext, "찜한 챌린지 보기", Toast.LENGTH_SHORT).show()
//                val intent = Intent(applicationContext, ) // 내가 찜한 챌린지로 이동
                return true
            }
//            R.id.favorite -> {
//                Toast.makeText(applicationContext, "찜한 챌린지 보기", Toast.LENGTH_SHORT).show()
//                val intent = Intent(applicationContext, ) // 내가 찜한 챌린지로 이동
//                return true
//            }
            R.id.notification -> {
                Toast.makeText(applicationContext, "알림센터로 이동", Toast.LENGTH_SHORT).show()
//                val intent = Intent(applicationContext, ) // 알림센터로 이동
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /* 그리드뷰 어댑터 */
    inner class GridViewAdapter(val context: Context, recordList: GridView) : BaseAdapter() {
        var recordList: ArrayList<RecordList> = ArrayList<RecordList>()
        override fun getCount(): Int { return recordList.size }

        fun addItem(item: RecordList) { recordList.add(item) }
        override fun getItem(position: Int): Any { return recordList[position] }
        override fun getItemId(position: Int): Long { return position.toLong() }
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val view: View = LayoutInflater.from(context).inflate(R.layout.gridview_record, null)

            var rc_num = view.findViewById<TextView>(R.id.recordNumber)
            var rc_title = view.findViewById<TextView>(R.id.recordTitle)
            val rc_image = view.findViewById<ImageButton>(R.id.recordImage)
            val recordList = recordList[position]
            val resourceId = context.resources.getIdentifier(recordList.resId.toString(), "drawable", context.packageName)
            rc_image.setImageResource(resourceId)

            rc_num.text = recordList.num.toString()
            rc_title.text = recordList.title
            recordList.resId?.let { rc_image.setImageResource(it) };

            //각 아이템 선택 event
            view.setOnClickListener {
                Toast.makeText(context,
                    recordList.num.toString() + " 번 - " + recordList.title + " 입니다! ",
                    Toast.LENGTH_SHORT).show()
            }
            return view //뷰 객체 반환
        }
    }
}