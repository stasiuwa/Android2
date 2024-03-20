package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Lab1Activity extends AppCompatActivity {

    Button gradesButton;
    EditText name, surname, grades;

    boolean[] showButton = {false, false, false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);
//dodac ogolne zmienne

        Toast formError = Toast.makeText(this, "Wprowadzono zle dane", Toast.LENGTH_SHORT);

        gradesButton = findViewById(R.id.gradesButton);
        gradesButton.setVisibility(View.INVISIBLE);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        grades = findViewById(R.id.grades);

//      name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        name.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                if(name.length() == 0) {
                    showButton[0] = false;
                    name.setError("Podaj imie");
                    formError.show();
                } else {
                    showButton[0] = true;
                    showButton();
                }
            }
        });
        surname.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                if(surname.length() == 0){
                    showButton[1] = false;
                    surname.setError("Podaj nazwisko!");
                    formError.show();
                } else {
                    showButton[1] = true;
                    showButton();
                }
            }
        });
        grades.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                if(grades.length() == 0){
                    showButton[2] = false;
                    grades.setError("Wprowadz ilosc ocen!");
                    formError.show();
                } else {
                    int gradesValue = Integer.parseInt(grades.getText().toString());
                    if (gradesValue <= 0 || gradesValue > 15) {
                        showButton[2] = false;
                        grades.setError("Zla ilosc ocen!");
                        formError.show();
                    } else {
                        showButton[2] = true;
                        showButton();
                    }
                }
            }
        });
        gradesButton.setOnClickListener( view -> {
            Intent intent = new Intent(Lab1Activity.this, Lab2Activity.class);

            Bundle sendData = new Bundle();
            sendData.putInt("gradesAmount", Integer.parseInt(grades.getText().toString()));
            intent.putExtras(sendData);
            setResult(RESULT_OK, intent);
            finish();

            startActivity(intent);
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        grades = findViewById(R.id.grades);

        outState.putString("name", name.getText().toString());
        outState.putString("surname", surname.getText().toString());
        outState.putString("grades", grades.getText().toString());
        outState.putBooleanArray("buttonVis", showButton);

        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        grades = findViewById(R.id.grades);


        name.setText(savedInstanceState.getString("name"));
        surname.setText(savedInstanceState.getString("surname"));
        grades.setText(savedInstanceState.getString("grades"));
        this.showButton = savedInstanceState.getBooleanArray("buttonVis");

        showButton();
    }
    private void showButton(){
        boolean allTrue = true;
        for (boolean value : showButton){
            if (!value) {
                allTrue = false;
                break;
            }
        }
        if(allTrue){
            gradesButton.setVisibility(View.VISIBLE);
        } else {
            gradesButton.setVisibility(View.INVISIBLE);
        }
    }
}