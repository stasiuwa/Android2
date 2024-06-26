package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast toast = Toast.makeText(this, "Jeszcze nie wykonano!", Toast.LENGTH_SHORT);


        Button btn1 = (Button) findViewById(R.id.btnLab1);
        btn1.setOnClickListener(
                view -> changeActivity(Lab1Activity.class));

        Button btn2 = (Button) findViewById(R.id.btnLab2);
        btn2.setOnClickListener(
                view -> changeActivity(PhoneDatabaseActivity.class));

        Button btn3 = (Button) findViewById(R.id.btnLab3);
        btn3.setOnClickListener(
                view -> changeActivity(Lab4Activity.class));

        Button btn4 = (Button) findViewById(R.id.btnLab4);
        btn4.setOnClickListener(
                view -> changeActivity(Lab5Activity.class));

    }

    protected void changeActivity(Class<?> act){
        Intent intent = new Intent(MainActivity.this, act);
        startActivity(intent);
    }

}