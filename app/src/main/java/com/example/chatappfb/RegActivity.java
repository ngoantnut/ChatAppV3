package com.example.chatappfb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText UserEmail, UserPassword;
    private TextView AlreadyHaveAccountLinks;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mAuth= FirebaseAuth.getInstance();
        InitializeFields();

        AlreadyHaveAccountLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackUserLoginActivity();
            }
        });
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }


        });
    }

    private void CreateNewAccount() {
        String email= UserEmail.getText().toString();
        String password= UserPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Bạn chưa nhập email",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Bạn chưa nhập mật khẩul",Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Tạo tài khoản mới");
            loadingBar.setMessage("Xin đợi một lát");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                BackUserLoginActivity();
                                Toast.makeText(RegActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(RegActivity.this, "Error : "+message,Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

        }

    }

    private void InitializeFields() {

        CreateAccountButton = (Button) findViewById(R.id.reg_button);
        UserEmail = (EditText) findViewById(R.id.reg_mail);
        UserPassword = (EditText) findViewById(R.id.reg_pass);
        AlreadyHaveAccountLinks = (TextView) findViewById(R.id.already_have_account_link);
        loadingBar= new ProgressDialog(this);
    }

    private void BackUserLoginActivity() {
        Intent regIntent = new Intent(RegActivity.this, LoginFActivity.class);
        startActivity(regIntent);
    }
}

