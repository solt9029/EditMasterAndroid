package com.solt9029.editmasterandroid.view.customview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import timber.log.Timber;

public abstract class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    protected SurfaceHolder holder;
    protected int width;

    public BaseSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Timber.d("surfaceChanged");
        this.width = width;
        draw(0);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Timber.d("surfaceCreated");
        width = getWidth();
        draw(0);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public abstract void draw(int translateY);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);
    }
}

