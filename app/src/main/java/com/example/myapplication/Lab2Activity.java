package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Lab2Activity extends AppCompatActivity {

    private List<GradeModel> mGradesList;
    int amountOfGrades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("LAB 2");


        try {
            amountOfGrades = getIntent().getExtras().getInt("gradesAmount");
        } catch (NullPointerException e) {
            Toast.makeText(this, "popsulo sie", Toast.LENGTH_SHORT).show();
        }

        Button averageButton = findViewById(R.id.averageButton);
        averageButton.setOnClickListener(
                view -> { computeAverage(); }
        );

        String[] gradesNames = getResources().getStringArray(R.array.lessonsArray);

        mGradesList = new ArrayList<>();
        for (int i=0; i<amountOfGrades; i++){
            mGradesList.add(new GradeModel(gradesNames[i], 2));
        }

        MyAdapter myAdapter = new MyAdapter(mGradesList, this);
        RecyclerView gradeRecyclerView = findViewById(R.id.gradesRecyclerView);

        gradeRecyclerView.setAdapter(myAdapter);
        //RecylcerView wymaga managera układu
        gradeRecyclerView.setLayoutManager( new LinearLayoutManager(this) );

    }

    private void computeAverage() {
        int sum=0;
        for (GradeModel grade: mGradesList) sum+=grade.getGrade();
        float srednia = sum / (float)amountOfGrades;

        String message="Średnia = " + srednia;
        if (srednia < 3.0) {
            message += "\nWyjazd na waruna!";
        } else {
            message += "\nSESJA ZDANA";
        }
        new AlertDialog.Builder(this).setMessage(message).show();
//        Toast.makeText(this, "Srednia = " + srednia , Toast.LENGTH_SHORT).show();
    }
}
