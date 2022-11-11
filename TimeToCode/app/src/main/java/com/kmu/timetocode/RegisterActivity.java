package com.kmu.timetocode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    ImageView x;
    EditText editId, editPw, editPw2, editName;
    ImageView idCheck, pwCheck, pw2Check, nameCheck;
    Button btnRegister;
    LinearLayout btnLogin;

    boolean notError = true;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        x = findViewById(R.id.x);
        x.setOnClickListener(view -> {
            finish();
        });

        editId = findViewById(R.id.editId);
        idCheck = findViewById(R.id.idCheck);
        editId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                idCheck.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String id = editId.getText().toString();
                if (!id.contains("@")) {
                    idCheck.setVisibility(View.INVISIBLE);
                } else {
                    idCheck.setVisibility(View.VISIBLE);
                }
            }
        });

        editPw = findViewById(R.id.editPw);
        pwCheck = findViewById(R.id.pwCheck);
        editPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pwCheck.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pw = editPw.getText().toString();
                if (pw.length() < 8) {
                    pwCheck.setVisibility(View.INVISIBLE);
                } else {
                    pwCheck.setVisibility(View.VISIBLE);
                }
            }
        });

        editPw2 = findViewById(R.id.editPw2);
        pw2Check = findViewById(R.id.pw2Check);
        editPw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pw2Check.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pw = editPw.getText().toString();
                String pw2 = editPw2.getText().toString();
                if (!pw.equals(pw2)) {
                    pw2Check.setVisibility(View.INVISIBLE);
                } else {
                    pw2Check.setVisibility(View.VISIBLE);
                }
            }
        });

        editName = findViewById(R.id.editName);
        nameCheck = findViewById(R.id.nameCheck);
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameCheck.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editName.getText().toString();
                if (name.isEmpty()) {
                    nameCheck.setVisibility(View.INVISIBLE);
                } else {
                    nameCheck.setVisibility(View.VISIBLE);
                }
            }
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(view -> {
            if (check()) {
                Toast.makeText(this, "계정이 생성되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            finish();
        });
    }

    private boolean check() {
        String id = editId.getText().toString();
        String pw = editPw.getText().toString();
        String pw2 = editPw2.getText().toString();
        String name = editName.getText().toString();

        if (id.isEmpty() & !id.contains("@")) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pw.length() < 8) {
            Toast.makeText(this, "비밀번호를 8자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!pw.equals(pw2)) {
            Toast.makeText(this, "비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.isEmpty()) {
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return makeUser(id, pw, name);
    }

    private boolean makeUser(String id, String pw, String name) {
        StringBuilder loginUrl = new StringBuilder();
        loginUrl.append(LoginActivity.url);
        loginUrl.append("/user/register");

        notError = false;
        StringRequest sr = new StringRequest(Request.Method.POST, loginUrl.toString(), response -> {
            // 회원가입 응답 확인하기
            notError = true;
        }, error -> {
            Toast.makeText(this, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() throws Error {
                Map<String, String> params = new HashMap<>();
                params.put("email", id);
                params.put("pw", pw);
                params.put("name", name);
                return params;
            }
        };
        sr.setShouldCache(false);
        queue = Volley.newRequestQueue(this);
        queue.add(sr);

        return notError;
    }
}