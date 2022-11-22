package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class bookhotel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookhotel);
        initViews();
        setListensers();
    }
    private Button button_back;
    private void initViews() {
        button_back = (Button) findViewById(R.id.button);
    }
    private void setListensers()
    {
        button_back.setOnClickListener(gotonext);
    }
    private View.OnClickListener gotonext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(bookhotel.this, ReportActivity.class);
            startActivity(intent);
        }
    };

}