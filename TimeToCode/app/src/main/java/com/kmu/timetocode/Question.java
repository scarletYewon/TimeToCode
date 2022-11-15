package com.kmu.timetocode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Question extends Fragment {

    public static Question newInstance(){
        return new Question();
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup question = (ViewGroup) inflater.inflate(R.layout.fragment_question, container, false);
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
