package com.example.myapplication.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

import java.util.ArrayList;

public class PaintSurfaceView  extends SurfaceView implements SurfaceHolder.Callback {
    private int mColor = R.color.blue;
    private Paint mPaint, dotPaint;

    private static final int cirRad = 20;
    private Path mPath, dotPath;
    public ArrayList<Pair<Path,Paint>> mPaths;

    public PaintSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        getHolder().addCallback(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(mColor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15);

        dotPaint = new Paint(mPaint);
        dotPaint.setStyle(Paint.Style.FILL);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mPath = new Path();
        dotPath = new Path();
        mPaths = new ArrayList<>();
        setOnTouchListener((v, event) -> {
            synchronized (getHolder()){
                float X = event.getX();
                float Y = event.getY();
                mPaint.setColor(getResources().getColor(mColor));
                dotPaint.setColor(getResources().getColor(mColor));
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mPath.reset();
                        mPath.moveTo(X, Y);
                        makeDot(X,Y);
                        mPaths.add(new Pair<>(new Path(dotPath),new Paint(dotPaint)));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mPath.lineTo(X, Y);
                        break;
                    case MotionEvent.ACTION_UP:
                        makeDot(X,Y);
                        mPaths.add(new Pair<>(new Path(dotPath),new Paint(dotPaint)));
                        mPaths.add(new Pair<>(new Path(mPath), new Paint(mPaint)));
                        break;
                }
                drawCanva();
                return true;
            }
        });

    }

    /**
     * Tworzy kropke o promieniu cirRad
     * @param X,Y wspołrzedne
     */
    private void makeDot(float X, float Y) {
        dotPath.reset();
        dotPath.moveTo(X,Y);
        dotPath.addCircle(X,Y,cirRad,Path.Direction.CW);
    }

    public void drawCanva(){
        synchronized (getHolder()){
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                try {
//                    wyczysc ekran
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//                    narysuj sciezki z tablicy na nowym ekranie
                    for (Pair<Path,Paint> p : mPaths) {
                        canvas.drawPath(p.first, p.second);
                    }
//                    rysuj aktualną ścieżke
                    if (!mPath.isEmpty()){
                        canvas.drawPath(mPath,mPaint);
                    }
                } finally {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }

    public void setStrokeColor(int color) {
        this.mColor = color;
    }

    public void clearCanva() {
        synchronized (getHolder()) {
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    mPaths.clear();
                } finally {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
