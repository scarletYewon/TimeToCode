package com.kmu.timetocode;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Inquiry extends Fragment {

    EditText inquiryContexts;
    EditText inquiryTitle;
    Button TermsOfService;
    ImageButton backButton;
    Boolean nameFlag;
    Boolean contentFlag;

    public void flagCheck() {
        if (nameFlag & contentFlag){
            TermsOfService.setBackgroundColor(Color.parseColor("#645EFF"));
        }
        else{
            TermsOfService.setBackgroundColor(Color.parseColor("#BFBFBF"));
        }
        TermsOfService.setClickable(nameFlag & contentFlag);
    }

    public static Inquiry newInstance() {
        return new Inquiry();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup inquiry = (ViewGroup) inflater.inflate(R.layout.fragment_inquiry, container, false);
        TermsOfService = (Button) inquiry.findViewById(R.id.inquiryButton);
        backButton = (ImageButton) inquiry.findViewById(R.id.backCertification);
        inquiryTitle = inquiry.findViewById(R.id.inquiryTitle);
        inquiryContexts = inquiry.findViewById(R.id.inquiryContents);

        nameFlag = false;
        contentFlag = false;
        TermsOfService.setClickable(false);

        inquiryTitle.addTextChangedListener(textWatcher);
        inquiryContexts.addTextChangedListener(textWatcher);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Support.newInstance());
            }
        });

        TermsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getContext(), "문의 완료", Toast.LENGTH_SHORT);
                toast.show();
                ((NavActivity)getActivity()).replaceFragment(Support.newInstance());
            }
        });

        return inquiry;
    }
    final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            nameFlag = (inquiryTitle.getText().length() > 0);
            contentFlag = (inquiryContexts.getText().length() > 0);
            flagCheck();
        }
    };
}
