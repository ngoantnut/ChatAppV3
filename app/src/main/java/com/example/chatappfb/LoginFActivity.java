package com.example.chatappfb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private Button LoginButton, PhoneLoginButton;
    private EditText UserEmail, UserPassword;
    private TextView ForgetPassword, NeedNewAccountLink;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_factivity);
        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        InitializeFields();
        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserRegActivity();
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserLogin();
            }


        });
    }

    private void InitializeFields() {
        LoginButton = (Button) findViewById(R.id.login_button);
        PhoneLoginButton = (Button) findViewById(R.id.login_phone);
        UserEmail = (EditText) findViewById(R.id.login_mail);
        UserPassword = (EditText) findViewById(R.id.login_pass);
        ForgetPassword =(TextView) findViewById(R.id.forget_pass);
        NeedNewAccountLink = (TextView) findViewById(R.id.need_new_account_link);
        loadingBar= new ProgressDialog(this);

    }
    private void AllowUserLogin() {
        String email= UserEmail.getText().toString();
        String password= UserPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Bạn chưa nhập email",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Bạn chưa nhập mật khẩu",Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Đang đăng nhập");
            loadingBar.setMessage("Xin đợi một lát");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                SendUserToMainActivity();
                                Toast.makeText(LoginFActivity.this,"Đăng nhập thành công...",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(LoginFActivity.this, "Error : "+message,Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    protected void onStart() {
        super.onStart();
        if(currentUser!=null){
            SendUserToMainActivity();
        }
}

    private void SendUserToMainActivity() {
        Intent loginIntent = new Intent(LoginFActivity.this, MainActivity.class);
        startActivity(loginIntent);
    }

    private void SendUserRegActivity() {
        Intent regIntent = new Intent(LoginFActivity.this, RegActivity.class);
        startActivity(regIntent);
    }

    }