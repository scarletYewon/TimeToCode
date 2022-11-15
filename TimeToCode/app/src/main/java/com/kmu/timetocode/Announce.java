package com.kmu.timetocode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Announce extends Fragment {
    public static Announce newInstance(){
        return new Announce();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup announce = (ViewGroup) inflater.inflate(R.layout.fragment_announce, container, false);
        ImageButton backButton = announce.findViewById(R.id.backCertification);
        ImageButton AnnouncePageButton = announce.findViewById(R.id.announcePageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(MainFragment.newInstance());
            }
        });
        AnnouncePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(AnnouncePage.newInstance());
            }
        });
        return announce;
    }
}
