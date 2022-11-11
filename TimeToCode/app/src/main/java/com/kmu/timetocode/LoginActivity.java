package com.kmu.timetocode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText editId, editPw;
    Button btnLogin, btnRegister;
    EditText btnRef;

    ViewFlipper vf;

    public final static String url = "https://android-pkfbl.run.goorm.io";

    boolean canLogin = false;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vf = findViewById(R.id.vf);
        vf.setInAnimation(this, android.R.anim.slide_in_left);
        vf.setOutAnimation(this, android.R.anim.slide_out_right);
        vf.startFlipping();

        editId = findViewById(R.id.editId);
        editPw = findViewById(R.id.editPw);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            if (login(editId.getText().toString(), editPw.getText().toString())) {
                Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "아이디와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

        btnRef = findViewById(R.id.btnRef);
        btnRef.setOnClickListener(view -> {
            // 이용약관
        });
    }

    private boolean login(String id, String pw) {
        StringBuilder loginUrl = new StringBuilder();
        loginUrl.append(LoginActivity.url);
        loginUrl.append("/user/login");

        StringRequest sr = new StringRequest(Request.Method.POST, loginUrl.toString(), response -> {
            // 로그인 응답 확인하기
            if (response.equals("1"))
                canLogin = true;
            else
                canLogin = false;
        }, error -> {
            Toast.makeText(this, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            canLogin = false;
        }) {
            @Override
            protected Map<String, String> getParams() throws Error {
                Map<String, String> params = new HashMap<>();
                params.put("email", id);
                params.put("pw", pw);
                return params;
            }
        };

        sr.setShouldCache(false);
        queue = Volley.newRequestQueue(this);
        queue.add(sr);
        return canLogin;
    }
}