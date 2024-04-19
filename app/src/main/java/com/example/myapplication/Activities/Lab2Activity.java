package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Data.Models.GradeModel;
import com.example.myapplication.Adapters.GradeAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
public class Lab2Activity extends AppCompatActivity {

    private ArrayList<GradeModel> mGradesList = new ArrayList<>();
    float avg = 0.0F;
    String message="";
    int amountOfGrades;

    GradeAdapter gradeAdapter = null;
    RecyclerView gradeRecyclerView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Średnia");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle receivedData = getIntent().getExtras();

        try {
            amountOfGrades = receivedData.getInt("gradesAmount");
        } catch (NullPointerException e) {
            Toast.makeText(this, "popsulo sie", Toast.LENGTH_SHORT).show();
        }

        Button averageButton = findViewById(R.id.averageButton);
        averageButton.setOnClickListener(
                view -> { computeAverage(); }
        );
        String[] gradesNames = getResources().getStringArray(R.array.lessonsArray);

        for (int i=0; i<amountOfGrades; i++){
            mGradesList.add(new GradeModel(gradesNames[i], 2 ));
        }

        setUpState();
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
        sendData.putBoolean("isFinished", true);
        intent.putExtras(sendData);

        setResult(RESULT_OK, intent);
        finish();

//        new AlertDialog.Builder(this).setMessage(message).show();
//        Toast.makeText(this, "Srednia = " + srednia , Toast.LENGTH_SHORT).show();
    }

    /*
      Funkcja ustawiajaca GradeAdapter z lista ocen oraz RecyclerView
      - napisana aby uniknąć duplikacji kodu
     */
    private void setUpState(){

        gradeAdapter = new GradeAdapter(mGradesList, this);
        gradeRecyclerView = findViewById(R.id.gradesRecyclerView);
        gradeRecyclerView.setAdapter(gradeAdapter);
        gradeRecyclerView.setLayoutManager( new LinearLayoutManager(this) );
    }

    /*
       Wymagane aby po powrocie do poprzedniej aktywnosci przy uzyciu strzalki z menu
       nie czyscilo danych z formularza
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("grades", mGradesList);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("grades", mGradesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mGradesList = savedInstanceState.getParcelableArrayList("grades");

        /*
              po resecie aktywnosci ustawione oceny byly poprawnie zapisane w pamieci,
              ale wyswietlane byly z onCreate ( czyli 2.0 ), aby to naprawic
              trzeba od nowa ustawic adapter i recyclerView
         */

        setUpState();

        super.onRestoreInstanceState(savedInstanceState);
    }
}
