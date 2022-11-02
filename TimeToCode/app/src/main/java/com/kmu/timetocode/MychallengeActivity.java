package com.kmu.timetocode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MychallengeActivity extends AppCompatActivity {

    ImageButton backCertification;
    ListView challengeList;
    ListViewAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychallenge);

        backCertification = (ImageButton) findViewById(R.id.backCertification);
        backCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "뒤로가기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        challengeList = (ListView) findViewById(R.id.challengeList);
        adapter = new ListViewAdapter();


    }

    public class ListViewAdapter extends BaseAdapter {
        ArrayList<ChallengeItem> items = new ArrayList<ChallengeItem>();

        @Override
        public int getCount() {
            return items.size();
        }
        public void addItem(ChallengeItem item) {
            items.add(item);
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final ChallengeItem challengeItem = items.get(position);
            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_challenge, viewGroup, false);
            } else {
                View view = new View(context);
                view = (View) convertView;
            }
            TextView ch_title = (TextView) convertView.findViewById(R.id.challengeTitle);
            TextView ch_explain = (TextView) convertView.findViewById(R.id.challengeExplain);
            TextView ch_maker = (TextView) convertView.findViewById(R.id.challengeMaker);
//            ImageView ch_icon = (ImageView) convertView.findViewById(R.id.iv_icon);

            ch_title.setText(challengeItem.getName());
            ch_explain.setText(challengeItem.getNum());
//            ch_maker.setImageResource(challengeItem.getResId());
            Log.d("리스트뷰", "getView() - [ "+position+" ] "+challengeItem.getName());

            //각 아이템 선택 event
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, challengeItem.getNum()+" 번 - "+challengeItem.getName()+" 입니당! ", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;  //뷰 객체 반환
        }
    }

}