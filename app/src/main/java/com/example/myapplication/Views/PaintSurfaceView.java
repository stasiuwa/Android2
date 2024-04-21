package com.example.myapplication.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

public class PaintSurfaceView  extends SurfaceView implements SurfaceHolder.Callback {

    private int mColor = R.color.blue;
    private Paint mPaint;
    private Path mPath;
    Bitmap bitmap = null;
    Canvas canvas= null;

    public PaintSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
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
        mPaint.setStrokeWidth(10);

//        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mPath = new Path();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float X = event.getX();
                float Y = event.getY();
                mPaint.setColor(getResources().getColor(mColor));

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mPath.reset();
                        mPath.moveTo(X,Y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mPath.lineTo(X,Y);
                        break;
                    case MotionEvent.ACTION_UP:
                        mPath.lineTo(event.getX(), event.getY());
                        canvas = getHolder().lockCanvas();
                        canvas.drawPath(mPath, mPaint);
                        getHolder().unlockCanvasAndPost(canvas);
                        break;
                }
                if (mPath != null) {
                    canvas = getHolder().lockCanvas();
                    canvas.drawPath(mPath, mPaint);
                    getHolder().unlockCanvasAndPost(canvas);
                }
                return true;
            }
        });

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
}
