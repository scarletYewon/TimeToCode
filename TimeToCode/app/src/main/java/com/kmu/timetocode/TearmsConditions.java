package com.kmu.timetocode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TearmsConditions extends Fragment {

    public static TearmsConditions newInstance(){
        return new TearmsConditions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup termsCondition = (ViewGroup) inflater.inflate(R.layout.fragment_terms_conditions, container, false);
        ImageButton backCertification = termsCondition.findViewById(R.id.backCertification);
        backCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Support.newInstance());
            }
        });


        return termsCondition;
    }
}
