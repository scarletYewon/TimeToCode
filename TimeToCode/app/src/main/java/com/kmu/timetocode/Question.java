package com.kmu.timetocode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Question extends Fragment {

    private ArrayList<String> mGroupList = new ArrayList<>();
    private ArrayList<ArrayList<String>> mChildList = new ArrayList<>();
    private ArrayList<String> questionOne = new ArrayList<>();
    private ArrayList<String> questionTwo = new ArrayList<>();
    private ExpandableListView mListView;

    public static Question newInstance(){
        return new Question();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup question = (ViewGroup) inflater.inflate(R.layout.fragment_question, container, false);
        mListView = question.findViewById(R.id.question_list);

        mGroupList.add("인증은 어떻게 하나요?");
        questionOne.add("인증하기 버튼을 눌러 사진과 함께 인증하시면 됩니다!");
        mChildList.add(questionOne);
        mGroupList.add("인증 현황은 어디서 볼 수 있나요?");
        questionTwo.add("인증 현황은 달력에 인증한 날짜가 표시되어 있습니다!");
        mChildList.add(questionTwo);

        mListView.setAdapter(new BaseExpandableAdapter(getContext(), mGroupList, mChildList));

        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });


        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        ImageButton backButton = question.findViewById(R.id.backCertification);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Support.newInstance());
            }
        });

        return question;
    }
}
