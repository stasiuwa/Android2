package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Lab2Activity extends AppCompatActivity {

    private ArrayList<GradeModel> mGradesList = new ArrayList<>();
    float avg = 0.0F;
    String message="";
    int amountOfGrades;

    MyAdapter myAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LAB 2");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
        Random random = new Random();

//        przy każdym obrocie losuje nowe oceny i wyświetla odpowiednie radioCheck
//        ale w pamięci ma zapisane własciwe oceny z pierwszego uruchomienia/wybraneprzez użytkownika

        for (int i=0; i<amountOfGrades; i++){
            mGradesList.add(new GradeModel(gradesNames[i], random.nextInt(4) + 2 ));
        }

        myAdapter = new MyAdapter(mGradesList, this);
        RecyclerView gradeRecyclerView = findViewById(R.id.gradesRecyclerView);


        gradeRecyclerView.setAdapter(myAdapter);
        gradeRecyclerView.setLayoutManager( new LinearLayoutManager(this) );

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void computeAverage() {
        int sum=0;
        boolean isZdane;
        for (GradeModel grade: mGradesList) sum+=grade.getGrade();
        avg = sum / (float)amountOfGrades;

        Intent intent = new Intent(Lab2Activity.this, Lab1Activity.class);
        Bundle sendData = new Bundle();

        if (avg < 3.0) {
            message += "Wyjazd na waruna!";
            isZdane = true;
        } else {
            message += "SESJA ZDANA";
            isZdane = false;
        }

//        sendData.putBoolean("isZdane", isZdane);
        sendData.putFloat("average", avg);
        sendData.putString("message", message);
        intent.putExtras(sendData);

        setResult(RESULT_OK, intent);
        finish();

//        new AlertDialog.Builder(this).setMessage(message).show();
//        Toast.makeText(this, "Srednia = " + srednia , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("grades", mGradesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mGradesList = savedInstanceState.getParcelableArrayList("grades");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
