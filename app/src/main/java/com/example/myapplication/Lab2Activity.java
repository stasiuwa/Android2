package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Lab2Activity extends AppCompatActivity {

    private ArrayList<Integer> mNubmerList = new ArrayList<>();
    int amountOfGrades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);

        Bundle receivedData = getIntent().getExtras();
        amountOfGrades = receivedData.getInt("gradesAmount");

        TextView temp = findViewById(R.id.TEMP1);
        temp.setText(String.valueOf(amountOfGrades));

        for (int i=0; i<amountOfGrades; i++) mNubmerList.add(i);

        Button averageButton = findViewById(R.id.averageButton);
        averageButton.setOnClickListener(
                view -> { computeAverage(); }
        );
        RecyclerView numberRecyclerView = findViewById(R.id.numberRecyclerView);
        MyAdapter myAdapter = new MyAdapter(mNubmerList, this);
        numberRecyclerView.setAdapter(myAdapter);
        //RecylcerView wymaga managera układu
        numberRecyclerView.setLayoutManager( new LinearLayoutManager(this) );

    }

    private void computeAverage() {
        int sum=0;
        for (int number: mNubmerList) sum+=number;
        float srednia = sum/(float) amountOfGrades;
//        Toast.makeText(this, "Srednia = " + srednia , Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(this).setMessage("Średnia = " + srednia ).show();
    }
}
