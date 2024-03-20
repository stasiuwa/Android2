package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Lab2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);

        Bundle receivedData = getIntent().getExtras();
        int amountOfGrades = receivedData.getInt("gradesAmount");

        TextView temp = findViewById(R.id.TEMP1);
        temp.setText(String.valueOf(amountOfGrades));
    }
}