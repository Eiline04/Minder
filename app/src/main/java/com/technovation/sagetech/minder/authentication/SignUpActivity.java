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

public class SignUpActivity extends AppCompatActivity {

    private EditText emailFiled, passwordFiled, checkPasswordFiled;
    private Button register, signIn;
    private FirebaseAuth mAth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        emailFiled = findViewById(R.id.emailFiled);
        passwordFiled = findViewById(R.id.passwordFiled);
        checkPasswordFiled = findViewById(R.id.repasswordFiled);
        register = findViewById(R.id.register);
        signIn = findViewById(R.id.goToLoginButton);

        mAth = FirebaseAuth.getInstance();


        //-------------Go to LoginActivity----------------
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });


        //-------------Register the user-----------------
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailFiled.getText().toString();
                String password = passwordFiled.getText().toString();
                String repassword = checkPasswordFiled.getText().toString();

                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(repassword)){

                    if(password.equals(repassword)){

                        mAth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(SignUpActivity.this,"Eroare: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(SignUpActivity.this,"Parolele diferă!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SignUpActivity.this,"Toate câmpurile trebuie completate", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}