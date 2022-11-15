package com.kmu.timetocode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        ViewGroup main = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton toNoticeButton = main.findViewById(R.id.toNotice);
        ImageButton toSupportButton = main.findViewById(R.id.toSupport);
        toSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Support.newInstance());
            }
        });
        toNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Announce.newInstance());
            }
        });

        return main;
    }

}
