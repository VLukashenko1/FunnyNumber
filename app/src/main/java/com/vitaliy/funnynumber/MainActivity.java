package com.vitaliy.funnynumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vitaliy.funnynumber.Util.FactDownloaderNetworkUtil;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        numberInput.setOnKeyListener((view, keyKode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyKode == KeyEvent.KEYCODE_ENTER){
                getFact();
            }
            return false;
        });

        getRandFact.setOnClickListener(v -> getRandFact());
    }

    void getFact() {
        progressBar.setVisibility(View.VISIBLE);

        if (numberInput == null || numberInput.getText() == null || numberInput.getText().toString().isEmpty()) {
            makeText("Enter a number to get a fact about it");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (!FactDownloaderNetworkUtil.isOpenNetwork(this)) {
            makeText("no internet connection");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        int number = Integer.parseInt(numberInput.getText().toString());

        Flowable.fromCallable(() -> FactDownloaderNetworkUtil.getInstance().getFact(number))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s == null || s.isEmpty()) return;
                    progressBar.setVisibility(View.INVISIBLE);
                    makeResultDialog(s);
                });
        numberInput.setText("");
    }

    void getRandFact() {
        progressBar.setVisibility(View.VISIBLE);
        if (!FactDownloaderNetworkUtil.isOpenNetwork(this)) {
            makeText("no internet connection");
            return;
        }
        Flowable.fromCallable(() -> FactDownloaderNetworkUtil.getInstance().getRandFact())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s == null || s.isEmpty()) return;
                    progressBar.setVisibility(View.INVISIBLE);
                    makeResultDialog(s);
                });
    }

    void makeText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    void makeResultDialog(String result) {
        AlertDialog.Builder resultDialog = new AlertDialog.Builder(this);
        resultDialog.setMessage(result).setCancelable(false).setPositiveButton("Ok", ((dialogInterface, i) -> {
            return;
        }));
        AlertDialog alertDialog = resultDialog.create();
        alertDialog.show();
    }

}