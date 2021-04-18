package com.technovation.sagetech.minder.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.technovation.sagetech.minder.MainActivity;
import com.technovation.sagetech.minder.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailAddress;
    private EditText passwordEditText;

    private Button signInBtn;
    private Button signUpBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //HOOKS
        emailAddress = findViewById(R.id.emailFiled);
        passwordEditText = findViewById(R.id.passwordFiled);
        signInBtn = findViewById(R.id.signinButton);
        signUpBtn = findViewById(R.id.signupButton);

        mAuth = FirebaseAuth.getInstance();

        //------------GO TO SIGN UP ACTIVITY----------------------
        signUpBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        //------------SIGN IN THE USER----------------------------
        signInBtn.setOnClickListener(v -> {
            String email = emailAddress.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Eroare: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Există câmpuri necompletate!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}