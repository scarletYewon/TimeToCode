package com.kmu.timetocode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AnnouncePage extends Fragment {
    public static AnnouncePage newInstance(){
        return new AnnouncePage();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup announcePage = (ViewGroup) inflater.inflate(R.layout.fragment_announce_page, container, false);
        ImageButton backButton = announcePage.findViewById(R.id.backCertification);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Announce.newInstance());
            }
        });
        return announcePage;
    }
}