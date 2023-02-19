package com.vitaliy.funnynumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vitaliy.funnynumber.Adapter.HistoryAdapter;
import com.vitaliy.funnynumber.Room.History;

public class FactDetails extends AppCompatActivity {

    TextView number, fact;
    ImageButton back;
    Button copy, delete, done;

    History history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_details);

        number = findViewById(R.id.numberTextViewElDetails);
        fact = findViewById(R.id.factTextViewElDetails);

        back = findViewById(R.id.backButtonElDetails);
        back.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));

        copy = findViewById(R.id.copyButtonElDetails);
        copy.setOnClickListener(view -> {
            App.getInstance().getClipboardManager().setPrimaryClip(ClipData
                    .newPlainText("text", history.number + " " + history.fact));
            makeText("Copied");
        });

        delete = findViewById(R.id.deleteButtonElDetails);
        delete.setOnClickListener(view -> {
            App.getInstance().getHistoryDAO().delete(history);
            startActivity(new Intent(this, MainActivity.class));
        });

        done = findViewById(R.id.doneButtonElDetails);
        done.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));

        history = (History) getIntent().getExtras().get(HistoryAdapter.EXTRA_HISTORY);

        show();
    }

    private void show() {
        if (history == null){
            Toast.makeText(this,"An error occurred", Toast.LENGTH_SHORT);
            return;
        }

        number.setText(history.number);
        fact.setText(history.fact);
    }

    private void makeText(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        onDestroy();
    }
}