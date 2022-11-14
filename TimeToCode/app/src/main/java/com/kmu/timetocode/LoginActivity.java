package com.kmu.timetocode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    ProgressDialog dialog;

    ViewFlipper vf;

    SharedPreferences.Editor editor;

    public final static String url = "https://android-pkfbl.run.goorm.io";

    RequestQueue queue;

    LinearLayout splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        splash = findViewById(R.id.splash);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        vf = findViewById(R.id.vf);
        vf.setInAnimation(this, android.R.anim.slide_in_left);
        vf.setOutAnimation(this, android.R.anim.slide_out_right);
        vf.startFlipping();

        SharedPreferences spref = getSharedPreferences("login", MODE_PRIVATE);
        editor = spref.edit();

        editId = findViewById(R.id.editId);
        editPw = findViewById(R.id.editPw);

        editId.setText(spref.getString("id", null));
        editPw.setText(spref.getString("pw", null));

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            login(editId.getText().toString(), editPw.getText().toString());
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

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloudanim);
        splash.startAnimation(anim);
    }

    private void login(String id, String pw) {
        dialog.show();

        StringBuilder loginUrl = new StringBuilder();
        loginUrl.append(LoginActivity.url);
        loginUrl.append("/user/login");

        StringRequest sr = new StringRequest(Request.Method.POST, loginUrl.toString(), response -> {
            // 로그인 응답 확인하기
            if (response.equals("1")) {
                editor.putString("id", editId.getText().toString());
                editor.putString("pw", editPw.getText().toString());
                editor.commit();

                Toast.makeText(this, "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                dialog.dismiss();
                startActivity(intent);
            } else {
                dialog.dismiss();
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.chillanim);
                editPw.startAnimation(anim);
                Toast.makeText(this, "아이디와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            dialog.dismiss();
            Toast.makeText(this, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
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
    }
}