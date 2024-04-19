package com.example.myapplication.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class Lab1Activity extends AppCompatActivity {

    Button gradesButton, buttonGradesFinish;
    EditText name, surname, grades;

    TextView gradesMessageFinish;

    boolean[] showButton = {false, false, false};
    boolean showAvg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Oceny");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//dodac ogolne zmienne

        Toast formError = Toast.makeText(this, "Wprowadzono zle dane", Toast.LENGTH_SHORT);

        gradesButton = findViewById(R.id.gradesButton);
        buttonGradesFinish = findViewById(R.id.buttonGradesFinish);
        gradesMessageFinish = findViewById(R.id.textViewGradesAverage);

        gradesButton.setVisibility(View.INVISIBLE);
        buttonGradesFinish.setVisibility(View.INVISIBLE);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        grades = findViewById(R.id.grades);

//      name.setOnFocusChangeListener(new View.OnFocusChangeListener() { - alternatyna wersja
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

            Lab2ActivityResultLauncher.launch(intent);
//            startActivityForResult(intent, REQUEST_CODE);
//            setResult(RESULT_OK, intent);
//            finish();
//            startActivity(intent);
        });
        buttonGradesFinish.setOnClickListener( view -> {
            String message = "";
            if (buttonGradesFinish.getText().toString().equals("SESJA ZDANA")) {
                message = "Gratulacje!\nOtrzymuejsz zaliczenie 🎉🎉";
            } else {
                message = "Wysyłam podanie o zaliczenie warunkowe 😥😥";
            }
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finishAffinity();
//                            wg UX to niemiło zamykać aplikacje tako
                        }
                    })
                    .show();
        });
    }

    /**
     * Odpalenie aktywnosci Lab2 z obsługą danych wprowadzonych w podrzednej aktywnosci
     */
    ActivityResultLauncher<Intent> Lab2ActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        buttonGradesFinish.setVisibility(View.VISIBLE);
//                        It is just a warning as it will never be null if the response is successful.
//                        You can ignore it or wrap around if(response.body() != null) to remove the warning.
//                        https://stackoverflow.com/questions/46519388/method-invocation-may-produce-nullpointerexception-retrofit-body
                        buttonGradesFinish.setText(data.getExtras().getString("message"));
                        gradesMessageFinish.setText("ŚREDNIA: " + Float.toString(data.getExtras().getFloat("average")));

                        showAvg = true;
                    }
                }
            }
    );

    @Override
    protected void onSaveInstanceState(Bundle outState){

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        grades = findViewById(R.id.grades);

        outState.putString("name", name.getText().toString());
        outState.putString("surname", surname.getText().toString());
        outState.putString("grades", grades.getText().toString());
        outState.putBooleanArray("buttonVis", showButton);

        buttonGradesFinish = findViewById(R.id.buttonGradesFinish);
        gradesMessageFinish = findViewById(R.id.textViewGradesAverage);
        outState.putString("message", buttonGradesFinish.getText().toString());
        outState.putString("gradesMessage", gradesMessageFinish.getText().toString());



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
        showButton = savedInstanceState.getBooleanArray("buttonVis");



        String message = savedInstanceState.getString("message");
        if (!message.equals("")){
            buttonGradesFinish = findViewById(R.id.buttonGradesFinish);
            gradesMessageFinish = findViewById(R.id.textViewGradesAverage);
            buttonGradesFinish.setText(message);
            gradesMessageFinish.setText(savedInstanceState.getString("gradesMessage"));
            buttonGradesFinish.setVisibility(View.VISIBLE);
        }

        showButton();
    }

    /**
     * Funkcja zarzadzająca wyświetlaniem przycisku do przejścia do następnej aktywności
     * zależnie od poprawności wprowadzonych do formularza danych
     */
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