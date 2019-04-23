package com.solt9029.editmasterandroid.view.customview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.solt9029.editmasterandroid.util.CalcUtil;

import timber.log.Timber;

public class EditorView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private int width;

    public EditorView(Context context) {
        super(context);
        init();
    }

    public EditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditorView(Context context, AttributeSet attrs, int defStyle) {
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

    /**
     * @param offset scroll offset (y)
     */
    public void draw(int offset) {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setStyle(Style.FILL);

        int count = 0;
        for (int y = -offset; y < CalcUtil.convertDp2Px(2000, getContext()) - offset; y += CalcUtil.convertDp2Px(80, getContext())) {
            canvas.drawCircle(width / 2, y, CalcUtil.convertDp2Px(10, getContext()) + count, paint);
            count++;
        }

        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);
    }
}

