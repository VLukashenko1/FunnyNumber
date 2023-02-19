package com.vitaliy.funnynumber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button getRandFact, getFact;
    EditText numberInput;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRandFact = findViewById(R.id.getRandFactBtnMain);
        getFact = findViewById(R.id.getFactBtnMain);
        numberInput = findViewById(R.id.numberInputMain);
        progressBar = findViewById(R.id.progressBar);

        getFact.setOnClickListener(v -> getFact());
        getRandFact.setOnClickListener(v -> getRandFact());
    }

    void getFact() {
    }

    void getRandFact() {

    }

    void makeText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}