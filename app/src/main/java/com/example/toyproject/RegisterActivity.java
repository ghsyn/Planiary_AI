package com.example.toyproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText id_register, pw_register, pwck_register, name_register, address_register;
    private Button idck_btn, register_btn, back_btn;
    private AlertDialog dialog;
    private boolean validate = false;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> onBackPressed());

        id_register = findViewById(R.id.id_register);
        pw_register = findViewById(R.id.pw_register);
        pwck_register = findViewById(R.id.pwck_register);
        name_register = findViewById(R.id.name_register);
        address_register = findViewById(R.id.address_register);

        idck_btn = findViewById(R.id.idck_btn);
        idck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = id_register.getText().toString();
                if(validate) {
                    return;
                }
                if(userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            if(success) {
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                id_register.setEnabled(false);
                                validate = true;
                                idck_btn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            } else {
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = id_register.getText().toString();
                final String userPassword = pw_register.getText().toString();
                final String pwck = pwck_register.getText().toString();
                final String userName = name_register.getText().toString();
                final String userAddress = address_register.getText().toString();

                /*
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                 */

                if (userID.equals("")||userPassword.equals("") || pwck.equals("") ||userName.equals("")||userAddress.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (userPassword.equals(pwck)) {
                                if (success) {
                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", userName), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAddress, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}