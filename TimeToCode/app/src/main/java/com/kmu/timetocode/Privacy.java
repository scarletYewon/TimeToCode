package com.kmu.timetocode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Privacy extends Fragment {
    public static Privacy newInstance(){
        return new Privacy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup privacy = (ViewGroup) inflater.inflate(R.layout.fragment_privacy, container, false);
        ImageButton backCertification = privacy.findViewById(R.id.backCertification);
        TextView privacyText = privacy.findViewById(R.id.privacyText);
        privacyText.setText("<개인정보보호 포털> 개인정보 처리방침\n" +
                "회원가입을 위해서는 아래와 같이 개인정보를 수집 및 이용합니다.\n" +
                        "\n" +
                        "1. 개인정보 수집 목적: 회원관리\n" +
                        "\n" +
                        "2. 개인정보 수집 항목 : 이메일, 비밀번호, 이름, 전화번호, 주소\n" +
                        "\n" +
                        "3. 보유 및 이용기간: 회원 탈퇴 시까지\n" +
                        "\n" +
                        "위 개인정보 수집 및 이용을 확인합니다.");
        backCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavActivity)getActivity()).replaceFragment(Support.newInstance());
            }
        });
    return privacy;
    }
}
