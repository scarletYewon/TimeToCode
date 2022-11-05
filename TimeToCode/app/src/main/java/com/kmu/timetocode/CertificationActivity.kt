package com.kmu.timetocode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class CertificationActivity:AppCompatActivity() {
    var challengeList = ArrayList<ChallengeList>()
    var adapter: ListViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification)

//        val challengeImage = findViewById<ImageButton>(R.id.challengeImage)
//        val challengeTitle = findViewById<Button>(R.id.challengeTitle)
//        val btnCertification = findViewById<Button>(R.id.btnCertification)
//        val btnGallery = findViewById<Button>(R.id.btnGallery)
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        val backCertification = findViewById<ImageButton>(R.id.backCertification)
        val challengeList = findViewById<ListView>(R.id.challengeList)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigationView2)

        adapter = ListViewAdapter(this, challengeList)
        // challenge 추가하는 부분
        adapter!!.addItem(ChallengeList(1, "Github Commit", "설명1", "만든이", R.drawable.ic_launcher_background))
        adapter!!.addItem(ChallengeList(2, "BOJ algorithm", "설명2", "만든사람", R.drawable.ic_launcher_background))

        challengeList.setAdapter(adapter);


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 왼쪽 버튼 사용 설정
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_favorite_24)
        backCertification.setOnClickListener {
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

    // 툴바 메뉴 설정
    inner class ListViewAdapter(val context: Context, challengeList: ListView): BaseAdapter() {
        override fun getCount(): Int { return challengeList.size }

        fun addItem(item: ChallengeList) { challengeList.add(item) }
        override fun getItem(position: Int): Any { return challengeList[position] }
        override fun getItemId(position: Int): Long { return position.toLong() }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val view: View = LayoutInflater.from(context).inflate(R.layout.listview_challenge, null)

            val ch_number = view.findViewById<TextView>(R.id.challengeNum)
            val ch_title = view.findViewById<TextView>(R.id.challengeTitle)
            val ch_explain = view.findViewById<TextView>(R.id.challengeExplain)
            val ch_maker = view.findViewById<TextView>(R.id.challengeMaker)
            val ch_image = view.findViewById<ImageButton>(R.id.challengeImage)

            val challengeList = challengeList[position]
            val resourceId = context.resources.getIdentifier(challengeList.resId.toString(), "drawable", context.packageName)
            ch_image.setImageResource(resourceId)

            ch_number.text = challengeList.num.toString()
            ch_title.text = challengeList.title
            ch_explain.text = challengeList.explain
            ch_maker.text = challengeList.maker
            challengeList.resId?.let { ch_image.setImageResource(it) };

            //각 아이템 선택 event
            view.setOnClickListener {
                Toast.makeText(context,
                    challengeList.num.toString() + " 번 - " + challengeList.title + " 입니다! ",
                    Toast.LENGTH_SHORT).show()
            }
            return view //뷰 객체 반환
        }
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
}