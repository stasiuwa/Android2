package com.example.myapplication.Activities;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Views.PaintSurfaceView;
import com.example.myapplication.databinding.ActivityLab5Binding;

import java.util.ArrayList;

public class Lab5Activity extends AppCompatActivity {

    ActivityLab5Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLab5Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Rysowanie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PaintSurfaceView paintSurfaceView = binding.paintSurfaceView;

        binding.buttonRED.setOnClickListener( v -> {
            paintSurfaceView.setStrokeColor(R.color.red);
        });
        binding.buttonYELLOW.setOnClickListener( v -> {
            paintSurfaceView.setStrokeColor(R.color.yellow);
        });
        binding.buttonBLUE.setOnClickListener( v -> {
            paintSurfaceView.setStrokeColor(R.color.blue);
        });
        binding.buttonGREEN.setOnClickListener( v -> {
            paintSurfaceView.setStrokeColor(R.color.green);
        });
        binding.clearPaintButton.setOnClickListener( v-> {
            paintSurfaceView.clearCanva();
        });

    }
}