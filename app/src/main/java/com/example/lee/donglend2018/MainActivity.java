package com.example.lee.donglend2018;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText email_join;
    private EditText pwd_join;
    private Button btn;
    private Button btn2;
    private CheckBox chk;
    FirebaseAuth firebaseAuth;
    TextView ex;
    private SharedPreferences appData;
    private boolean saveLoginData;
    private String id;
    private String pwd;

    //mysql
    private static String IP_ADDRESS = "49.170.233.37";
    private static String TAG = "dbtest";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email_join = (EditText) findViewById(R.id.emailInput);
        pwd_join = (EditText) findViewById(R.id.passwordInput);
        btn = (Button) findViewById(R.id.signupButton);
        btn2 = (Button) findViewById(R.id.loginButton);
        chk = (CheckBox) findViewById(R.id.checkBox);
        firebaseAuth = FirebaseAuth.getInstance();


        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        if (saveLoginData) {
            email_join.setText(id);
            pwd_join.setText(pwd);
            chk.setChecked(saveLoginData);

            final String email = email_join.getText().toString().trim();
            String pwd = pwd_join.getText().toString().trim();

            firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        save();
                        intent.putExtra("text", String.valueOf(email_join.getText()));
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_join.getText().toString().trim();
                String pwd = pwd_join.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "등록 완료", Toast.LENGTH_SHORT).show();
                                    email_join.setText("");
                                    pwd_join.setText("");


                                } else {
                                    Toast.makeText(MainActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = email_join.getText().toString().trim();
                String pwd = pwd_join.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            save();
                            intent.putExtra("text", String.valueOf(email_join.getText()));
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", chk.isChecked());
        editor.putString("ID", email_join.getText().toString().trim());
        editor.putString("PWD", pwd_join.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }


    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }


}