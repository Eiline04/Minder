package com.technovation.sagetech.minder.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.technovation.sagetech.minder.MainActivity;
import com.technovation.sagetech.minder.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailAddress, passwordEditText;
    private Button signInBtn, signUpBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        emailAddress = findViewById(R.id.emailFiled);
        passwordEditText = findViewById(R.id.passwordFiled);
        signInBtn = findViewById(R.id.signinButton);
        signUpBtn = findViewById(R.id.signupButton);

        signUpBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,SignUpActivity.class)));

        mAuth = FirebaseAuth.getInstance();

        signInBtn.setOnClickListener(v -> {
            String email = emailAddress.getText().toString();
            String password = passwordEditText.getText().toString(); //Pass from tutorial

            if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"Eroare: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(LoginActivity.this,"Există câmpuri necompletate!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}