package com.kmu.timetocode.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.kmu.timetocode.NavActivity;
import com.kmu.timetocode.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText editId, editPw;
    Button btnLogin, btnRegister;
    EditText btnRef;
    CustomProgressDialog dialog;

    ViewFlipper vf;

    SharedPreferences.Editor editor;

    public final static String url = "https://android-pkfbl.run.goorm.io";

    RequestQueue queue;

    LinearLayout splash;
    View blur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        splash = findViewById(R.id.splash);
        blur = findViewById(R.id.blur);

        dialog = new CustomProgressDialog(LoginActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

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
        blur.setVisibility(View.VISIBLE);

        String loginUrl = LoginActivity.url + "/user/login";
        StringRequest sr = new StringRequest(Request.Method.POST, loginUrl, response -> {
            // 로그인 응답 확인하기
            if (response.equals("1")) {
                editor.putString("id", editId.getText().toString());
                editor.putString("pw", editPw.getText().toString());
                editor.commit();

                setUserProfile(editId.getText().toString());

                Toast.makeText(this, "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                blur.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                startActivity(intent);
            } else {
                blur.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.chillanim);
                editPw.startAnimation(anim);
                Toast.makeText(this, "아이디와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            blur.setVisibility(View.INVISIBLE);
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

    private void setUserProfile(String email) {
        // 요청하고 싶은 HTTP Url
        String url = LoginActivity.url + "/user/email";
        StringRequest sr = new StringRequest(Request.Method.POST, url, response -> {
            // 직접 response를 출력해서 어떤 데이터인지 파악할것!
            try {
                JSONObject jsonObject = new JSONObject(response);
                int id = jsonObject.getInt("id"); // JSON 타입으로 얻고 싶은 데이터 명을 적으면 됨
                String name = jsonObject.getString("name");
                int level = jsonObject.getInt("level");
                UserProfile.setRef(id, name, email, level);
            } catch (Exception e) {
                Log.e("LoginJSON", "예외 발생");
            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() throws Error {
                // 파라미터로 넘겨야 하는 것을 추가할 것, 대소문자 주의!
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        sr.setShouldCache(false);
        queue = Volley.newRequestQueue(this);
        queue.add(sr);
    }
}