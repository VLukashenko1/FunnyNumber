package com.vitaliy.funnynumber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vitaliy.funnynumber.Adapter.HistoryAdapter;
import com.vitaliy.funnynumber.Room.History;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button getRandFact, getFact;
    EditText numberInput;
    ProgressBar progressBar;
    RecyclerView recyclerViewHistory;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        getRandFact = findViewById(R.id.getRandFactBtnMain);
        getFact = findViewById(R.id.getFactBtnMain);
        numberInput = findViewById(R.id.numberInputMain);
        progressBar = findViewById(R.id.progressBar);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistoryMain);

        getFact.setOnClickListener(v -> getFact());
        numberInput.setOnKeyListener((view, keyKode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyKode == KeyEvent.KEYCODE_ENTER) {
                getFact();
            }
            return false;
        });

        getRandFact.setOnClickListener(v -> getRandFact());

        buildRecyclerView();
    }

    void getFact() {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getFact(numberInput);
        viewModel.getMutableLiveDataFact().observe(this, result ->{
            if(result ==null) return;
            if (result.containsKey(MainActivityViewModel.TOAST_REQUEST_CODE)){
                progressBar.setVisibility(View.INVISIBLE);
                makeText(result.get(MainActivityViewModel.TOAST_REQUEST_CODE));
            }
            else if (result.containsKey(MainActivityViewModel.ALERT_REQUEST_CODE)){
                progressBar.setVisibility(View.INVISIBLE);
                makeResultDialog(result.get(MainActivityViewModel.ALERT_REQUEST_CODE));
            }
        });

        numberInput.setText("");
    }

    void getRandFact() {
        progressBar.setVisibility(View.VISIBLE);

        viewModel.getRandFact();

        viewModel.getMutableLiveDataFact().observe(this, result ->{
            if(result ==null) return;
            if (result.containsKey(MainActivityViewModel.TOAST_REQUEST_CODE)){
                progressBar.setVisibility(View.INVISIBLE);
                makeText(result.get(MainActivityViewModel.TOAST_REQUEST_CODE));
            }
            else if (result.containsKey(MainActivityViewModel.ALERT_REQUEST_CODE)){
                progressBar.setVisibility(View.INVISIBLE);
                makeResultDialog(result.get(MainActivityViewModel.ALERT_REQUEST_CODE));
            }
        });

    }

    void buildRecyclerView() {
        List<History> historyList = App.getInstance().getHistoryDAO().getAll();
        if (historyList == null) {
            historyList = new ArrayList<>();
        }
        HistoryAdapter historyAdapter = new HistoryAdapter(this, historyList, this);
        recyclerViewHistory.setAdapter(historyAdapter);
    }

    void makeText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        viewModel.getMutableLiveDataFact().setValue(new HashMap<>());
    }

    void makeResultDialog(String result) {
        AlertDialog.Builder resultDialog = new AlertDialog.Builder(this);
        resultDialog.setMessage(result).setCancelable(false).setPositiveButton("Ok", ((dialogInterface, i) -> {
            viewModel.getMutableLiveDataFact().setValue(new HashMap<>());
            return;
        }));
        AlertDialog alertDialog = resultDialog.create();
        alertDialog.show();
    }

}