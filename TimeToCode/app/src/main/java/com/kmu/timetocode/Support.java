package com.kmu.timetocode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Support extends Fragment{

    public static Support newInstance(){
        return new Support();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup support = (ViewGroup) inflater.inflate(R.layout.fragment_support, container, false);
        ImageButton backButton = support.findViewById(R.id.backCertification);
        ImageButton questionButton = support.findViewById(R.id.questionButton);
        ImageButton inquiryButton = support.findViewById(R.id.inquiryButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(MainFragment.newInstance());
            }
        });
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Question.newInstance());
            }
        });
        inquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Inquiry.newInstance());
            }
        });
        return support;
    }
}
