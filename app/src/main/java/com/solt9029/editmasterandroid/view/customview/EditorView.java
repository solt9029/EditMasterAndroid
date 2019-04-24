package com.solt9029.editmasterandroid.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.solt9029.editmasterandroid.util.CalcUtil;

public class EditorView extends BaseSurfaceView implements SurfaceHolder.Callback {
    public EditorView(Context context) {
        super(context);
    }

    public EditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void draw(int translateY) {
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setStyle(Style.FILL);

        int count = 0;
        for (int y = -translateY; y < CalcUtil.convertDp2Px(2000, getContext()) - translateY; y += CalcUtil.convertDp2Px(80, getContext())) {
            canvas.drawCircle(width / 2, y, CalcUtil.convertDp2Px(10, getContext()) + count, paint);
            count++;
        }

        holder.unlockCanvasAndPost(canvas);
    }
}

