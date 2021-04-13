package com.technovation.sagetech.minder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.technovation.sagetech.minder.quizzez.TestTrueFalse.Test1_TrueFalse;

public class EndTestScore extends AppCompatActivity {

    private TextView scoreTextView;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_test_score);

        scoreTextView = findViewById(R.id.finalScoreTextView);
        finishButton = findViewById(R.id.finishButton);

        scoreTextView.setText(GlobalUtilities.getScorePercentage());

        finishButton.setClickable(true);
        //------------Start Main Activity---------------
        finishButton.setOnClickListener(v -> {
            startActivity(new Intent(EndTestScore.this, MainActivity.class));
            finish();
        });

    }
}