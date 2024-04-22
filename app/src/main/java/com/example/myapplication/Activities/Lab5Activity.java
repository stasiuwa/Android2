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

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        ArrayList<Pair<Path, Paint>> temp = binding.paintSurfaceView.mPaths;
//        for (Pair<Path, Paint> p : temp) {
//            Log.d("LAB5", "Path" + p.first + "   Paint: " + p.second);
//        }
//        outState.putParcelable("paths", binding.paintSurfaceView.mPaths);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        binding.paintSurfaceView.mPaths = (ArrayList<Pair<Path, Paint>>) savedInstanceState.getSerializable("paths");
//        Log.d("LAB5", "mPaths:" + binding.paintSurfaceView.mPaths);
//        binding.paintSurfaceView.drawCanva();
//    }
}