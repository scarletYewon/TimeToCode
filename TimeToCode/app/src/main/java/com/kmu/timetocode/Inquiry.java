package com.kmu.timetocode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Inquiry extends Fragment {
    public static Inquiry newInstance() {
        return new Inquiry();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup inquiry = (ViewGroup) inflater.inflate(R.layout.fragment_inquiry, container, false);
        ImageButton backButton = inquiry.findViewById(R.id.backCertification);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Support.newInstance());
            }
        });
        return inquiry;
    }
}
